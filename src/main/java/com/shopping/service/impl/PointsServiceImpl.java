package com.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shopping.common.BusinessException;
import com.shopping.common.UserHolder;
import com.shopping.dto.PointsRecordVO;
import com.shopping.dto.UserDTO;
import com.shopping.entity.PointsRecord;
import com.shopping.entity.User;
import com.shopping.mapper.PointsRecordMapper;
import com.shopping.mapper.UserMapper;
import com.shopping.service.PointsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointsServiceImpl implements PointsService {

    private final UserMapper userMapper;
    private final PointsRecordMapper pointsRecordMapper;

    private Long getUserId() {
        UserDTO user = UserHolder.getUser();
        if (user == null || user.getId() == null) {
            throw new BusinessException(401, "请先登录后操作");
        }
        return user.getId();
    }

    @Override
    public int getPoints() {
        User user = userMapper.selectById(getUserId());
        return user != null && user.getPoints() != null ? user.getPoints() : 0;
    }

    @Override
    public int getTotalEarned() {
        Long userId = getUserId();
        List<PointsRecord> list = pointsRecordMapper.selectList(new LambdaQueryWrapper<PointsRecord>()
                .eq(PointsRecord::getUserId, userId)
                .gt(PointsRecord::getPoints, 0)
                .select(PointsRecord::getPoints));
        int total = list.stream().mapToInt(r -> r.getPoints() != null ? r.getPoints() : 0).sum();
        return total;
    }

    @Override
    public Page<PointsRecordVO> records(Integer current, Integer size) {
        Long userId = getUserId();
        Page<PointsRecord> page = pointsRecordMapper.selectPage(
                new Page<>(current, size),
                new LambdaQueryWrapper<PointsRecord>()
                        .eq(PointsRecord::getUserId, userId)
                        .orderByDesc(PointsRecord::getCreateTime));

        Page<PointsRecordVO> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(page.getRecords().stream().map(r -> {
            PointsRecordVO vo = new PointsRecordVO();
            vo.setId(r.getId());
            vo.setPoints(r.getPoints());
            vo.setType(r.getType());
            vo.setDescription(r.getDescription());
            vo.setRelatedId(r.getRelatedId());
            vo.setBalanceAfter(r.getBalanceAfter());
            vo.setCreateTime(r.getCreateTime());
            return vo;
        }).toList());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void awardPoints(Long userId, String orderNo, int points) {
        if (points <= 0) return;
        User user = userMapper.selectById(userId);
        if (user == null) return;

        int balanceAfter = (user.getPoints() != null ? user.getPoints() : 0) + points;
        user.setPoints(balanceAfter);
        userMapper.updateById(user);

        PointsRecord pr = new PointsRecord();
        pr.setUserId(userId);
        pr.setPoints(points);
        pr.setType(1);
        pr.setDescription("订单 " + orderNo + " 付款奖励");
        pr.setRelatedId(orderNo);
        pr.setBalanceAfter(balanceAfter);
        pr.setCreateTime(LocalDateTime.now());
        pointsRecordMapper.insert(pr);

        log.info("发放积分: userId={}, orderNo={}, points={}, balance={}", userId, orderNo, points, balanceAfter);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void spendPoints(Long userId, int points, String description, String relatedId) {
        if (points <= 0) return;
        User user = userMapper.selectById(userId);
        if (user == null) throw new BusinessException(400, "用户不存在");
        if (user.getPoints() == null || user.getPoints() < points) {
            throw new BusinessException(400, "积分不足");
        }

        int balanceAfter = user.getPoints() - points;
        user.setPoints(balanceAfter);
        userMapper.updateById(user);

        PointsRecord pr = new PointsRecord();
        pr.setUserId(userId);
        pr.setPoints(-points);
        pr.setType(2);
        pr.setDescription(description);
        pr.setRelatedId(relatedId);
        pr.setBalanceAfter(balanceAfter);
        pr.setCreateTime(LocalDateTime.now());
        pointsRecordMapper.insert(pr);

        log.info("消耗积分: userId={}, points={}, balance={}, desc={}", userId, points, balanceAfter, description);
    }
}
