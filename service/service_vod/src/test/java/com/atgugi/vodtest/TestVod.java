package com.atgugi.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;


public class TestVod {
    public static void main(String[] args) throws Exception {
        //根据视频iD获取视频播放凭证
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tQQ8rJRF23D8AhfuHyV", "nkfpjU8GZMZU7PFsoLuOGfZxtPPGZe");
        //创建获取视频凭证request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向request对象里面设置视频id
        request.setVideoId("6ff53400dc6d71ed80397035d0b20102");
        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);
        System.out.println("playAuth:"+response.getPlayAuth());
    }


    //1 根据视频iD获取视频播放地址
    public static void getPlayUrl() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tQQ8rJRF23D8AhfuHyV", "nkfpjU8GZMZU7PFsoLuOGfZxtPPGZe");

        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象里面设置视频id
        request.setVideoId("6ff53400dc6d71ed80397035d0b20102");

        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
