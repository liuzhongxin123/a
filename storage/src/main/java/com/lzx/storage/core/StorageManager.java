package com.lzx.storage.core;

import com.lzx.storage.core.impl.FileStorage;
import com.lzx.storage.core.impl.SPStorage;

public class StorageManager {
    private static StorageManager instance=new StorageManager();
    private StorageManager(){
        storage=new SPStorage();
    }
    public static StorageManager getInstance(){
        return instance;
    }

    private IStorage storage;
    public void init(int storageType){
        if (storageType==StorageType.SP){
            storage=new SPStorage();
        }else if (storageType==StorageType.FILE){
            storage=new FileStorage();
        }
    }
    public <T> boolean save(String key, T value) {
        return storage.save(key,value);
    }

    public <T> T get(String key) {
        return storage.get(key);
    }

}
