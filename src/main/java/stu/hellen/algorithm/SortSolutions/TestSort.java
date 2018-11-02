package stu.hellen.algorithm.SortSolutions;

import java.util.Arrays;

public class TestSort {
    SortItems sort = new SortItems();

    public void testMergeSort(){
        int[] arr = {3, 0, 7, 10, 5, 21, 13, 15, 11};
        System.out.println(Arrays.toString(sort.mergeSort(arr)));
    }

    public void testquickSort(){
        int[] arr = {3, 0, 7, 10, 5, 21, 13, 15, 11};
        System.out.println(Arrays.toString(sort.quickSort(arr)));
    }

    public void testRainBow(){
        //int[] arr = {};
        //int[] arr = null;
        int[] arr = {1,2,1,1,2,2,2,1,1};
        //int[] arr = {1,1,1,1,1,1,1,1,1};
        //int[] arr = {2,2,2,2,2,2,2,2,2};
        //int[] arr = {1,1,1,1,2,2,2,2,2};
        //int[] arr = {1,1,1,1,1,1};
        //int[] arr = {2};
        //int[] arr = {1,2};
//        int[] arr = {1};
        System.out.println(Arrays.toString(sort.rainBow(arr)));
    }

    public void testRainBowII(){
        int[] arr = {1,2,1,1,3,3,2,2,3,3,2,1,3,3,1};
        //int[] arr = {3,3,3,3,3,3,2,2,3,3,2,1,3,3,1};
//        int[] arr = {2,2,2,2,3,3,2,2,3,3,2,1,3,3,1};
        //int[] arr = {2,2,2,1,3,3,2,2,3,3,2,1,1,1,1};
        //int[] arr = {3,3,3,3,3,3};
        //int[] arr = {1,1,1,1,1,1};
        //int[] arr = {1,1,2,3};
        //int[] arr = {1,2};
        //int[] arr = {1,2,2};
//        int[] arr = {2};
        System.out.println(Arrays.toString(sort.rainBowII(arr)));
    }


    public static void main(String args[]){
        TestSort ts = new TestSort();

//        ts.testRainBow();
//        ts.testRainBowII();
//        ts.testMergeSort();
        ts.testquickSort();


    }
}
