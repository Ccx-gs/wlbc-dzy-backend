package com.shopping.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.common.Result;
import com.shopping.entity.Area;
import com.shopping.service.AreaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/area")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    /**
     * 获取所有省级行政区
     */
    @GetMapping("/provinces")
    public Result<List<Area>> getProvinces() {
        return Result.ok(areaService.getProvinces());
    }

    /**
     * 根据省ID获取市级行政区
     */
    @GetMapping("/cities/{provinceId}")
    public Result<List<Area>> getCities(@PathVariable Long provinceId) {
        return Result.ok(areaService.getCitiesByProvinceId(provinceId));
    }

    /**
     * 根据市ID获取区县级行政区
     */
    @GetMapping("/districts/{cityId}")
    public Result<List<Area>> getDistricts(@PathVariable Long cityId) {
        return Result.ok(areaService.getDistrictsByCityId(cityId));
    }

    /**
     * 根据code获取下级行政区
     */
    @GetMapping("/children")
    public Result<List<Area>> getChildren(@RequestParam String code) {
        return Result.ok(areaService.getChildrenByCode(code));
    }

    /**
     * 根据ID获取单个行政区信息
     */
    @GetMapping("/{id}")
    public Result<Area> getById(@PathVariable Long id) {
        return Result.ok(areaService.getById(id));
    }
}