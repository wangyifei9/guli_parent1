package com.atguigu.eduorder.service.impl;

import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    //1.生成订单的方法
    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        //通过远程调用根据用户id获取用户信息

        //通过远程调用根据id获取课信息

        return null;
    }
}
