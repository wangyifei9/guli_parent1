package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.ConstantWxUtilsTest;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @Autowired
    private ConstantWxUtilsTest constantWxUtilsTest;
    //2获取扫描人信息，添加数据
    @GetMapping("callback")
        public String callback(String code,String state){
        System.out.println("code:"+code);
        System.out.println("state:"+state);
        try {
            //1.获取code值，临时票据，类似于验证码
            //2.获取code值 微信固定的地址，得到两个值 accsess_token 和 openid
            String baseAccessTokenUrl =
                    "https://api.weixin.qq.com/sns/oauth2/access_token" +
                            "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";
            //拼接三个参数：id 秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    constantWxUtilsTest.getAppId(),
                    constantWxUtilsTest.getAppSecret(),
                    code
            );
            //请求这个拼接好的地址，得到两个返回值access_token 和openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //从accessTokenInfo字符串取出两个值access_token 和openid
            //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            //使用json转换工具 Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");

            //3拿着得到access_token 和 openid ,再去请求微信提供固定的地址，获取到扫描人信息
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            //拼接两个参数
            String userInfoUrl = String.format(
                    baseUserInfoUrl,
                    access_token,
                    openid
            );
            //发送请求
            String userInfo = HttpClientUtils.get(userInfoUrl);
            System.out.println("userInfo:"+userInfo);
            //获取返回userInfo字符串扫描人信息
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoMap.get("nickname");//昵称
            String headimgurl = (String)userInfoMap.get("headimgurl");//头像

            //把扫描人信息添加数据库里面
            //判断数据库表里面是否存在相同微信信息,根据openid判断
            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);
            if(member == null){
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                ucenterMemberService.save(member);
            }

//            System.out.println("accessTokenInfo"+accessTokenInfo);

            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //最后返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw new GuliException(20001,"微信扫码登录失败");
        }
        }
    //1生成微信扫码二维码
    @GetMapping("login")
    public String genQrConnect(HttpSession session) {
        // 微信开放平台授权baseUrl %s相当于？代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址，对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (Exception e) {
//            throw new GuliException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "atguigu";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                constantWxUtilsTest.getAppId(),
                constantWxUtilsTest.getRedirectUrl(),
                state);
        //重定向到请求微信地址里面
        return "redirect:" + qrcodeUrl;
    }

    //1生成微信扫码二维码
    //
//@Test
//    public  void genQr() {
//        // 微信开放平台授权baseUrl %s相当于？代表占位符
//        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
//                "?appid=%s" +
//                "&redirect_uri=%s" +
//                "&response_type=code" +
//                "&scope=snsapi_login" +
//                "&state=%s" +
//                "#wechat_redirect";
//        // 回调地址，对redirect_url进行URLEncoder编码
//    //获取业务服务器重定向地址
////    String redirectUrl = constantWxUtilsTest.getRedirectUrl();
////    constantWxUtilsTest.ConstantWxUtilsTest();
////    String redirectUrl = redirectUrl;
//    System.out.println("redirectUrl:"+redirectUrl);
//
//        try { //url编码
//            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
//            System.out.println("redirectUrl:"+redirectUrl);
//        } catch (Exception e) {
////            throw new GuliException(20001, e.getMessage());
//        }
//
//        String state = "atguigu";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
//        //生成qrcodeUrl
//        String qrcodeUrl = String.format(
//                baseUrl,
//                "wxed9954c01bb89b47",
//                "http://localhost:8160/api/ucenter/wx/callback",
//                state);
//    System.out.println("ConstantWxUtils.WX_OPEN_APP_ID:"+appId);
//    System.out.println("redirectUrl:"+redirectUrl);
//    System.out.println("qrcodeUr:"+qrcodeUrl);
//    }


}
