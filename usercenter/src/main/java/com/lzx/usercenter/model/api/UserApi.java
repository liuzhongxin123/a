package com.lzx.usercenter.model.api;

import androidx.lifecycle.LiveData;

import com.lzx.net.protocol.BaseRespEntity;
import com.lzx.usercenter.entity.UserEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
//    @Headers({Config.NewUrlHeaderKey + ":" + Config.NewUrlHeaderValue})
//    @GET("/login")
//    Call<TokenRespEntity> getTest();

    @POST("api/User/login")
    LiveData<BaseRespEntity<UserEntity>> login(@Body UserEntity userEntity);

}
