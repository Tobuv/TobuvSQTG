package com.tobuv.sqtg.activity.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tobuv.sqtg.activity.mapper.ActivityInfoMapper;
import com.tobuv.sqtg.activity.mapper.ActivityRuleMapper;
import com.tobuv.sqtg.activity.mapper.ActivitySkuMapper;
import com.tobuv.sqtg.activity.service.ActivityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tobuv.sqtg.client.product.ProductFeignClient;
import com.tobuv.sqtg.model.activity.ActivityInfo;
import com.tobuv.sqtg.model.activity.ActivityRule;
import com.tobuv.sqtg.model.activity.ActivitySku;
import com.tobuv.sqtg.model.product.SkuInfo;
import com.tobuv.sqtg.vo.activity.ActivityRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-14
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivitySkuMapper activitySkuMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

//    @Autowired
//    private CouponInfoService couponInfoService;
    //列表
    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        IPage<ActivityInfo> activityInfoPage =
                baseMapper.selectPage(pageParam, null);
        //分页查询对象里面获取列表数据
        List<ActivityInfo> activityInfoList = activityInfoPage.getRecords();
        //遍历activityInfoList集合，得到每个ActivityInfo对象，
        // 向ActivityInfo对象封装活动类型到activityTypeString属性里面
        activityInfoList.stream().forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return activityInfoPage;
    }

    //1 根据活动id获取活动规则数据
    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        Map<String, Object> result = new HashMap<>();
        //1 根据活动id查询，查询规则列表 activity_rule表
        LambdaQueryWrapper<ActivityRule> wrapperActivityRule = new LambdaQueryWrapper<>();
        wrapperActivityRule.eq(ActivityRule::getActivityId,id);
        List<ActivityRule> activityRuleList = activityRuleMapper.selectList(wrapperActivityRule);
        result.put("activityRuleList",activityRuleList);

        //2 根据活动id查询，查询使用规则商品skuid列表 activity_sku表
        List<ActivitySku> activitySkuList = activitySkuMapper.selectList(
                new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, id)
        );
        //获取所有skuId
        List<Long> skuIdList =
                activitySkuList.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        //2.1 通过远程调用 service-product模块接口，根据 skuid列表 得到商品信息
        List<SkuInfo> skuInfoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(skuIdList)) {
            skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        }
        result.put("skuInfoList",skuInfoList);

        return result;
    }

    //2 在活动里面添加规则数据
    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        //第一步 根据活动id删除之前规则数据
        //ActivityRule数据删除
        Long activityId = activityRuleVo.getActivityId();
        activityRuleMapper.delete(
                new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId,activityId)
        );
        //ActivitySku数据删除
        activitySkuMapper.delete(
                new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId,activityId)
        );

        //第二步 获取规则列表数据
        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        ActivityInfo activityInfo = baseMapper.selectById(activityId);
        for (ActivityRule activityRule:activityRuleList) {
            activityRule.setActivityId(activityId);//活动id
            activityRule.setActivityType(activityInfo.getActivityType());//类型
            activityRuleMapper.insert(activityRule);
        }

        //第三步 获取规则范围数据
        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        for (ActivitySku activitySku:activitySkuList) {
            activitySku.setActivityId(activityId);
            activitySkuMapper.insert(activitySku);
        }
    }

    //3 根据关键字查询匹配sku信息
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        //第一步 根据关键字查询sku匹配内容列表
        //// (1) service-product模块创建接口 据关键字查询sku匹配内容列表
        //// (2) service-activity远程调用得到sku内容列表
        List<SkuInfo> skuInfoList =
                productFeignClient.findSkuInfoByKeyword(keyword);
        //判断：如果根据关键字查询不到匹配内容，直接返回空集合
        if(skuInfoList.size()==0) {
            return skuInfoList;
        }

        //从skuInfoList集合获取所有skuId
        List<Long> skuIdList =
                skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());

        //第二步 判断添加商品之前是否参加过活动，
        // 如果之前参加过，活动正在进行中，排除商品
        //// (1) 查询两张表判断 activity_info 和 activity_sku，编写SQL语句实现
        List<Long> existSkuIdList = baseMapper.selectSkuIdListExist(skuIdList);

        //// (2) 判断逻辑处理:排除已经参加活动商品
        List<SkuInfo> findSkuList = new ArrayList<>();
        //遍历全部sku列表
        for (SkuInfo skuInfo:skuInfoList) {
            if(!existSkuIdList.contains(skuInfo.getId())) {
                findSkuList.add(skuInfo);
            }
        }
        return findSkuList;
    }
}
