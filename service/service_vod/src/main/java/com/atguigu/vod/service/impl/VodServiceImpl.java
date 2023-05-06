package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.atguigu.vod.Utils.ConstantVodUtils;
import com.atguigu.vod.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAly(MultipartFile file) {

        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
//            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
//
//            UploadVideoImpl uploader = new UploadVideoImpl();
//            UploadStreamResponse response = uploader.uploadStream(request);
//
//            String videoId = null;
//            if (response.isSuccess()) {
//                videoId = response.getVideoId();
//            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
//                videoId = response.getVideoId();
//                System.out.print("VideoId=" + response.getVideoId() + "\n");
//                System.out.print("ErrorCode=" + response.getCode() + "\n");
//                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//            }


        UploadVideoRequest request = new UploadVideoRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        String videoId = null;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            videoId = response.getVideoId();
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
