package com.finaccle.utils;

import com.finaccle.cache.model.CachedObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryCacheHelper {

    /*public static final Integer DEFAULT_TTL = 50000;*/
    final static Logger LOGGER = LoggerFactory.getLogger(InMemoryCacheHelper.class);

    public static Map<String, CachedObjectWrapper> removeLastRecentlyUsedObjectFromMemory(String key, Map<String, CachedObjectWrapper> inMemoryConcurrentMap) {

        // sort the map according to CachedObjectWrapper's lastAccessedTime in ascending order.
        List<Map.Entry<String, CachedObjectWrapper>> entryList =
                new ArrayList<>(inMemoryConcurrentMap.entrySet());

        Collections.sort(entryList,
                new Comparator<Map.Entry<String, CachedObjectWrapper>>() {
                    @Override
                    public int compare(Map.Entry<String, CachedObjectWrapper> lastAccessedTime1,
                                       Map.Entry<String, CachedObjectWrapper> lastAccessedTime2) {
                        return lastAccessedTime1.getValue().getLastAccessedTime()
                                .compareTo(lastAccessedTime2.getValue().getLastAccessedTime());
                    }
                }
        );
        Map.Entry<String, CachedObjectWrapper> removedObject = entryList.remove(0);
        LOGGER.info("Removed LRU Object is : {} ", removedObject);
        LOGGER.info("Remaining List : {} ", entryList);
        inMemoryConcurrentMap = entryList.stream().collect(Collectors.toMap(Map.Entry<String, CachedObjectWrapper>::getKey, Map.Entry<String, CachedObjectWrapper>::getValue));
        return inMemoryConcurrentMap;
    }

    public static CachedObjectWrapper createMockCachedObject(Integer seconds) {
        CachedObjectWrapper mockedObject = new CachedObjectWrapper();
        mockedObject.setData(new Object());
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, seconds);
        //set the expiry time in milliseconds
        mockedObject.setExpiryTime(System.currentTimeMillis() + now.getTimeInMillis());
        mockedObject.setLastAccessedTime(System.currentTimeMillis());

        return mockedObject;
    }
}
