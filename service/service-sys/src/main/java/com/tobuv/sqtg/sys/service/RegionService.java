package com.tobuv.sqtg.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tobuv.sqtg.model.sys.Region;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
public interface RegionService extends IService<Region> {

    List<Region> getRegionByKeyword(String keyword);
}
