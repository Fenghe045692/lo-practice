package stu.jzhang.algorithm.smallclass.wed;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by hellen on 7/26/17.
 */
public class Wed170719 {
    public static void main(String[] args){
        Wed170719 test = new Wed170719();
//        System.out.println(test.minJump(new int[]{5, 1, 1, 0, 2}, 2));
        System.out.println(test.minCut("aab"));
    }

    /**
     * Array Hopper problem
     * @param array
     * @param index
     * @return
     */
    public int minJump(int[] array, int index){
        if(array.length <= 1 || index == array.length - 1){
            return 0;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(index);
        int level = 0;

        // this is for optimization to add valid nodes
        int rightMax = index;
        int leftMin = index;
        while(!queue.isEmpty()){
            level++;
            int size = queue.size();
            while (size-- > 0){
                int curr = queue.poll();
                if(curr + array[curr] >= array.length - 1){
                    return level;
                }

                // add left children nodes
                for(int i = leftMin-1; i >= curr - array[curr] && i >= 0;i--){
                    queue.add(i);
                }
                leftMin = Math.min(leftMin, curr - array[curr]);

                // add right children nodes
                for(int i = rightMax+1; i <= array[curr]+curr; i++){
                    queue.add(i);
                }
                rightMax = Math.max(rightMax, array[curr] + curr);
            }

        }
        return -1;
    }

    public int minCut(String s) {
        if(s == null || s.length() <= 1){
            return 0;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int i = 0; i < s.length(); i++) {
            for(int j = 0; j <= i; j++) {
                if(s.charAt(i) == s.charAt(j) && (i - j <= 1 || dp[j+1][i-1])) {
                    dp[j][i] = true;
                }
            }
        }

        int[] dpCuts = new int[s.length() + 1];
        for(int i = 2; i <= s.length(); i++){
            int min = i-1;
            for(int j = 0; j < i; j++){
                if(dp[j][i-1]){
                    min = Math.min(min, j == 0 ? 1 : dpCuts[j] + 1);
                }
            }

            dpCuts[i] = min;
        }

        return dpCuts[s.length()];
    }

    /**
     * A very classical way to deconstruct your ways to solve the problem
     * @param s
     * @return
     */
    public int minCutII(String s) {
        int n = s.length();
        int[] cut = new int[n+1];  // number of cuts for the first k characters
        for (int i = 0; i <= n; i++) cut[i] = i-1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; i-j >= 0 && i+j < n && s.charAt(i-j)==s.charAt(i+j) ; j++) // odd length palindrome
                cut[i+j+1] = Math.min(cut[i+j+1],1+cut[i-j]);

            for (int j = 1; i-j+1 >= 0 && i+j < n && s.charAt(i-j+1) == s.charAt(i+j); j++) // even length palindrome
                cut[i+j+1] = Math.min(cut[i+j+1],1+cut[i-j+1]);
        }
        return cut[n];
    }
}
