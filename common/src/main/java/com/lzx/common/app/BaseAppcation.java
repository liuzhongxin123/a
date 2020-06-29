package com.lzx.common.app;

import android.app.Application;
import android.content.Context;

public abstract class BaseAppcation extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initOtherConfig();
    }

    /**
     * 初始化其他配置
     */
    protected abstract void initOtherConfig();

    /**
     * 获取应用的上下文
     * @return
     */
    public static Context getAppContext(){
        return context;
    }

}