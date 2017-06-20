package stu.jzhang.algorithm;

import stu.jzhang.algorithm.util.Utilies;

import java.util.ArrayList;
import java.util.List;

/**
 * Two pointers. The left side of the left pointer represents processed result.
 * The elements between two pointers are useless items. the item of the fast pointer is unknown result.
 * Created by hellen on 6/19/17.
 */
public class ArraysQuestions {
    public static void main(String[] args){
        ArraysQuestions test = new ArraysQuestions();
//        System.out.println(Arrays.toString(test.deleteDuplicates(new int[]{1,2,2,2,2,2,2,2,2,3,4,5,6})));
//        System.out.println(Arrays.toString(test.deleteDuplicatesWithTwoLeftAtMost(new int[]{1,2,2,2,2,2,2,2,2,3,4,5,6})));
//        System.out.println(Arrays.toString(test.deleteAllDuplicates(new int[]{1,2,2,2,2,2,2,2,2,3,4,5,6})));
//        System.out.println(Arrays.toString(test.findLargestAndSmallest(new int[]{1,2,2,2,2,12,2,2,2,3,4,5,6})));
//        System.out.println(Arrays.toString(test.findLargestAndSecondLargest(new int[]{1,2,12,2,2,12,2,2,2,3,4,5,6})));
//        Utilies.print2DArray(test.rotateMatrix(new int[][]{{1,2,3,4}, {12,13,14,5}, {11,16,15,6}, {10,9,8,7}}));
        Utilies.print2DArray(test.rotateMatrixFlips(new int[][]{{1,2,3,4}, {12,13,14,5}, {11,16,15,6}, {10,9,8,7}}));
    }

    /**
     * remove duplicates with one left
     * @param arr
     * @return
     */
    public int[] deleteDuplicates(int[] arr){
        if(arr == null || arr.length <= 1){
            return arr;
        }

        int fast = 1;
        int slow = 1;
        while (fast < arr.length){
            if(arr[fast] != arr[slow - 1]){
                Utilies.swap(arr, slow, fast);
                slow++;
            }
            fast++;
        }

        return arr;
    }

    public int[] deleteDuplicatesWithTwoLeftAtMost(int[] arr){
        if(arr == null || arr.length <= 1){
            return arr;
        }

        int fast = 2;
        int slow = 2;
        while (fast < arr.length){
            if(arr[fast] != arr[slow - 2]){
                Utilies.swap(arr, slow, fast);
                slow++;
            }
            fast++;
        }

        return arr;
    }

    public int[] deleteAllDuplicates(int[] arr){
        if(arr == null || arr.length <= 1){
            return arr;
        }

        int slow = 0;
        int fast = 1;
        while (fast < arr.length){
            if(arr[fast] == arr[slow]){
                while (arr[fast] == arr[slow] && fast < arr.length) fast++;
                if(fast == arr.length){
                    return arr;
                }

                Utilies.swap(arr, slow, fast++);
            }else {
                Utilies.swap(arr, ++slow, fast++);
            }
        }

        return arr;
    }

    /**
     * Assume that the size of the array is >1.
     * @param arr
     * @return
     */
    public int[] findLargestAndSmallest(int[] arr){
        int n = arr.length;
        for(int i =0; i < n/2; i++){
            if(arr[i] < arr[n-1-i]){
                Utilies.swap(arr, i, n-1-i);
            }
        }

        return new int[]{findLargest(arr, 0, n/2), findSmallest(arr, (n+1)/2, n-1)};
    }

    private int findLargest(int[] arr, int left, int right){
        int largest = arr[left];
        for(int i = left; i <= right; i++){
            largest = Math.max(largest, arr[i]);
        }

        return largest;
    }

    private int findSmallest(int[] arr, int left, int right){
        int smallest = arr[left];
        for(int i = left; i <= right; i++){
            smallest = Math.min(smallest, arr[i]);
        }

        return smallest;
    }

    public int[] findLargestAndSecondLargest(int[] arr){
        List[] M = new List[arr.length];
        for(int i = 0; i < arr.length; i++){
            List newList = new ArrayList();
            newList.add(arr[i]);
            M[i] = newList;
        }

        return helper(M, 0);
    }

    public int[] helper(List[] arr, int left){
        int right = arr.length - 1;
        if(left >= right){
            return getLargestAndSecond(arr[arr.length - 1]);
        }

        while (left < right){
            if((Integer) arr[left].get(0) > (Integer)arr[right].get(0)){
                arr[left].add(arr[right].get(0));
                Utilies.swap(arr, left, right);
            }else {
                arr[right].add(arr[left].get(0));
            }
            left++;
            right--;
        }

        return helper(arr, left);
    }

    private int[] getLargestAndSecond(List arr){
        Object secondLarge = arr.get(1);
        for(int i = 2; i < arr.size(); i++){
            secondLarge = Math.max((Integer) secondLarge, (Integer) arr.get(i));
        }

        return new int[]{(Integer)arr.get(0), (Integer) secondLarge};
    }

    /**
     * Matrix is N X N
     * Two hard points here:
     * 1. how to determine the pair of i and right-(i-level)
     * 2. how to properly copy elements from a circle direction
     * @param matrix
     */
    public int[][] rotateMatrix(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return matrix;
        }
        int n = matrix.length;
        int offset = matrix.length - 1;
        for(int level = 0; level < n/2; level++){
            int right = level + offset;

            for(int i = level; i < right; i++){
                int tmp = matrix[level][i];
                // copy top
                matrix[level][i] = matrix[right-i+level][level];
                // copy left
                matrix[right-i+level][level] = matrix[right][right-i+level];
                // copy bottom
                matrix[right][right-i+level] = matrix[i][right];
                // copy right
                matrix[i][right] = tmp;
                Utilies.print2DArray(matrix);
            }
            offset-=2;
        }

        return matrix;
    }

    /**
     * Matrix N X N
     * flips twice and directly generate the result
     * @param matrix
     * @return
     */
    public int[][] rotateMatrixFlips(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return matrix;
        }

        Utilies.mirrorDiagonal(matrix);
        Utilies.mirrorX(matrix);

        return matrix;
    }
}
