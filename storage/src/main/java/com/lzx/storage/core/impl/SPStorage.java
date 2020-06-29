package com.lzx.storage.core.impl;

import com.lzx.common.Utils.LogUtils;
import com.lzx.common.app.BaseAppcation;
import com.lzx.storage.core.IStorage;
import com.lzx.storage.utils.SharePreferenceUtils;

public class SPStorage implements IStorage {
    private final String TAG=SPStorage.class.getSimpleName();
    @Override
    public <T> boolean save(String key, T value) {
        try {
            SharePreferenceUtils.put(BaseAppcation.getAppContext(),key,value);
        }
        catch (Exception ex){
            LogUtils.INSTANCE.e(TAG,""+ex);
            return false;
        }

        return true;
    }

    @Override
    public <T> T get(String key) {
        T result = (T) SharePreferenceUtils.get(BaseAppcation.getAppContext(), key, "");
        return result;
    }
}