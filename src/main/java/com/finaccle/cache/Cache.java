package com.finaccle.cache;

import com.finaccle.cache.model.CachedObjectWrapper;

import java.util.NoSuchElementException;

public interface Cache<K, V> {

    void remove(String key) throws NoSuchElementException;
    void add(String key, CachedObjectWrapper value);
    void add(String key, CachedObjectWrapper value, long timeToLive);
    long size();
    void displayCache();
    CachedObjectWrapper get(String key);
}
