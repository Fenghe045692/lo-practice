package stu.jzhang.algorithm.smallclass.wed;

import stu.jzhang.algorithm.util.Utilies;

import java.util.*;

/**
 * Created by hellen on 7/19/17.
 */
public class Web170702DP {
    public static void main(String[] args){
        Web170702DP test = new Web170702DP();
//        for(List<Integer> arr : test.findAllLongestIncreasingSubSequence(new int[]{3, 1, 6, 7, 2, 4, 10}))
//            Utilies.printArrayList(arr);
//        Utilies.printArrayList(test.wordBreakII("aaa",
//                new ArrayList<>(Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"))));
//        System.out.println(test.longestValidParentheses("()()()()))((((()"));
        System.out.println((int)Math.sqrt(18));
    }

    /**
     * [3, 1, 6, 7, 2, 4, 10]
     * We can get our solutions when we construct the problem
     * @param arr
     * @return
     */
    List<List<Integer>> findAllLongestIncreasingSubSequence(int[] arr){
        List<List<Integer>> rst = new ArrayList<>();
        if(arr.length == 0){
            return rst;
        }
        // prev[i] represents all the last indexes that can be used to form the longest increasing subsequence.
        List[] prev = new List[arr.length];
        prev[0] = new ArrayList<>();

        int[] dp = new int[arr.length];
        dp[0] = 1;
        int max = 1;
        for(int i = 1; i < arr.length; i++){
            int currMax = 1;
            List<Integer> currPre = new ArrayList<>();
            for(int j = i - 1; j >= 0; j--){
                if(arr[j] < arr[i]){
                    if(dp[j] + 1 > currMax) {
                        currMax = dp[j] + 1;
                        currPre.clear();
                        currPre.add(j);
                    }else if (dp[j] + 1 == currMax){
                        currPre.add(0, j);
                    }
                }
            }

            dp[i] = currMax;
            prev[i] = currPre;
            if(currMax > max){
                max = currMax;
            }
        }

        for(int i = arr.length - 1; i >= 0; i--){
            if(dp[i] == max){
                List<Integer> path = new ArrayList<>();
                path.add(arr[i]);
                dfs(prev, arr, i, path, rst);
                path.remove(path.size() - 1);
            }
        }
        return rst;
    }

    /**
     * Find all the required output. It only contains the result that we are interested in.
     * @param helper
     * @param arr
     * @param i
     * @param path
     * @param rst
     */
    private void dfs(List[] helper, int[] arr, int i, List<Integer> path, List<List<Integer>> rst){
        if(helper[i].size() == 0){
            rst.add(new ArrayList<>(path));
            return;
        }

        for(Object curr : helper[i]){
            int next = (int) curr;
            path.add(arr[next]);
            dfs(helper, arr, next, path, rst);
            path.remove(path.size() - 1);
        }
    }

    /**
     * Time Limit Exceeded for some corner case.
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        List[] f = new List[s.length() + 1];
        f[0] = new ArrayList<Integer>();
        // First DP
        for(int i = 1; i <= s.length(); i++){
            List<String> prev = new ArrayList<>();
            for(String str: wordDict){
                if(str.length() <= i && f[i - str.length()] != null && s.substring(i-str.length(), i).equals(str)){
                    if(f[i - str.length()].size() == 0){
                        prev.add(str);
                    }else{
                        for(Object item : f[i - str.length()]){
                            prev.add(item + " " + str);
                        }
                    }
                }
            }
            f[i] = prev.size() == 0 ? null : prev;
        }
        return f[s.length()] == null ? new ArrayList<>() : new ArrayList<>(f[s.length()]);
    }

    /**
     * DFS + memorization is different from DP, since it can prune the branches.
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreakII(String s, List<String> wordDict) {
        List<String> rst = new ArrayList<>();
        DFSII(s, new ArrayList<>(), rst, wordDict);
        return rst;
    }

    // DFS function returns an array including all substrings derived from s.
    List<String> DFS(String s, List<String> wordDict, HashMap<String, LinkedList<String>>map) {
        if (map.containsKey(s))
            return map.get(s);

        LinkedList<String> res = new LinkedList<String>();
        if (s.length() == 0) {
            res.add("");
            return res;
        }
        for (String word : wordDict) {
            if (s.startsWith(word)) {
                List<String>sublist = DFS(s.substring(word.length()), wordDict, map);
                for (String sub : sublist)
                    res.add(word + (sub.isEmpty() ? "" : " ") + sub);
            }
        }
        map.put(s, res);
        return res;
    }

    // DFS function returns an array including all substrings derived from s.
    void DFSII(String s, List<String> path, List<String> result, List<String> wordDict) {

        if(s == null || s.length() == 0){
            result.add(getCombinationStr(path));
        }

        for (String word : wordDict) {
            if (s.startsWith(word)) {
                path.add(word);
                DFSII(s.substring(word.length()), path, result, wordDict);
                path.remove(path.size() - 1);
            }
        }
    }

    private String getCombinationStr(List<String> str){
        StringBuffer rst = new StringBuffer();
        for(String item : str){
                rst.append(rst.length() == 0 ? item : (" " + item));
        }

        return rst.toString();
    }

    /**
     * Be careful of the corner case
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }

        char[] str = s.toCharArray();
        int[] dp = new int[s.length()];
        int max = 0;
        for(int i = 1; i < str.length; i++){
            if(str[i] == '('){
                dp[i] = 0;
            }else{
                if(str[i-1] == '('){
                    dp[i] = (i-2 >= 0 ? dp[i-2] : 0) + 2;
                }else{
                    if(i-dp[i-1] - 1 >= 0){
                        dp[i] = str[i-dp[i-1] - 1] == '('
                                ? (dp[i-1] + 2 + (i-dp[i-1]-2 >= 0
                                ? dp[i-dp[i-1]-2] : 0)) : 0;
                    }else{
                        dp[i] = 0;
                    }
                }
            }
            max = Math.max(max, dp[i]);
        }

        return max;
    }

    /**
     * Time complexity : O(N*LogN)
     * @param envelopes
     * @return
     */
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes.length == 0 || envelopes[0].length == 0){
            return 0;
        }

        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0] != o2[0]){
                    return o1[0] - o2[0];
                }else{
                    return o2[1] - o1[1];
                }
            }
        });

        int[] cache = new int[envelopes.length];
        cache[0] = envelopes[0][1];
        int len = 1;
        for(int i = 1; i < envelopes.length; i++){
            int index = Arrays.binarySearch(cache, 0, len, envelopes[i][1]);
            if(index < 0){
                index = -(index + 1);
            }
            cache[index] =  envelopes[i][1];
            if(index == len){
                len++;
            }
        }

        return len;
    }
}
