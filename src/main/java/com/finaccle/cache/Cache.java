package com.finaccle.cache;

import com.finaccle.cache.model.CachedObjectWrapper;

public interface Cache<K, V> {

    void remove(String key) throws Exception;
    void add(String key, CachedObjectWrapper value);
    void add(String key, CachedObjectWrapper value, long timeToLive);
    long size();
    void displayCache();
    CachedObjectWrapper get(String key);
}
