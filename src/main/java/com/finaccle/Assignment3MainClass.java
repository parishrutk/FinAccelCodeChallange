package com.finaccle;

import java.util.Arrays;
import java.util.Scanner;

public class Assignment3MainClass {

    public static void main(String[] args) {
        System.out.println("Please Enter Total number of process_ids ==> ");
        Scanner input = new Scanner(System.in);
        int totalNumber = input.nextInt();
        System.out.println("Total Number of Processes: " + totalNumber);

        int[] arrayOfProcessIds = new int[totalNumber];
        System.out.println("Please Enter ProcessIds one by one separated by Comma: ");
        String inputString = input.next();
        String[] inputArray = inputString.split(",");
        for (int i = 0; i < totalNumber; i++) {
            arrayOfProcessIds[i] = Integer.parseInt(inputArray[i]);
        }
        System.out.println("Please enter Swap Array one by one separated by comma.");
        String swapArray = input.next();
        String[] swapSplitArray = swapArray.split(",");

        System.out.println("=================================================================");
        //print the original copy for reference
        for (int j = 0; j < arrayOfProcessIds.length; j++) {
            System.out.println("Original Process Id: " + arrayOfProcessIds[j]);
        }
        System.out.println("=================================================================");
        try {
            System.out.println("Swapping Position is considered from 0th Index.\n\n==================================================");
            arrayOfProcessIds = swapArrayKTimes(arrayOfProcessIds, swapSplitArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("After Swapping it " + swapSplitArray.length + " Times New Process Array \n");
        for (int j = 0; j < arrayOfProcessIds.length; j++) {
            System.out.println("New Process Id: " + arrayOfProcessIds[j]);
        }
    }

    private static int[] swapArrayKTimes(int[] arrayOfProcessIds, String[] swapPositionsArray) throws Exception {

        int swapFailures = 0;
        for (int i = 0; i < swapPositionsArray.length; i++) {
            int positionOfSwap = Integer.parseInt(swapPositionsArray[i]);

            if (positionOfSwap > arrayOfProcessIds.length) {
                swapFailures++;
                System.out.println("Invalid Swap Position [" + positionOfSwap + "] specified. Swapping position has to be between [0 to " + arrayOfProcessIds.length + "] Ignoring the Swap Position and continuing the next one.");
                continue;
            }
            int[] newArray = new int[arrayOfProcessIds.length];
            shuffleArray(newArray, arrayOfProcessIds, positionOfSwap);

            System.out.println("Swapping above Array with swap position: " + positionOfSwap + " and iteration number: " + (i + 1) + "\n");
            for (int count = 0; count < newArray.length; count++) {
                System.out.println("New array Element: " + newArray[count]);
            }
            System.out.println("-----------------------------------------------------");
            arrayOfProcessIds = newArray;
        }

        return arrayOfProcessIds;
    }

    private static void shuffleArray(int[] newArray, int[] arrayOfProcessIds, int positionOfSwap) {
        newArray[0] = arrayOfProcessIds[positionOfSwap];
        for (int i = 1; i <= positionOfSwap; i++) {
            newArray[i] = arrayOfProcessIds[i - 1];
        }
        for (int j = positionOfSwap; j < arrayOfProcessIds.length - 1; j++) {
            newArray[j + 1] = arrayOfProcessIds[j + 1];
        }
    }
}
