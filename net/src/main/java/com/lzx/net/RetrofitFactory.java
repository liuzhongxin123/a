package com.lzx.net;

import android.os.Build;
import android.text.TextUtils;

import com.lzx.common.Utils.LogUtils;
import com.lzx.net.api.TokenApi;
import com.lzx.net.calladapter.LiveDataCallAdapterFactory;
import com.lzx.net.common.Config;
import com.lzx.net.protocol.TokenRespEntity;
import com.lzx.storage.core.StorageManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private final String TAG=RetrofitFactory.class.getSimpleName();
    private volatile static RetrofitFactory instance=null;
    private Retrofit retrofit;

    private RetrofitFactory(){
        initRetrofit();
    }

    public static RetrofitFactory getInstance(){
        if (null==instance){
            synchronized (RetrofitFactory.class){
                if (null==instance){
                    instance=new RetrofitFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化retrofit
     */
    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .build();
    }

    /**
     * 创建OkHttp客户端
     * @return
     */
    private OkHttpClient createOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Config.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Config.TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(Config.TIMEOUT,TimeUnit.SECONDS)
                .addNetworkInterceptor(createNetworkInterceptor())
                .addInterceptor(tokenInterceptor())
                .addInterceptor(changeBaseUrl())
                .addInterceptor(headerParamsInterceptor())
                .build();
        return client;
    }

    /**
     * 更改BaseUrl的拦截器
     * @return
     */
    private Interceptor changeBaseUrl() {
        Interceptor interceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拿到request请求对象
                Request request = chain.request();
                //从request请求中拿到url
                HttpUrl oldUrl = request.url();
                //创建一个新的request bilder
                Request.Builder newBuilder = request.newBuilder();
                //从当前请求中获取对应key的请求头信息
                List<String> headers = request.headers(Config.NewUrlHeaderKey);
                //如果包括该请求头
                if (headers!=null&&headers.size()>0){
                    //移除http请求的头信息 因为它只在程序内有效
                    newBuilder.removeHeader(Config.NewUrlHeaderKey);
                    //获取索引0的头 也就是第一个头信息
                    String headerValue = headers.get(0);
                    HttpUrl newBaseUrl=null;
                    //如果存在指定value
                    if (headerValue.equals(Config.NewUrlHeaderValue)){
                        //将baseUrl更换成testServerUrl
                        newBaseUrl=HttpUrl.parse(Config.TEST_SERVER_URL);
                    }else{
                        //没有指定的value就沿用原baseurl
                        newBaseUrl=oldUrl;
                    }
                    //构建一个新的HttpUrl对象
                    HttpUrl newUrl = oldUrl.newBuilder()
                            .scheme(newBaseUrl.scheme())
                            .host(newBaseUrl.host())
                            .port(newBaseUrl.port())
                            .build();
                    //沟通一个新的request对象
                    Request newRequest = newBuilder.url(newUrl).build();
                    //传递到下游链节点
                    return chain.proceed(newRequest);
                }
                //不包含直接执行原请求
                return chain.proceed(request);
            }
        };
        return interceptor;
    }

    /**
     * 头信息拦截器
     * @return
     */
    private Interceptor headerParamsInterceptor() {
        Interceptor interceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest = request.newBuilder()
                        .addHeader("v0", Build.MANUFACTURER)
                        .addHeader("v1",Build.MODEL)
                        .addHeader("v2",Build.TYPE)
                        .addHeader("v3",""+Build.VERSION.SDK_INT)
                        .build();
                return chain.proceed(newRequest);
            }
        };
        return interceptor;
    }

    /**
     * Token拦截器
     * @return
     */
    private Interceptor tokenInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //获取本地Token
                String localToken = StorageManager.getInstance().get("token");
                if (!TextUtils.isEmpty(localToken)){
                    return resetRequest(request,localToken,chain);
                }

                Response response = chain.proceed(request);

                //如果是401 同步请求Token然后加载到新请求的Header里，重新发起业务请求
                if (checkHttpCode401(response)){
                    String token=requestToken();
                    if (TextUtils.isEmpty(token)){
                        LogUtils.INSTANCE.e(TAG,"Error : token is null...");
                        return response;
                    }

                    //TODO:保存Token 到SP
                    StorageManager.getInstance().save("token",token);

                    return resetRequest(request,token,chain);
                }
                return response;
            }
        };

        return interceptor;
    }

    /**
     * 重置请求
     * @param request 请求
     * @param token token令牌
     * @param chain Okhttp的执行链
     * @return
     */
    private Response resetRequest(Request request,String token,Interceptor.Chain chain){
        Request.Builder newBuilder = request.newBuilder().addHeader("Authorization", "bearer " + token);

        Request newRequest=newBuilder.build();
        try {
            return chain.proceed(newRequest);
        } catch (IOException e) {
            LogUtils.INSTANCE.e(TAG,""+e);
        }
        return null;
    }



    /**
     * 判断HTTP CODE 是否401 —— TOKEN失效
     * @param response
     * @return
     */
    private boolean checkHttpCode401(Response response) {
        if (null==response){
            return false;
        }

        if (response.code()==401){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 获取Token的同步网络请求
     * @return
     */
    private String requestToken() {

        TokenApi tokenApi = create(TokenApi.class);
        Call<TokenRespEntity> tokenService = tokenApi.getToken("password", Config.AUTH_CODE, "");
        try {
            retrofit2.Response<TokenRespEntity> result = tokenService.execute();
            if (result!=null&&result.body()!=null){
                return  result.body().getAccess_token();
            }
        } catch (IOException e) {
            LogUtils.INSTANCE.e(TAG,e.getMessage());
        }
        return "";
    }

    /**
     * 网络请求的拦截器
     * @return
     */
    private Interceptor createNetworkInterceptor() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    /**
     * 创建服务器请求
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }
}