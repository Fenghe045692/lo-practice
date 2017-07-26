package stu.jzhang.algorithm.smallclass.sun;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by hellen on 7/21/17.
 */
public class Sun170709 {
    public static void main(String[] args){
        Sun170709 test = new Sun170709();
//       System.out.println(test.evaluate(new double[]{1, 0.5, 2, -2}));
//        System.out.println(test.evaluate(new double[]{0, 0, 0, 0}));
//        System.out.println(test.calculate("3+2*2"));
        System.out.println(test.getLongestPalindromeSubseq("BBABCBCAB"));
    }

    /**
     * The available operator is *, +, -, (), (/ not included, it's very complicated to solve this problem)
     *
     * @param nums
     * @return
     */
    public double evaluate(double[] nums){
        if(nums.length == 0){
            return -1;
        }

        double[][] dpMax = new double[nums.length][nums.length];
        double[][] dpSmall = new double[nums.length][nums.length];
        for(int i = 0; i < nums.length; i++){
            dpMax[i][i] = nums[i];
            dpSmall[i][i] = nums[i];
        }

        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j + i < nums.length; j++){
                // avoid to use Double.MAX_VALUE to eliminate 0's corner case
                double currMax = dpMax[j][j+i-1] + dpMax[j][j+i];
                double currSmall = dpMax[j][j+i-1] + dpMax[j][j+i];
                for(int k = j+i; k > j; k--){
                    double leftM = dpMax[j][k-1];
                    double rightM = dpMax[k][j+i];
                    double leftS = dpSmall[j][k-1];
                    double rightS = dpSmall[k][j+i];
                    currMax = Math.max(currMax, Math.max(Math.max(leftM + rightM, leftM - rightS), Math.max(leftM * rightM,
                            Math.max(leftS* rightM, Math.max(leftS * rightS, leftM * rightS)))));
                    currSmall = Math.min(currSmall, Math.min(Math.min(leftS + rightS, leftS- rightM), Math.min(leftM * rightM,
                            Math.min(leftS* rightM, Math.min(leftS * rightS, leftM * rightS)))));
                }

                dpMax[j][j+i] = currMax;
                dpSmall[j][j+i] = currSmall;
            }
        }

        return dpMax[0][nums.length-1];
    }

    /**
     * The key point is lear to think over the questions by the reverse direction
     * @param s
     * @return
     */
    public int calculate(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }
        Deque<Integer> operands = new LinkedList<>();
        Deque<Character> operator = new LinkedList<>();
        int p = 0;
        while(p < s.length()){
            if(s.charAt(p) == '+' || s.charAt(p) == '-' || s.charAt(p) == '*' || s.charAt(p) == '/'){
                char currOp = s.charAt(p);
                while(!operator.isEmpty() && priority(currOp) <= priority(operator.peekLast())){
                    operands.offerLast(calculate(operator.pollLast(), operands.pollLast(), operands.pollLast()));
                }
                operator.offerLast(currOp);
                p++;
            }else if(s.charAt(p) == ' '){
                p++;
            }else{
                int k = p;
                while(k < s.length() && s.charAt(k) <= '9' && s.charAt(k) >= '0') k++;
                operands.offerLast(Integer.valueOf(s.substring(p, k)));
                p = k;
            }
        }
        while(!operator.isEmpty()){
            operands.offerLast(calculate(operator.pollLast(), operands.pollLast(), operands.pollLast()));
        }
        return operands.peek();
    }

    private int priority(char op){
        if(op == '+' || op == '-'){
            return 0;
        }else{
            return 1;
        }
    }

    private int calculate(char op, int opr1, int opr2){
        switch(op){
            case '+':
                return opr1 + opr2;
            case '-':
                return opr2 - opr1;
            case '*':
                return opr1 * opr2;
            case '/':
                return opr2 / opr1;
            default:
        }

        return -1;
    }

    public String getLongestPalindromeSubseq(String s) {
        if(s == null || s.length() == 0){
            return null;
        }
        int[][] dp = new int[s.length()][s.length()];
        // record if we matched the subSequence
        // 0 : matched 1 or 2 represents the path to match the string
        int[][] back = new int[s.length()][s.length()];
        int max = 1;
        int maxL = 0;
        int maxR = 0;
        for(int i = 0; i < s.length(); i++){
            for(int j = 0; j+i < s.length(); j++){
                if(s.charAt(j) == s.charAt(j+i)){
                    dp[j][i+j] = i < 2 ? i+1 : (dp[j+1][i+j-1] + 2);
                    back[j][i+j] = 0;
                }else{
                    dp[j][i+j] = i < 2 ? 1 : Math.max(dp[j][i+j-1], dp[j+1][i+j]);
                    if(dp[j][i+j-1] > dp[j+1][i+j]){
                        back[j][i+j] = 1;
                    }else{
                        back[j][i+j] = 2;
                    }
                }
                if(dp[j][i+j] > max){
                    max = dp[j][i+j];
                    maxL = j;
                    maxR = i+j;
                }
            }
        }

        StringBuffer rst = new StringBuffer();
        char old = ' ';
        while (maxL <= maxR){
            if(back[maxL][maxR] == 0){
                if(maxL == maxR){
                    old = s.charAt(maxL);
                }else {
                    rst.append(s.charAt(maxL));
                }
                maxL++;
                maxR--;
            }else if(back[maxL][maxR] == 1){
                maxR--;
            }else {
                maxL++;
            }
        }
        return rst.toString() + (old == ' ' ? "" : old) + rst.reverse().toString();
    }
}
