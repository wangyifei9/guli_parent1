package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-09-21
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    //统计某一天注册人数，生成统计数据
    void registerCount(String day);
}
