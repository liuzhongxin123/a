package com.lzx.usercenter.viewmodel;

import androidx.lifecycle.LiveData;

import com.lzx.core.viewmodel.BaseViewModel;
import com.lzx.net.protocol.BaseRespEntity;
import com.lzx.usercenter.entity.UserEntity;
import com.lzx.usercenter.repository.UserRepository;

public class UserViewModel extends BaseViewModel {
    public UserEntity userEntity=new UserEntity();

    public UserViewModel(){
        registerRepository(UserRepository.class.getSimpleName(),new UserRepository());
    }

    public LiveData<BaseRespEntity<UserEntity>> login(){
        UserRepository userRepository=getRepository(UserRepository.class.getSimpleName());
        return userRepository.login(userEntity);
    }
}