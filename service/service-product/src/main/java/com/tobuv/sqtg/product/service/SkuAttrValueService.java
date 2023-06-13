package com.tobuv.sqtg.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tobuv.sqtg.model.product.SkuAttrValue;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    List<SkuAttrValue> getAttrValueListBySkuId(Long id);
}
