package stu.jzhang.algorithm.smallclass.wed;

/**
 * DP is just recursion + memorization. For constructing all possible solutions, we pick the best one.
 * Created by hellen on 7/12/17.
 */
public class Wed170628DP {
    public static void main(String[] args){
        Wed170628DP test = new Wed170628DP();
        System.out.println(test.longestSubSequence(new int[]{1,3,6,7,9,4,10,5,6}));
    }

    /**
     * [1, 4, 2, 3, 5] recursion + memorization
     * @param nums
     * @return
     */
    public int longestSubSequence(int[] nums){
        int max = 0;
        int[] dp = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            max = Math.max(max, dfs(nums, dp, i));
        }

        return max;
    }

    private int dfs(int[] nums, int[] dp, int index){
        if(dp[index] != 0){
            return dp[index];
        }
        int max = 0;
        for(int i = index - 1; i >= 0; i--){
            if(nums[i] <= nums[index]){
                max = Math.max(max, dfs(nums, dp, i));
            }
        }
        dp[index] = max + 1;
        return ++max;
    }
}
