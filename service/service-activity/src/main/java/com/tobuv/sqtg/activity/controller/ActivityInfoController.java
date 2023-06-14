package com.tobuv.sqtg.activity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tobuv.sqtg.activity.service.ActivityInfoService;
import com.tobuv.sqtg.common.result.Result;
import com.tobuv.sqtg.model.activity.ActivityInfo;
import com.tobuv.sqtg.model.product.SkuInfo;
import com.tobuv.sqtg.vo.activity.ActivityRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author tobuv
 * @since 2023-06-14
 */
@RestController
@RequestMapping("/admin/activity/activityInfo")
@CrossOrigin
public class ActivityInfoController {

    @Autowired
    private ActivityInfoService activityInfoService;

    //列表
    //    url: `${api_name}/${page}/${limit}`,
    //    method: 'get'
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit) {
        Page<ActivityInfo> pageParam = new Page<>(page,limit);
        IPage<ActivityInfo> pageModel =
                activityInfoService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    //    url: `${api_name}/get/${id}`,
    //    method: 'get'
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        ActivityInfo activityInfo = activityInfoService.getById(id);
        activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment());
        return Result.ok(activityInfo);
    }

    //添加活动
    //    url: `${api_name}/save`,
    //    method: 'post',
    //    data: role
    @PostMapping("save")
    public Result save(@RequestBody ActivityInfo activityInfo) {
        activityInfoService.save(activityInfo);
        return Result.ok(null);
    }

    //    url: `${api_name}/update`,
    //    method: 'put',
    //    data: role
    @PostMapping("update")
    public Result update(@RequestBody ActivityInfo activityInfo){
        activityInfoService.updateById(activityInfo);
        return Result.ok(null);
    }

    //    url: `${api_name}/remove/${id}`,
    //    method: 'delete'
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        activityInfoService.removeById(id);
        return Result.ok(null);
    }

    //    url: `${api_name}/batchRemove`,
    //    method: 'delete',
    //    data: idList
    public Result batchRemove(@RequestBody List<Long> idList){
        activityInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    //营销活动规则相关接口
    //1 根据活动id获取活动规则数据
    //    url: `${api_name}/findActivityRuleList/${id}`,
    //    method: 'get'
    @GetMapping("findActivityRuleList/{id}")
    public Result findActivityRuleList(@PathVariable Long id) {
        Map<String,Object> activityRuleMap =
                activityInfoService.findActivityRuleList(id);
        return Result.ok(activityRuleMap);
    }

    //2 在活动里面添加规则数据
    //    url: `${api_name}/saveActivityRule`,
    //    method: 'post',
    //    data: rule
    @PostMapping("saveActivityRule")
    public Result saveActivityRule(@RequestBody ActivityRuleVo activityRuleVo) {
        activityInfoService.saveActivityRule(activityRuleVo);
        return Result.ok(null);
    }

    //3 根据关键字查询匹配sku信息
    //    url: `${api_name}/findSkuInfoByKeyword/${keyword}`,
    //    method: 'get'
    @GetMapping("findSkuInfoByKeyword/{keyword}")
    public Result findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        List<SkuInfo> list =
                activityInfoService.findSkuInfoByKeyword(keyword);
        return Result.ok(list);
    }

}

