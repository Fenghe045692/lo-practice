package stu.hellen.algorithm.SortSolutions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Implement merge sort, quick sort, selection sort.
 */
public class SortItems {
    /**
     * Merge Sort: 3, 0, 7, 10, 5, 21, 13, 15, 11
     */
    public int[] mergeSort(int[] arr){
        if(arr == null || arr.length == 0){
            return arr;
        }

        int[] helper = new int[arr.length];

        mergeSortS(arr, helper, 0, arr.length -1);

        return arr;
    }

    public void mergeSortS(int[] arr, int[] helper, int left, int right){
        if(left >= right){
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSortS(arr, helper, left, mid);
        mergeSortS(arr, helper, mid + 1, right);
        merge(arr, helper, left, right, mid);
    }

    public void merge(int[] arr, int[] helper, int left, int right, int mid){
        for(int i = 0; i <= right; i++){
            helper[i] = arr[i];
        }

        int l_helper = left;
        int r_helper = mid + 1;

        while(l_helper <= mid && r_helper <= right){
            if(helper[l_helper] < helper[r_helper]){
                arr[left++] = helper[l_helper++];
            }else{
                arr[left++] = helper[r_helper++];
            }
        }

        while(l_helper <= mid){
            arr[left++] = helper[l_helper++];
        }
    }


    /**
     *Quick Sort: 3, 0, 7, 10, 5, 21, 13, 15, 11
     */
    public int[] quickSort(int[] arr){
        if(arr == null || arr.length == 0){
            return arr;
        }

        quickSortS(arr, 0, arr.length - 1);

        return arr;
    }

    public void quickSortS(int[] arr, int left, int right){
        if(left >= right){
            return;
        }

        int pivot = partition(arr, left, right);

        quickSortS(arr, 0, pivot - 1);
        quickSortS(arr, pivot + 1, right);
    }

    public int partition(int[] arr, int left, int right){
        int partitionIndex = partitionIndex(left, right);
        int pivot = arr[partitionIndex];

        swap(arr, partitionIndex, right);

        int left_bound = left;
        int right_bound = right - 1;

        while(left_bound <= right_bound){
            if(arr[left_bound] < pivot){
                left_bound ++;
            }else if(arr[right_bound] > pivot){
                right_bound --;
            }else{
                swap(arr, left_bound, right_bound);
                left_bound ++;
                right_bound --;
            }
        }
        swap(arr, left_bound, right);

        return left_bound;
    }

    public int partitionIndex(int left, int right){
        return (int)Math.random() * (right - left + 1) + left;
    }


    /**
     * Selection Sort
     */



    /** implement selection sort by using three stacks*/


    /** implement selection sort by using two stacks*/


    /**
     * A1B2C3D4 --> ABCD1234
     */


    //TODO: ABCD1234 --> A1B2C3D4

    /**
     * rainbow(two pointers)
     * NOTE: check the boundary of array and pointers
     * 121122211 --> 111112222
     */
    public int[] rainBow(int arr[]){
        if(arr == null || arr.length == 0){
            return arr;
        }

        int i = 0;
        int j = arr.length - 1;

        while(i < j){
            if(arr[i] == 1){
                i ++;
            }else if(arr[j] == 2){
                j --;
            }else{
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        return arr;
    }

    public void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * rainbow(three pointers)
     * 121133223321331 --> 111112222333333
     */
    public int[] rainBowII(int[] arr){
        if(arr == null || arr.length == 0){
            return arr;
        }

        int i = 0;
        int j = 0;
        int k = arr.length - 1;

        while(i < k && j <= k){
            if(arr[j] == 1){
                swap(arr, i, j);

                if(arr[i] == 1){
                    i ++;
                }
                if(arr[j] == 1){
                    j++;
                }
            }else if(arr[j] == 2){
                j++;
            }else if(arr[k] == 3){
                k--;
            }else{
                swap(arr, j, k);
                k--;
            }
        }

        return arr;
    }


    /**
     * rainbow(four pointers)
     * 1211443322413 --> 1111222333444
     */



}
