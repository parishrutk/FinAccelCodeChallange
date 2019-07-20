package com.finaccle.cache;


import com.finaccle.cache.model.CachedObjectWrapper;
import com.finaccle.utils.InMemoryCacheHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache<K, V> implements Cache {

    private final static Logger LOGGER = LoggerFactory.getLogger(InMemoryCache.class);
    private static Integer inMemorySize;
    private static Long timeToLiveInMs;
    private static Map<String, CachedObjectWrapper> inMemoryConcurrentMap;

    @Override
    public void remove(String key) throws Exception {

        if (key == null)
            throw new Exception("Key can not be null or empty");

        LOGGER.debug("Removing Cached Object from Memory for Key {} ", key);
        CachedObjectWrapper removedObject = inMemoryConcurrentMap.remove(key);
        LOGGER.debug("Removed Cached Memory Object : {} ", removedObject);
    }

    @Override
    public void add(String key, CachedObjectWrapper value) {

        //first check if the size of the Cache is full or not.
        if (inMemoryConcurrentMap.size() >= inMemorySize) {
            //remove the least recently used Object by comparing its lastAccessedTime.
            LOGGER.info("Memory is full, removing LRU Cached Object from Memory.");
            inMemoryConcurrentMap = InMemoryCacheHelper.removeLastRecentlyUsedObjectFromMemory(key, inMemoryConcurrentMap);
        }
        LOGGER.debug("Adding Cached Object in Memory with Key [{}] ", key);
        inMemoryConcurrentMap.put(key, value);
    }

    @Override
    public void add(String key, CachedObjectWrapper value, long timeToLive) {
        value.setExpiryTime(System.currentTimeMillis() + timeToLive);
        this.add(key, value);
    }

    @Override
    public long size() {
        return inMemoryConcurrentMap.size();
    }

    @Override
    public void displayCache() {
        System.out.println("=================================================================");
        for (Map.Entry<String, CachedObjectWrapper> entry : inMemoryConcurrentMap.entrySet()) {
            LOGGER.debug("Key {} :: Value {}", entry.getKey(), entry.getValue());
        }
        if (inMemoryConcurrentMap.isEmpty()) {
            LOGGER.info("Cache is Empty !!! ");
        }
        System.out.println("=================================================================\n");
    }

    @Override
    public CachedObjectWrapper get(String key) {
        //when any object is accessed need to modify is lastAccessedTime
        CachedObjectWrapper accessedObject = inMemoryConcurrentMap.get(key);
        if (accessedObject == null) {
            throw new NoSuchElementException("There is no Data in Memory for Given Input " + key);
        }
        //When an Object is read/accessed for some reason, its LastAccessedTime is changed to current time
        //If the lastAccessedTime for an Object is changed its TTL remains the Same and thus that object would be
        //evicted from Memory after its TTL has expired.
        accessedObject.setLastAccessedTime(System.currentTimeMillis());
        LOGGER.info("LastAccessedTime changed for Key {} with Object {} need to modify the Cache.", key, accessedObject);
        /* TODO: Since the lastAccessedTime for the Object has changed, it has to go through the Add LifeCycle of Cache. And if the Cache is full then
         * LRU object would be removed and this object would be added.
         * IS IT CORRECT ASSUMPTION OR NOT??
         */
        inMemoryConcurrentMap.put(key, accessedObject);
        return accessedObject;
    }

    public void initalizeCache(Integer memorySize, Integer timeToLive) {
        LOGGER.info("Initializing in memory cache with given configuration Memory Size {}, TimeToLive(ms) {}.", memorySize, timeToLive);
        inMemorySize = memorySize;
        timeToLiveInMs = (long) 1000 * timeToLive;
        inMemoryConcurrentMap = new ConcurrentHashMap<>(inMemorySize);
        LOGGER.info("In Memory cache initalized with given configuration, Going to Start the Expiry Monitor Thread.");

        //start an ExpiryListener here which would remove objects whose TTL is expired.
        Thread expiryMonitorThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(timeToLiveInMs);
                    boolean isRemoved = inMemoryConcurrentMap.entrySet().removeIf(entry -> entry.getValue().getExpiryTime() < System.currentTimeMillis());
                    LOGGER.info(isRemoved ? "Expiry Monitor Thread Removed an entry from Memory." : "No Entry removed from Cache by Expiry Monitor Thread.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        expiryMonitorThread.setDaemon(true);
        expiryMonitorThread.start();
        LOGGER.info("Started the Expiry Monitor Thread at {} ", new SimpleDateFormat("dd/MM/yyyy:hh:MM:ss.SSS").format(new Date()));
    }
}
