package com.shopping.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shopping.common.Result;
import com.shopping.dto.PointsRecordVO;
import com.shopping.service.PointsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class PointsController {

    private final PointsService pointsService;

    /** 积分概况 */
    @GetMapping("/points")
    public Result<Map<String, Integer>> points() {
        return Result.ok(Map.of(
                "points", pointsService.getPoints(),
                "totalEarned", pointsService.getTotalEarned()
        ));
    }

    /** 积分流水 */
    @GetMapping("/points/records")
    public Result<Page<PointsRecordVO>> records(@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "20") Integer size) {
        return Result.ok(pointsService.records(page, size));
    }
}
