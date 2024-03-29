package com.atguigu.vod.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {
//    aliyun.vod.file.keyid=LTAI5tQQ8rJRF23D8AhfuHyV
//    aliyun.vod.file.keysecret=nkfpjU8GZMZU7PFsoLuOGfZxtPPGZe
    @Value("$aliyun.vod.file.keyid")
    private String keyid;
    @Value("$aliyun.vod.file.keysecret")
    private String keysecret;

    //定义公开静态常量
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }

}
