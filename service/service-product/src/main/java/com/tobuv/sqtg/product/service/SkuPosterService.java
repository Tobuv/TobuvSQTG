package com.tobuv.sqtg.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tobuv.sqtg.model.product.SkuPoster;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
public interface SkuPosterService extends IService<SkuPoster> {

    List<SkuPoster> getPosterListBySkuId(Long id);
}
