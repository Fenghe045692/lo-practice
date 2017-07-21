package stu.jzhang.algorithm.smallclass.sat;

import stu.jzhang.algorithm.util.Utilies;

/**
 * Created by hellen on 7/14/17.
 */
public class Sat170715 {
    public static void main(String[] args){
        Sat170715 test = new Sat170715();
//        System.out.println(test.isMatch("a", "a*"));
        System.out.println(test.isExpressionMatchDFS("aab", "c*a*b"));
    }

    public boolean isExpresionMatch(String str1, String str2){
        if(str1 == null || str2 == null){
            return false;
        }

        int m = str1.length();
        int n = str2.length();
        // dp[i][j] represents whether p.substring(0, j] can match s.substring(0, i]
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                // induction rule
                // 1. str2[j-1] == '.' : dp[i-1][j-1]
                // 2. str2[j-1] == '*' :
                //  2.1 if str2[j-2] == '.' or str[j-2] == str1[i-1]
                //       dp[i][j] = dp[i-1][j](multiple str2[j-2]) || dp[i][j-1](one) || dp[i][j-2](zero)
                //  2.2 str[j-2] != str1[i-1] dp[i][j] = dp[i][j-2](zero str2[j-2])
                // 3. str2[j-1] is a letter dp[i][j] = str1[i-1] == str2[j-1] && dp[i-1][j-1]
                if(str2.charAt(j-1) == '.'){
                    dp[i][j] = dp[i-1][j-1];
                }else if(str2.charAt(j-1) == '*'){
                    if(str2.charAt(j-2) == '.' || str2.charAt(j-2) == str1.charAt(i-1)){
                        dp[i][j] = dp[i-1][j] || dp[i][j-1] || dp[i][j-2];
                    }else {
                        dp[i][j] = dp[i][j-2];
                    }
                }else{
                    dp[i][j] = str1.charAt(i-1) == str2.charAt(j-1) && dp[i-1][j-1];
                }
            }
        }

        return dp[m][n];
    }

    /**
     * This dfs method is exactly the same as the dp induction rule above.
     * @param str1
     * @param str2
     * @return
     */
    public boolean isExpressionMatchDFS(String str1, String str2){
        int[][] mem = new int[str1.length() + 1][str2.length() + 1];

        boolean valid = dfs(str1, str2, mem, str1.length(), str2.length());
        Utilies.print2DArray(mem);
        return valid;
    }

    public boolean dfs(String str1, String str2, int[][] mem, int indexS, int indexP){
        // base case
        if(mem[indexS][indexP] != 0){
            return mem[indexS][indexP] == 2;
        }
        if(indexS == 0 && indexP == 0){
            mem[0][0] = 2;
            return true;
        }

        if(indexS == 0 && indexP == 1){
            mem[0][1] = 1;
            return false;
        }

        if(indexS == 0){
            boolean valid = str2.charAt(indexP-1) == '*' ? dfs(str1, str2, mem, 0, indexP - 2) : Boolean.FALSE;
            mem[0][indexP] = valid ? 2 : 1;
            return valid;
        }

        if(indexP == 0){
            mem[indexS][0] = 1;
            return false;
        }

        boolean valid;
        if(str2.charAt(indexP - 1) == '.'){
            valid = dfs(str1, str2, mem, indexS-1, indexP-1);
        }else if(str2.charAt(indexP - 1) == '*'){
            if(str2.charAt(indexP - 2) == '.' || str2.charAt(indexP - 2) == str1.charAt(indexS-1)){
                valid =  dfs(str1, str2, mem, indexS - 1, indexP) || dfs(str1, str2, mem, indexS, indexP - 1) ||
                        dfs(str1, str2, mem, indexS, indexP - 2);
            }else {
                valid = dfs(str1, str2, mem, indexS, indexP - 2);
            }
        }else{
            valid = str1.charAt(indexS - 1) == str2.charAt(indexP - 1) && dfs(str1, str2, mem, indexS - 1, indexP - 1);
        }

        mem[indexS][indexP] = valid ? 2 : 1;
        return valid;
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
