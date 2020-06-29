package com.lzx.net.common;

import com.lzx.net.BuildConfig;

public class Config {

     //验证码 用于向后台请求Token使用
    public final static String AUTH_CODE="651851771c11d419413f1b91b717e16312e18f1d71d91d01";

     //网络请求超时时长
    public final static int TIMEOUT=10;

     //网络请求的基础地址
    public final static String BASE_URL= BuildConfig.baseUrl;

     // 测试业务服务器地址
    public final static String TEST_SERVER_URL=BuildConfig.testServerUrl;

    public final static String  NewUrlHeaderKey="newUrl";
    public final static String NewUrlHeaderValue="testServerUrl";
}