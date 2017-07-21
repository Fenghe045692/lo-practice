package stu.jzhang.algorithm;

import stu.jzhang.algorithm.util.Utilies;

import java.util.Arrays;

/**
 * Hints:
 * 1. 一维的original data(such as a rope, one word), 求MAX or MIN
 *      1.1. if each of the smallest elements is identical/similar.
 *           1.1.1 a meter of rope
 *           2.2.2 a letter
 *          linear scan and look back
 *      1.2. if each of the smallest elements is not same:
 *           1.2.1 cut wood
 *           1.2.3 沙子归并
 *           从中心开花，[index = 0,1, ....N-1], for each M[i,j], we usually try out all the possible
 *           k(i<k<j, M[i,j] = Min(M[i,k] + /-/ + M[k,j])
 * Created by hellen on 6/22/17.
 */
public class DynamicProgramming {
    public static void main(String[] args){
        DynamicProgramming test = new DynamicProgramming();
//        System.out.println(test.cutWood(new int[]{1,2, 3, 4,5,6, 7,8,9}, 10));
//        System.out.println(test.longestCommonSubstring("student", "sweden"));
//        System.out.println(test.longestCommonSubsequence("student", "suldten"));
//        System.out.println(test.longestAscendingSubSequence(new int[]{100, 0, 200, 1, 3, 4}));
//        System.out.println(Arrays.binarySearch(new int[]{1, 3, 4, 7, 9}, 5));
//        System.out.println(test.pizzaPickUp(new int[]{2, 6, 2, 10}));
//        System.out.println(test.minCost(new int[]{4, 2, 2, 2, 4}));
        System.out.println(test.largestSubArrayWithK(new int[]{-1, -2, -3, -4, -5}, 2));
    }


    /**
     * 0   1   2     3      4 -> M
     * 0   2   4     7     10 -> cutsArr
     * 0 1 2 3 4 5 6 7 8 9 10 -> wood
     * @param cuts
     * @param length
     * @return
     */
    public int cutWood(int[] cuts, int length){
        int[][] M = new int[cuts.length + 2][cuts.length + 2];
        int[] cutsArr = new int[cuts.length + 2];

        // construct the extra array for calculation
        for(int i = 0; i < cuts.length; i++){
            cutsArr[i+1] = cuts[i];
        }
        cutsArr[cutsArr.length - 1] = length;

        // initialize size = 1
        for(int i = 0; i < cutsArr.length - 1; i++){
            M[i][i+1] = 0;
        }

        for(int i = 2; i < cutsArr.length; i++){
            for(int j =0; j < cutsArr.length && j+i < cutsArr.length; j++){
                int min = Integer.MAX_VALUE;
                for(int k = j+i-1; k > j; k--){
                    min = Math.min(min, M[j][k] + M[k][i+j]);
                }

                M[j][j+i] = cutsArr[i+j] - cutsArr[j] + min;
            }
        }

        return M[0][M.length - 1];
    }

    public int longestCommonSubstring(String str1, String str2){
        if(str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0){
            return 0;
        }
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m+1][n+1];

        int largest = 0;
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                    largest = Math.max(largest, dp[i][j]);
                }else{
                    dp[i][j] = 0;
                }
            }
        }

        return largest;
    }

    public int longestCommonSubsequence(String str1, String str2){
        if(str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0){
            return 0;
        }
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m+1][n+1];

        // dp[i][j] represents the longest common subSequence between str1 and str2,
        // it might or might not include the ith and jth character.
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[m][n];
    }

    // The key point is how to avoid duplicate item. If we found a duplicate item in the adjusted arr, we just drop it.
    // otherwise, we adjust the stack.
    public int longestAscendingSubSequence(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int top = 1;
        for(int i = 1; i < nums.length; i++){
            // PLS carefully read the documentation of this method binarySearch. The right bound is exclusive.
            int index = Arrays.binarySearch(nums, 0, top, nums[i]);
            if(index < 0) index = -(index+1);
            nums[index] = nums[i];
            if(index == top){
                top++;
            }
        }

        return top;
    }

    // The key point to how to simulate the process of selecting pizza
    public int pizzaPickUp(int[] pizzas){
        if(pizzas == null || pizzas.length == 0){
            return 0;
        }

        // dp[i][j] represents the largest amount picked between ith pizza and jth pizza(included)
        int[][] dp = new int[pizzas.length + 1][pizzas.length + 1];
        for(int i = 1; i <= pizzas.length; i++){
            dp[i][i] = 1;
        }

        for(int i = 1; i < pizzas.length; i++){
            dp[i][i+1] = Math.max(pizzas[i-1], pizzas[i]);
        }

        for(int i = 2; i <= pizzas.length -1; i++){
            for(int j = 1; j + i <= pizzas.length; j++){
                dp[j][j+i] = Math.max(pizzas[j-1] + (pizzas[j] > pizzas[i+j-1] ? dp[j+2][i+j] : dp[j+1][i+j-1]),
                        pizzas[i+j-1] + (pizzas[j-1] > pizzas[i+j-1] ? dp[j+1][i+j-1] : dp[j][i+j-2]));
            }
        }

        return dp[1][pizzas.length];
    }

    /**
     * The key point is how to break the larger problems into smaller problems.
     * @param stones
     * @return
     */
    public int minCost(int[] stones) {
        if(stones.length == 0){
            return 0;
        }

        // dp[i][j] represents the minimum cost between i+1th stone and j+1th stone.
        int[][] dp = new int[stones.length][stones.length];
        // base case dp[i][i] = 0. not cost needed to merge stone.
        for(int i = 0; i < stones.length; i++){
            dp[i][i] = 0;
        }

        // base case 2.
        for(int i = 0; i < stones.length - 1; i++){
            dp[i][i+1] = stones[i] + stones[i+1];
        }

        int[] sumDp = new int[stones.length];
        sumDp[0] = stones.length;

        // sumArray
        for(int i = 1; i < stones.length; i++){
            sumDp[i] += sumDp[i-1] + stones[i];
        }

        for(int i = 2; i < stones.length; i++){
            for(int j = 0; j + i < stones.length; j++){
                int min = Integer.MAX_VALUE;
                int lastNum = sumDp[i+j] - sumDp[j] + stones[j];
                // induction rule:
                //      dp[i][j] = min(dp[i][k] + dp[k+1][j] + lastNum) i <= k <=j
                for(int k = i + j; k > j; k--){
                    min = Math.min(min, dp[k][i+j] + dp[j][k-1] + lastNum);
                }
                dp[j][i+j] = min;
            }

        }
        return dp[0][stones.length - 1];
    }

    public int largestSubArrayWithK(int[] arr, int k){
        if(arr.length == 0 || k <= 0 || k > arr.length){
            return 0;
        }

        int[] sumDp = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            sumDp[i] = arr[i] + (i == 0 ? 0 : sumDp[i-1]);
        }

        int max = sumDp[k-1];
        // dp[i] represents the largest subarray with at k elements.
        int[] dp = new int[arr.length];
        dp[k-1] = sumDp[k-1];
        for(int i = k; i < arr.length; i++){
            // Two cases : 1. exactly k elements
            //              2. more than k elements
            dp[i] = Math.max(sumDp[i] - sumDp[i-k], dp[i-1] + arr[i]);
            max = Math.max(max, dp[i]);
        }

        return max;
    }
}
