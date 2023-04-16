package com.atgugi.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

public class TestVod {
    //根据视频id获取播放地址
    //创建初始化对象
    DefaultAcsClient client =InitObject.initVodClient("LTAI5tQQ8rJRF23D8AhfuHyV","nkfpjU8GZMZU7PFsoLuOGfZxtPPGZe");
    //创建获取视频地址request和response
    GetPlayInfoRequest request = new GetPlayInfoRequest();
    GetPlayInfoResponse response = new GetPlayInfoResponse();
    //向requset对象里面设置视频id
     request.setVideoId("6ff53400dc6d71ed80397035d0b20102");
    //调用初始化对象里面的方法,传递request,获取数据
}
