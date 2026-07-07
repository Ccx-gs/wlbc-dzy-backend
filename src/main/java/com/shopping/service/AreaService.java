package com.shopping.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.entity.Area;

public interface AreaService extends IService<Area> {

    /**
     * 获取所有省级行政区
     */
    List<Area> getProvinces();

    /**
     * 根据省ID获取市级行政区
     */
    List<Area> getCitiesByProvinceId(Long provinceId);

    /**
     * 根据市ID获取区县级行政区
     */
    List<Area> getDistrictsByCityId(Long cityId);

    /**
     * 根据code获取下级行政区
     */
    List<Area> getChildrenByCode(String code);
}