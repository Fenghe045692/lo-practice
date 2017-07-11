package stu.jzhang.algorithm.smallclass.sat;

import stu.jzhang.algorithm.model.ListNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by hellen on 7/6/17.
 */
public class SAT160713 {

    public static void main(String[] args){
        SAT160713 test = new SAT160713();
//        System.out.println(test.isPalinDrome(test.generatePalindromicListNode()));
//        System.out.println(test.isPalindrome(4224));
        System.out.println(test.longestPalindromeSequenceWithRecursion("bbbbab"));
    }

    /**
     * We first find the middle node, while we save the previous nodes into stack.
     * Continue to search the listNode after middle node and compare it to the top node of the stack.
     * @param head
     * @return
     */
    boolean isPalinDrome(ListNode head){
        if(head == null){
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        Deque<ListNode> queue = new LinkedList<>();
        queue.offerLast(slow);
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            queue.offerLast(slow);
        }

        // if it is the odd number of items.
        if(fast != null){
            queue.peekLast();
        }
        while (!queue.isEmpty() && queue.peekLast().val == slow.val){
            queue.poll();
            slow = slow.next;
        }

        return queue.isEmpty();
    }

    /**
     * Determine if an integer is palindrome
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x<0 || (x!=0 && x%10==0)) return false;
        int rev = 0;
        while (x>rev){
            rev = rev*10 + x%10;
            x = x/10;
        }
        return (x==rev || x==rev/10);
    }

    private int lo, maxLen;

    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2)
            return s;

        for (int i = 0; i < len-1; i++) {
            extendPalindrome(s, i, i);  //assume odd length, try to extend Palindrome as possible
            extendPalindrome(s, i, i+1); //assume even length.
        }
        return s.substring(lo, lo + maxLen);
    }

    private void extendPalindrome(String s, int j, int k) {
        while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
            j--;
            k++;
        }
        if (maxLen < k - j - 1) {
            lo = j + 1;
            maxLen = k - j - 1;
        }
    }

    public String longestPalindromeWithDP(String s) {
        if(s == null || s.length() == 0){
            return null;
        }
        // dp[i][j] represents whether the s.substring(i,j] is palindrome
        boolean[][] dp = new boolean[s.length()][s.length()];
        int largest = 1;
        int left = 0;
        int right = 0;

        for(int i = 0; i < s.length(); i++){
            for(int j = 0; j+i < s.length(); j++){
                // induction rule
                dp[j][i+j] = s.charAt(j) == s.charAt(i+j) && (i < 2 || dp[j+1][i+j-1]);
                if(dp[j][i+j] && (i+1) > largest){
                    left = j;
                    right = i+j;
                    largest = i+1;
                }
            }
        }

        return s.substring(left, right+1);
    }

    public int longestPalindromeSequenceWithDP(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }
        int[][] dp = new int[s.length()][s.length()];

        for(int i = 0; i < s.length(); i++){
            for(int j = 0; j+i < s.length(); j++){
                if(s.charAt(j) == s.charAt(j+i)){
                    dp[j][i+j] = i < 2 ? i+1 : (dp[j+1][i+j-1] + 2);
                }else{
                    dp[j][i+j] = i < 2 ? 1 : Math.max(dp[j][i+j-1], dp[j+1][i+j]);
                }
            }
        }

        return dp[0][s.length() - 1];
    }

    /**
     * Recursion + memorizaiton
     * @param s
     * @return
     */
    public int longestPalindromeSequenceWithRecursion(String s) {
        return helper(s, new int[s.length()][s.length()],0, s.length() -1);
    }

    public int helper(String s, int[][] dp, int left, int right){
        if(left < 0 || left > right || right >= s.length()){
            return 0;
        }

        // already checked
        if(dp[left][right] != 0){
            return dp[left][right];
        }

        if(left == right){
            dp[left][right] = 1;
            return 1;
        }

        if(left + 1 == right){
            dp[left][right] = s.charAt(left) == s.charAt(right) ? 2 : 1;
            return dp[left][right];
        }

        if(s.charAt(left) == s.charAt(right)){
            dp[left][right] = 2 + helper(s, dp, left+1, right-1);
        }else{
            dp[left][right] = Math.max(helper(s, dp, left, right-1), helper(s, dp, left+1, right));
        }

        return dp[left][right];
    }

    private ListNode generatePalindromicListNode(){
        ListNode l1 = new ListNode(5);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(4);
        ListNode l4 = new ListNode(5);
        ListNode l5 = new ListNode(6);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        return l1;
    }
}
