package com.shopping.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.entity.Area;
import com.shopping.mapper.AreaMapper;
import com.shopping.service.AreaService;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public List<Area> getProvinces() {
        return lambdaQuery()
                .eq(Area::getLevel, 1)
                .orderByAsc(Area::getId)
                .list();
    }

    @Override
    public List<Area> getCitiesByProvinceId(Long provinceId) {
        return lambdaQuery()
                .eq(Area::getLevel, 2)
                .eq(Area::getPid, provinceId)
                .orderByAsc(Area::getId)
                .list();
    }

    @Override
    public List<Area> getDistrictsByCityId(Long cityId) {
        return lambdaQuery()
                .eq(Area::getLevel, 3)
                .eq(Area::getPid, cityId)
                .orderByAsc(Area::getId)
                .list();
    }

    @Override
    public List<Area> getChildrenByCode(String code) {
        return lambdaQuery()
                .eq(Area::getLevel, 2)
                .eq(Area::getPid, Long.parseLong(code))
                .orderByAsc(Area::getId)
                .list();
    }
}