package com.lzx.usercenter.app;


import com.lzx.common.app.BaseAppcation;
import com.lzx.storage.core.StorageManager;
import com.lzx.storage.core.StorageType;

public class UserCenterApp extends BaseAppcation {
    @Override
    protected void initOtherConfig() {
        //初始化存储位置为SP
        StorageManager.getInstance().init(StorageType.SP);
    }
}
