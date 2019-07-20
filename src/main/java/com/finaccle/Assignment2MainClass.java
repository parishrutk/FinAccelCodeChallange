package com.finaccle;

import java.util.Scanner;

public class Assignment2MainClass {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Enter Number of Rows: ");
        int rows = scanner.nextInt();
        System.out.println("Please Enter number of Columns: ");
        int column = scanner.nextInt();

        int[][] array = new int[rows][column];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                System.out.println("Please Enter Value for Element [" + i + "]" + "[" + j + "] ==> ");
                array[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print("" + array[i][j] + ",");
            }
            System.out.print("\n");
        }
        int output = findSquareHouse(array, rows, column);
        System.out.println("Squre House Output:  " + output);
    }

    private static int findSquareHouse(int[][] array, int rows, int column) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                int leftUpper = array[i][j];
                int leftBottom = array[i + 1][j];
                int rightUpper = array[i][j + 1];
                int rightBottom = array[i + 1][j + 1];

                if (leftUpper == 0 || leftBottom == 0 || rightBottom == 0 || rightUpper == 0)
                    continue;
                else {

                }
            }
        }
        return 0;
    }
}
