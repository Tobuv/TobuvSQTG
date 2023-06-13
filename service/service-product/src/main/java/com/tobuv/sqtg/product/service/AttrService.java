package com.tobuv.sqtg.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tobuv.sqtg.model.product.Attr;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
public interface AttrService extends IService<Attr> {

    List<Attr> getAttrListByGroupId(Long groupId);
}
