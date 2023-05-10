package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.servicebase.exception.GuliException;
import com.atguigu.vod.Utils.InitVodClient;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传阿里云视频
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file){
        //返回上传视频id

        String videoId = vodService.uploadVideoAly(file);
//        System.out.println("videoId="+videoId);
        return  R.ok().data("videoId",videoId);
    }
    //根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
    try {
        //初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tQQ8rJRF23D8AhfuHyV","nkfpjU8GZMZU7PFsoLuOGfZxtPPGZe");
        //创建删除视频request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        //向request设置视频id
        request.setVideoIds(id);
        //调用初始化对象的方法实现删除
        client.getAcsResponse(request);
        return R.ok();
    }catch (Exception e){
        e.printStackTrace();
        throw new GuliException(20001,"视频删除失败");
    }
    }
}
