package com.atguigu.eduservice.client;

import org.springframework.stereotype.Component;

@Component
public class OrderFileDegradeFeignClient implements OrderClient{
    //出错之后会执行
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }


}
