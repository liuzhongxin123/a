package com.lzx.storage.core.impl;

import com.lzx.storage.core.IStorage;

public class StorageSqlite implements IStorage {
    @Override
    public <T> boolean save(String key, T value) {
        return false;
    }

    @Override
    public <T> T get(String key) {
        return null;
    }
}