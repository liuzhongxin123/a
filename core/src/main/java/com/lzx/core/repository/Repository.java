package com.lzx.core.repository;

import com.lzx.core.model.IModel;

public abstract class Repository<T extends IModel> {

    protected T mModel;
    public Repository(){
        mModel=createModel();
    }
    protected abstract T createModel();
}
