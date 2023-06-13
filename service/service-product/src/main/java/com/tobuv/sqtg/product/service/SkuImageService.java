package com.tobuv.sqtg.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tobuv.sqtg.model.product.SkuImage;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> getImageListBySkuId(Long id);
}
