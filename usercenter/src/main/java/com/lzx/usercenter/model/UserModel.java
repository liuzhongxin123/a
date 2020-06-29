package com.lzx.usercenter.model;

import androidx.lifecycle.LiveData;

import com.lzx.core.model.IModel;
import com.lzx.net.RetrofitFactory;
import com.lzx.net.protocol.BaseRespEntity;
import com.lzx.usercenter.entity.UserEntity;
import com.lzx.usercenter.model.api.UserApi;

import io.reactivex.disposables.Disposable;

public class UserModel implements IModel {
    private final String TAG=UserModel.class.getSimpleName();
    Disposable disposable=null;
    /**
     * 登录方法
     * @param userEntity
     * @return
     */
    public LiveData<BaseRespEntity<UserEntity>> login(final UserEntity userEntity){
        final UserApi userApi = RetrofitFactory.getInstance().create(UserApi.class);
        LiveData<BaseRespEntity<UserEntity>> result = userApi.login(userEntity);
        return result;
    }
}