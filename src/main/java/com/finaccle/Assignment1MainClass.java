package com.finaccle;

import com.finaccle.cache.Cache;
import com.finaccle.cache.InMemoryCache;
import com.finaccle.cache.model.CachedObjectWrapper;
import com.finaccle.utils.InMemoryCacheHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Assignment1MainClass {

    public static void main(String[] args) {

        final Logger LOGGER = LoggerFactory.getLogger(Assignment1MainClass.class);

        Cache<String, CachedObjectWrapper> cache = new InMemoryCache<>();

        Scanner scannerInput = new Scanner(System.in);
        System.out.println("Please enter Cache Memory size (example: 1, 100, 500, 8798, 1000000) ==> ");
        Integer memorySize = scannerInput.nextInt();

        System.out.println("Please enter TimeToLive(in seconds) for Memory Object ==> ");
        Integer timeToLive = scannerInput.nextInt();

        ((InMemoryCache) cache).initalizeCache(memorySize, timeToLive);

        //Display Menu for Cache Operations.
        String choice;
        do {
            System.out.println("**********************CACHE MENU OPTIONS*************************");
            System.out.println("1. Add \n2. Add with TTL. \n3. Remove \n4. Get \n5. Size \n6. Display Cache\n0. Exit!");
            System.out.println("*****************************************************************");

            choice = scannerInput.next();

            switch (choice) {
                case "1":
                    System.out.println("Please Enter a String value: ");
                    String key = scannerInput.next();
                    CachedObjectWrapper cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(timeToLive);
                    cache.add(key, cachedObjectWrapper);
                    break;
                case "2":
                    System.out.println("Please Enter a String value: ");
                    key = scannerInput.next();
                    System.out.println("Please Enter a custom TTL value(in seconds): ");
                    String seconds = scannerInput.next();
                    cachedObjectWrapper = InMemoryCacheHelper.createMockCachedObject(Integer.parseInt(seconds));
                    cache.add(key, cachedObjectWrapper, Integer.valueOf(seconds));
                    break;
                case "3":
                    System.out.println("Please Enter a String value to be Removed from Memory: ");
                    key = scannerInput.next();
                    try {
                        cache.remove(key);
                    } catch (NoSuchElementException e) {
                        LOGGER.error("The given does not exist in Cache.");
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    System.out.println("Please Enter a String value to Get Details: ");
                    key = scannerInput.next();
                    try {
                        CachedObjectWrapper inMemoryCachedObjectWrapper = cache.get(key);
                        System.out.println("Retrieved Key "+key+" and Cached Object : " + inMemoryCachedObjectWrapper);
                    } catch (NoSuchElementException noElementExp) {
                        LOGGER.error("No Element found with Cache Key [{}].", key);
                    }
                    break;
                case "5":
                    System.out.println("Current Size of Cache Memory is : " + cache.size() + " Remaining Memory is " + (memorySize - cache.size()));
                    break;
                case "6":
                    System.out.println("Displaying Memory Cache");
                    cache.displayCache();
                    break;
                case "0":
                default:
                    System.out.println("Do you Want to Exit from System? Press Y or N ");
                    String option = scannerInput.next();
                    if (option != null && option.toUpperCase().equalsIgnoreCase("Y"))
                        System.exit(0);
                    else continue;
            }
        } while (choice != "y");

    }
}
