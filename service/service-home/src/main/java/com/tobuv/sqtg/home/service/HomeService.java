package com.tobuv.sqtg.home.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface HomeService {

    //首页数据显示接口
    Map<String, Object> homeData(Long userId);
}
