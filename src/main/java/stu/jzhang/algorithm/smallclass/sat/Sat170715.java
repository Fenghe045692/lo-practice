package stu.jzhang.algorithm.smallclass.sat;

import stu.jzhang.algorithm.util.Utilies;

/**
 * Created by hellen on 7/14/17.
 */
public class Sat170715 {
    public static void main(String[] args){
        Sat170715 test = new Sat170715();
//        System.out.println(test.isMatch("a", "a*"));
        System.out.println(test.isMatchDFS("aab", "c*a*b"));
    }

    /**
     * 记忆化搜索看似只是小小的优化，但对运行时间却能有巨大的提升。不同子问题的个数总共有O(mn)个，每个子问题只计算一遍。
     * 每个子问题的开销不包含任何循环，所以只有O(1)。所以总的时间复杂度变成了O(mn)。记忆化搜索分配了O(nm)的空间，换来的运行时间的提升，
     * 是一个空间换时间的策略。 仔细思考不难观察出来，分治法它本质上是在一个图上做一个深度优先搜索，
     * 而记忆化搜索本质上是按照这个图的拓扑顺序（topological order）的逆序填表。如果我们能用简单的循环描述出图的拓扑顺序，
     * 那么就可以用迭代的形式来完成填表的任务，这就是著名的动态规划。
     * https://www.laioffer.com/singleblog.html?category=algorithm&id=9
     * @param str1
     * @param str2
     * @return
     */
    public boolean isExpresionMatch(String str1, String str2){
//        if(str1 == null || str2 == null){
//            return false;
//        }
//
//        int m = str1.length();
//        int n = str2.length();
//        // dp[i][j] represents whether p.substring(0, j] can match s.substring(0, i]
//        boolean[][] dp = new boolean[m+1][n+1];
//        dp[0][0] = true;
//        for(int i = 1; i <= m; i++){
//            for(int j = 1; j <= n; j++){
//                // induction rule
//                // 1. str2[j-1] == '.' : dp[i-1][j-1]
//                // 2. str2[j-1] == '*' :
//                //  2.1 if str2[j-2] == '.' or str[j-2] == str1[i-1]
//                //       dp[i][j] = dp[i-1][j](multiple str2[j-2]) || dp[i][j-1](one) || dp[i][j-2](zero)
//                //  2.2 str[j-2] != str1[i-1] dp[i][j] = dp[i][j-2](zero str2[j-2])
//                // 3. str2[j-1] is a letter dp[i][j] = str1[i-1] == str2[j-1] && dp[i-1][j-1]
//                if(str2.charAt(j-1) == '.'){
//                    dp[i][j] = dp[i-1][j-1];
//                }else if(str2.charAt(j-1) == '*'){
//                    if(str2.charAt(j-2) == '.' || str2.charAt(j-2) == str1.charAt(i-1)){
//                        dp[i][j] = dp[i-1][j] || dp[i][j-1] || dp[i][j-2];
//                    }else {
//                        dp[i][j] = dp[i][j-2];
//                    }
//                }else{
//                    dp[i][j] = str1.charAt(i-1) == str2.charAt(j-1) && dp[i-1][j-1];
//                }
//            }
//        }
//
//        return dp[m][n];
        if(str1 == null || str2 == null){
            return false;
        }

        int m = str1.length();
        int n = str2.length();
        boolean[][] dp = new boolean[2][n+1];
        for(int i = 0; i <= m; i++){
            for(int j = 0; j <= n; j++){
                if(j == 0){
                    dp[i%2][j] = i == 0;
                }else{
                    if(str2.charAt(j-1) == '*'){
                        dp[i%2][j] = dp[i][j-2] || (i != 0 && isMatch(str1.charAt(i-1), str2.charAt(j-2))
                                && dp[(i+1) % 2][j]);
                    }else{
                        dp[i%2][j] = i != 0 && isMatch(str1.charAt(i-1), str2.charAt(j-1)) && dp[(i+1)%2][j-1];
                    }
                }
            }
        }

        return dp[m%2][n];
    }

    /**
     * This dfs method is exactly the same as the dp induction rule above.
     * @param str1
     * @param str2
     * @return
     */
    public boolean isMatchDFS(String str1, String str2) {
        return dfs(str1, str2, new Boolean[str1.length() + 1][str2.length() + 1], 0, 0);
    }

    /**
     * Check if str1.substring(indexS) matches str2.substring(indexP).
     * ATTENTION: we do not need always to follow dp base case to define dfs' corner case
     * since we can directly give the output.
     * @param str1
     * @param str2
     * @param mem
     * @param i
     * @param j
     * @return
     */
    public boolean dfs(String str1, String str2, Boolean[][] mem, int i, int j){
        if(mem[i][j] != null){
            return mem[i][j];
        }

        if(j == str2.length()){
            return i == str1.length();
        }
        char c = str2.charAt(j);
        if(j+1 < str2.length() && str2.charAt(j+1) == '*'){
            mem[i][j] = dfs(str1, str2, mem, i, j+2) ||
                    (i < str1.length() && isMatch(str1.charAt(i), str2.charAt(j)) &&
                            dfs(str1, str2, mem, i+1, j));
            return mem[i][j];

        }

        mem[i][j] = i < str1.length() && isMatch(str1.charAt(i), str2.charAt(j)) && dfs(str1, str2, mem, i+1, j+1);
        return mem[i][j];
}

    private boolean isMatch(char a, char b){
        return b == '.'  || a == b;
    }
    public boolean isMatch(String str1, String str2){
        if(str1 == null || str2 == null){
            return false;
        }
        String compactMatch = compactStrPattern(str2);
        int m = str1.length();
        int n = compactMatch.length();
        // dp[i][j] represents whether p.substring(0, j] can match s.substring(0, i]
        boolean[][] dp = new boolean[m + 1][n + 1];

        // base case
        dp[0][0] = true;

        // dp[0][j] by default false
        if(compactMatch.length() != 0 && compactMatch.charAt(0) == '*'){
            dp[0][1] = true;
        }
        for(int i = 1; i <=m ; i++){
            for(int j = 1; j <= n; j++){

                // induction rule
                if(compactMatch.charAt(j-1) == '*'){
                    dp[i][j] = dp[i][j-1] || dp[i-1][j];
                }else if(compactMatch.charAt(j-1) == '?'){
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    dp[i][j] = str1.charAt(i-1) == compactMatch.charAt(j-1) && dp[i-1][j-1];
                }
            }
        }

        return dp[m][n];
    }

    private String compactStrPattern(String str2){
        if(str2 == null || str2.length() == 0){
            return str2;
        }

        StringBuffer res = new StringBuffer();
        res.append(str2.charAt(0));
        int pos = 1;
        while (pos < str2.length()){
            if(str2.charAt(pos) != '*' || str2.charAt(pos) != res.charAt(res.length() - 1)){
                res.append(str2.charAt(pos));
            }
            pos++;
        }

        return res.toString();
    }

//    public boolean dfs(String str1, String str2, int level, int matchPos){
//        if(level == str1.length() && matchPos == str2.length()){
//            return true;
//        }
//
//        if(level > str1.length() || matchPos >= str2.length()){
//            return false;
//        }
//
//        char matchChar = str2.charAt(matchPos);
//        if(matchChar == '?'){
//            return dfs(str1, str2, level+1, matchPos+1);
//        }else if(matchChar == '*'){
//            for(int i = 0; i <= str1.length() - level; i++){
//                if(dfs(str1, str2, level + i, matchPos+1)){
//                    return true;
//                }
//            }
//            return false;
//        }else{
//            return level < str1.length() && str1.charAt(level) == str2.charAt(matchPos) && dfs(str1, str2, level+1, matchPos+1);
//        }
//    }

    /**
     * Example:
     *                          preS		   indexS
     *  d	    b	c	e	    f	    g		s	    d	f	h	j
     *
     *  d	    ?	*	f	    g	    *		f	?	j	*	*
     *                preP          indexP
     * @param s
     * @param p
     * @return
     */
    public boolean isMatchWithPointer(String s, String p){
        if(s == null || p == null){
            return false;
        }

        int lenS = s.length();
        int lenP = p.length();

        int indexS = 0;
        int indexP = 0;

        // record the last matched position
        int preS = 0;
        int preP = 0;

        // record if there is a * match before
        boolean back = false;
        while (indexS < lenS){
            if(indexP < lenP && matchChar(s.charAt(indexS), p.charAt(indexP))){
                indexP++;
                indexS++;
            }else if(indexP < lenP && p.charAt(indexP) == '*'){
                while (indexP < lenP && p.charAt(indexP) == '*') indexP++;

                // it matches all the character
                if(indexP == lenP){
                    return true;
                }

                back = true;
                preS = indexS;
                preP = indexP;

            }else if(back){
                indexS = ++preS;
                indexP = preP;
            }else{
                return false;
            }
        }

        // remove all the left *
        while (indexP < lenP && p.charAt(indexP) == '*') indexP++;

        return indexP == lenP;
    }

    private boolean matchChar(char c, char p) {
        return (p == '?' || p == c);
    }
}
