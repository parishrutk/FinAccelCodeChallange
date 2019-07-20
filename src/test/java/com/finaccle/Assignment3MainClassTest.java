package com.finaccle;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for Assignment3.
 */
public class Assignment3MainClassTest {

    @Test
    public void testSwapArray_success() {

        int[] arrayOfProcessIds = {5, 1, 10, 3, 12};
        int[] newArrayCopy = {3, 1, 5, 10, 12};
        String[] swapPositionArray = {"3", "2", "1"};
        int[] newArray = Assignment3MainClass.swapArrayKTimes(arrayOfProcessIds, swapPositionArray);

        Assert.assertTrue(arrayOfProcessIds.length == 5);
        Assert.assertTrue(Arrays.equals(newArrayCopy, newArray));
        Assert.assertFalse(Arrays.equals(arrayOfProcessIds, newArray));
    }

    @Test
    public void testInvalidSwapPositionIndex_twoSuccess() {

        int[] arrayOfProcessIds = {5, 1, 10, 3, 12};
        int[] arrayOfProcessIds_swappedPositionTwoTimes = {5, 10, 1, 3, 12};
        String[] swapPositionArray = {"-1", "2", "1"};
        int[] newArray = Assignment3MainClass.swapArrayKTimes(arrayOfProcessIds, swapPositionArray);

        Assert.assertFalse(Arrays.equals(arrayOfProcessIds, newArray));
        Assert.assertTrue(Arrays.equals(newArray, arrayOfProcessIds_swappedPositionTwoTimes));
    }
}
