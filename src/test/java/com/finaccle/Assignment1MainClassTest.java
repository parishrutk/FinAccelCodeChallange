package com.finaccle;

import com.finaccle.cache.Cache;
import com.finaccle.cache.InMemoryCache;
import com.finaccle.cache.model.CachedObjectWrapper;
import com.finaccle.utils.InMemoryCacheHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.NoSuchElementException;

public class Assignment1MainClassTest {

    @Test
    public void testAdd_success() {
        int timeToLive = 120;
        int memorySize = 4;
        String key = "Test1";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);
        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(120);

        testCache.remove(key);
        testCache.add("Test1", cachedObjectWrapper);

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().size() == 1);
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().containsKey(key));
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key) != null);

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getLastAccessedTime() != null);
        //Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getLastAccessedTime().equals(System.currentTimeMillis()));

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getExpiryTime() != null);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, timeToLive);
        //Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getExpiryTime().equals(System.currentTimeMillis() + now.getTimeInMillis()));

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getData() != null);
    }

    @Test
    public void testAdd_cacheMemoryFull() {
        int timeToLive = 120;
        int memorySize = 1;
        String key = "Test1";
        String key2 = "Test11";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);


        testCache.remove(key);
        testCache.remove(key2);

        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(120);
        testCache.add(key, cachedObjectWrapper);

        cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(120);
        testCache.add(key2, cachedObjectWrapper);

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().size() == 1);
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().containsKey(key2));
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key2) != null);

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key2).getLastAccessedTime() != null);
        //Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getLastAccessedTime().equals(System.currentTimeMillis()));

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key2).getExpiryTime() != null);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, timeToLive);
        //Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getExpiryTime().equals(System.currentTimeMillis() + now.getTimeInMillis()));

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key2).getData() != null);

    }

    @Test
    public void testAddWithTTL_success() {

        int timeToLive = 120;
        int memorySize = 4;
        int customTTL = 60;
        String key = "Test2";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);
        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(customTTL);

        testCache.add(key, cachedObjectWrapper, customTTL);

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().size() == 1);
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().containsKey(key));
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key) != null);

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getLastAccessedTime() != null);
        //Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getLastAccessedTime().equals(System.currentTimeMillis()));

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getExpiryTime() != null);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, customTTL);
        //Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getExpiryTime().equals(System.currentTimeMillis() + now.getTimeInMillis()));

        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key).getData() != null);

    }

    @Test
    public void testRemove_success() {

        int timeToLive = 120;
        int memorySize = 4;
        int customTTL = 60;
        String key = "Test3";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);
        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(customTTL);

        testCache.add(key, cachedObjectWrapper, customTTL);
        testCache.remove(key);
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().size() == 0);
        Assert.assertTrue(!InMemoryCache.getInMemoryConcurrentMap().containsKey(key));
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().get(key) == null);
    }

    @Test
    public void testGet_success() {

        int timeToLive = 120;
        int memorySize = 4;
        int customTTL = 60;
        String key = "Test4";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);

        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(customTTL);
        testCache.add(key, cachedObjectWrapper, customTTL);

        CachedObjectWrapper inMemoryCachedObjectWrapper = testCache.get(key);

        Assert.assertTrue(inMemoryCachedObjectWrapper != null);
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().size() == 1);
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().containsKey(key));

    }

    @Test(expected = NoSuchElementException.class)
    public void testGet_exception() {

        int timeToLive = 120;
        int memorySize = 4;
        int customTTL = 60;
        String key = "Test4";
        String invalidKey = "Test44";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);

        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(customTTL);
        testCache.add(key, cachedObjectWrapper, customTTL);

        CachedObjectWrapper inMemoryCachedObjectWrapper = testCache.get(invalidKey);

        Assert.assertTrue(inMemoryCachedObjectWrapper != null);
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().size() == 1);
        Assert.assertTrue(InMemoryCache.getInMemoryConcurrentMap().containsKey(key));

    }

    @Test(expected = NoSuchElementException.class)
    public void testRemove_exception() {

        int timeToLive = 120;
        int memorySize = 4;
        int customTTL = 60;
        String key = null;

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);

        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(customTTL);
        testCache.remove(key);
    }

    @Test
    public void testSize_success() {

        int timeToLive = 120;
        int memorySize = 4;
        int customTTL = 60;
        String key = "Test4";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);

        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(customTTL);
        testCache.add(key, cachedObjectWrapper, customTTL);

        long size = testCache.size();

        Assert.assertTrue(size == 1);
    }

    @Test
    public void testDisplayCache_success() {

        int timeToLive = 120;
        int memorySize = 4;
        int customTTL = 60;
        String key = "Test4";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);

        CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(customTTL);
        testCache.add(key, cachedObjectWrapper, customTTL);

        testCache.displayCache();
    }

    @Test
    public void testDisplayCache_noData() {

        int timeToLive = 120;
        int memorySize = 4;
        int customTTL = 60;
        String key = "Test4";

        Cache testCache = new InMemoryCache<String, CachedObjectWrapper>();
        ((InMemoryCache) testCache).initalizeCache(memorySize, timeToLive);

        testCache.displayCache();
    }
}