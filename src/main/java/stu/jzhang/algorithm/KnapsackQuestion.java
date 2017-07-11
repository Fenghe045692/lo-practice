package stu.jzhang.algorithm;

/**
 * Created by hellen on 7/7/17.
 */
public class KnapsackQuestion {
    public static void main(String[] args){
        KnapsackQuestion test = new KnapsackQuestion();
//        System.out.println(test.getMaxTotalWeight(new int[]{4, 3, 5, 9, 10}, 50));
            System.out.println(test.getMinimumNumberOfTotalWeight(new int[]{3, 6}, 12));
//        System.out.println(test.getMaxTotalValueOfTotalWeight(new int[]{4, 3, 5}, new int[]{3,2,1}, 9));
    }

    public int getMaxTotalWeight(int[] weights, int W){
        if(W <=0 || weights.length == 0){
            return 0;
        }

        int[][] dp = new int[weights.length + 1][W+1];
        // dp[i][j] represents the max total weight formed by the first i nums.
        for(int i = 1; i <= weights.length; i++){
            for(int j = 1; j <= W; j++){
                dp[i][j] = Math.max(dp[i-1][j],
                        j < weights[i-1] ? dp[i-1][j] : (dp[i-1][j-weights[i-1]] + weights[i-1]));
            }
        }

        return dp[weights.length][W];
    }

    public int getMinimumNumberOfTotalWeight(int[] coins, int amount){
        if(amount <=0 || coins.length == 0){
            return 0;
        }

        int[][] dp = new int[coins.length + 1][amount+1];
        // we need to mark dp[0][i] (i 1->W) minimum number of item as infinite,
        // since there is impossible to have one combination to generate the num.
        for(int i = 1; i <= amount; i++){
            dp[0][i] = Integer.MAX_VALUE;
        }
        // dp[i][j] represents the minimum of num total weight formed by the first i nums.
        for(int i = 1; i <= coins.length; i++){
            for(int j = 1; j <= amount; j++){
                dp[i][j] = dp[i-1][j];
                if(j >= coins[i-1] && dp[i][j-coins[i-1]] != Integer.MAX_VALUE){
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-coins[i-1]] + 1);
                }
            }
        }

        return dp[coins.length][amount] == Integer.MAX_VALUE ? -1 : dp[coins.length][amount] ;
    }

    public int getMaxTotalValueOfTotalWeight(int[] weights, int[] values, int W){
        if(W <=0 || weights.length == 0){
            return 0;
        }

        int[][] dp = new int[weights.length + 1][W+1];
        // we need to mark dp[0][i] (i 1->W) minimum number of item as infinite,
        // since there is impossible to have one combination to generate the num.
        for(int i = 1; i <= W; i++){
            dp[0][i] = Integer.MIN_VALUE;
        }
        // dp[i][j] represents the minimum of num total weight formed by the first i nums.
        for(int i = 1; i <= weights.length; i++){
            for(int j = 1; j <= W; j++){
                dp[i][j] = dp[i-1][j];
                if(j >= weights[i-1] && dp[i-1][j-weights[i-1]] != Integer.MIN_VALUE){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weights[i-1]] + values[i-1]);
                }
            }
        }

        return dp[weights.length][W];
    }
}
