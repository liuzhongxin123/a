package com.lzx.usercenter.repository;

import androidx.lifecycle.LiveData;

import com.lzx.core.repository.Repository;
import com.lzx.net.protocol.BaseRespEntity;
import com.lzx.usercenter.entity.UserEntity;
import com.lzx.usercenter.model.UserModel;

public class UserRepository extends Repository<UserModel> {
    @Override
    protected UserModel createModel() {
        return new UserModel();
    }

    public LiveData<BaseRespEntity<UserEntity>> login(UserEntity userEntity){
        return mModel.login(userEntity);
    }
}
