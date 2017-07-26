package stu.jzhang.algorithm.smallclass.wed;

import stu.jzhang.algorithm.util.Utilies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * String match : https://www.laioffer.com/singleblog.html?category=algorithm&id=9
 * Created by hellen on 7/24/17.
 */
public class Wed170712 {
    public static void main(String[] args){
        Wed170712 test = new Wed170712();
//        System.out.println(test.canIWin(11, 9, new HashMap<>()));
//        System.out.println(test.canIWinWithDp(10, 9));
//        System.out.println(test.canIWin(10, 9));
//        System.out.println(test.numberoOfSubsequence("rabbbit", "rabit"));
//        System.out.println(test.numDecodings("01"));
        for(List<Integer> item : test.combinationSum3(3, 7)){
            Utilies.printArrayList(item);
        }
    }

    public boolean canIWin(int target, int n, HashMap<Integer, Boolean> mem){
        if(mem.containsKey(target)){
            return mem.get(target);
        }
        if(target <= n){
            return true;
        }

        for(int i = 1; i <= n; i++){
            if(!canIWin(target - i, n, mem)){
                mem.put(target, Boolean.TRUE);
                return true;
            }
        }

        mem.put(target, Boolean.FALSE);
        return false;
    }

    public boolean canIWinWithDp(int target, int n){
        if(target <= 0){
            return false;
        }
        boolean[] dp = new boolean[target + 1];
        for(int i = 0; i <= n; i++){
            dp[i] = true;
        }

        for(int i = n+1; i <= target; i++){
            for(int j = 1; j <= n; j++){
                if(!dp[i - j]){
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[target];
    }

    /**
     * We only are allowed to pick one number from 1 .. n
     * @param target
     * @param n
     * @return
     */
    public boolean canIWin(int target, int n){
        return canIWin(target, n, new boolean[n+1]);
    }
    public boolean canIWin(int target, int n, boolean[] used){
        if(target <= n){
            return true;
        }

        for(int i = 1; i <= n; i++){
            if(used[i]){
                continue;
            }
            used[i] = true;
            if(!canIWin(target - i, n, used)){
                used[i] = false;
                return true;
            }
            used[i] = false;
        }

        return false;
    }

    // The key point is how to quickly find the character index with logN
    public boolean isSubsequence(String s, String t) {
        List<Integer>[] idx = new List[256];
        for(int i = 0; i < t.length(); i++){
            if(idx[t.charAt(i)] == null){
                idx[t.charAt(i)] = new ArrayList<>();
            }

            idx[t.charAt(i)].add(i);
        }

        int prev = 0;
        for(int i = 0; i < s.length(); i++){
            if(idx[s.charAt(i)] == null) return false;
            int j = Collections.binarySearch(idx[s.charAt(i)], prev);
            if(j < 0){
                j = -j - 1;
            }

            if(j == idx[s.charAt(i)].size()){
                return false;
            }

            prev = idx[t.charAt(i)].get(j) + 1;
        }
        return true;
    }

    /**
     * S : rabbbit T: rabit
     * TODO use a different way to define the dp state for solving the problem
     * @param s
     * @param t
     * @return
     */
    public int numberoOfSubsequence(String s, String t) {
        int m = s.length();
        int n = t.length();

        int[] dp = new int[n+1];
        dp[0] = 1;

        for(int i = 1; i <= m; i++){
            for(int j = n; j >= 1; j--){
                if(s.charAt(i-1) == t.charAt(j-1)){
                    dp[j] = dp[j-1] + dp[j];
                }else{
                    dp[j] = dp[j];
                }
            }
        }

        return dp[n];
    }

    public int numDecodings(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }

        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        for(int i = 1; i <= s.length(); i++){
            int factor = 1;
            int num = 0;
            for(int j = i; j >= 1; j--){
                if(s.charAt(j-1) != '0'){
                    num += (s.charAt(j-1) - '0') * factor;
                    factor *= 10;
                    dp[i] += dp[j-1];
                }
            }
        }

        return dp[s.length()];
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> rst = new ArrayList<>();
        if(k > n || k <= 0){
            return rst;
        }
        dfs(n, k, 1, 0, new ArrayList<>(), rst);
        return rst;
    }

    private void dfs(int n, int k, int curr, int index, List<Integer> path, List<List<Integer>> rst){
        if(index == k){
            if(n == 0){
                rst.add(new ArrayList<>(path));
            }
            return;
        }

        if(n <= 0 || curr > n){
            return;
        }

        path.add(curr);
        dfs(n - curr, k, curr+1, index + 1, path, rst);
        path.remove(path.size() - 1);
        dfs(n, k, curr+1, index, path, rst);
    }
}
