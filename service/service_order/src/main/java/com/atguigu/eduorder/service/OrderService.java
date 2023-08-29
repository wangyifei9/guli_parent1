package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-07-28
 */
public interface OrderService extends IService<Order> {
    //1.生成订单的方法
    String createOrder(String courseId, String memberIdByJwtToken);
}
