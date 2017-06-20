package stu.jzhang.algorithm.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hellen on 6/19/17.
 */
public class Utilies {
    public static void printArray(List list){
        System.out.print("[");
        for(Object item : list){
            System.out.print(item + " ");
        }

        System.out.print("]");
        System.out.println();
    }

    public static void swap(int[] arr, int left, int right){
        int tmp = arr[left];
        arr[left] = arr[right];
        arr[right] = tmp;
    }

    public static void swap(List[] arr, int left, int right){
        List tmp = arr[left];
        arr[left] = arr[right];
        arr[right] = tmp;
    }

    public static void print2DArray(int[][] array){
        for(int[] arr : array){
            System.out.println(Arrays.toString(arr));
        }

        System.out.println();
    }

    public static void swap(int[][] matrix, int r1, int c1, int r2, int c2){
        int tmp = matrix[r1][c1];
        matrix[r1][c1] = matrix[r2][c2];
        matrix[r2][c2] = tmp;
    }

    public static void mirrorDiagonal(int[][] matrix){
        int n = matrix.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++){
                swap(matrix, i,j, j, i);
            }
        }
    }

    public static void mirrorX(int[][] matrix){
        int n = matrix.length;
        for(int i =0; i < n; i++){
            for(int j = 0; j < n/2; j++){
                swap(matrix, i, j, i, n-j-1);
            }
        }
    }
}
