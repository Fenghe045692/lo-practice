package stu.jzhang.algorithm;

import stu.jzhang.algorithm.model.TreeNode;
import stu.jzhang.algorithm.util.Utilies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by hellen on 6/20/17.
 */
public class Recursions {
    public static void main(String[] args) {
        Recursions test = new Recursions();
//        test.printBlocks(3);
//        System.out.println(test.isValidSerialization("9,3,4,#,#,1,#,#,2,#,6,#,#"));
//        for(List<Character> list : test.allSubsets(new char[]{'a', 'b', 'b', 'b', 'c'})){
//            Utilies.printArrayList(list);
//        }
//        for (List<Integer> list : test.kCombinations(new int[]{1, 1, 2}, 2)) {
//            Utilies.printArrayList(list);
//        }

//        Utilies.printArrayList(test.allPermutations("bbbc"));
//        for(List<Integer> list : test.allSubsetsII(new int[]{1,1,1,2,3})){
//            Utilies.printArrayList(list);
//        }
//        Utilies.printArrayList(test.coinsOfCombinations(new int[]{25, 10, 5, 1}, 100));
        Utilies.printArrayList(test.factors(12));
    }

    /**
     * {{}}{{}}
     *
     * @param n
     */
    public void printBlocks(int n) {
        List<String> result = new ArrayList<>();
        StringBuffer path = new StringBuffer();
        helper(n, 0, result, path, 0, 0);
    }

    private void helper(int n, int level, List<String> result, StringBuffer path, int left, int right) {
        // Base case
        if (level >= 2 * n) {
            result.add(path.toString());
            System.out.println(path.toString());
            return;
        }

        if (left < right) {
            return;
        }

        // recursive rule
        // Only one space to add possible elements
        if (left < n) {
            path.append("{");
            helper(n, level + 1, result, path, left + 1, right);
            path.deleteCharAt(path.length() - 1);
        }

        if (right < n) {
            path.append("}");
            helper(n, level + 1, result, path, left, right + 1);
            path.deleteCharAt(path.length() - 1);
        }
    }

    public boolean isBalanced(TreeNode root) {
        int height = getHeight(root);
        return height >= 0;
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = getHeight(root.left);
        int right = getHeight(root.right);

        if (left == -1 || right == -1) {
            return -1;
        }

        if (Math.abs(left - right) > 1) {
            return -1;
        }

        return Math.max(left, right) + 1;
    }

    /**
     * check if there is a preSum path(must be on the path from to root to leaf node) that equals to target.
     *
     * @param root
     * @param target
     * @return
     */
    public boolean exist(TreeNode root, int target) {
        HashSet<Integer> preSum = new HashSet<Integer>();
        preSum.add(0);
        return exist(root, target, 0, preSum);
    }

    private boolean exist(TreeNode root, int target, int currSum, HashSet<Integer> preSum) {
        if (root == null) {
            return false;
        }
        int newSum = currSum + root.val;
        boolean needRemove = preSum.add(newSum);

        if (preSum.contains(newSum - target)) {
            return true;
        }
        if (exist(root.left, target, newSum, preSum) || exist(root.right, target, newSum, preSum)) {
            return true;
        }

        if (needRemove) {
            preSum.remove(newSum);
        }
        return false;
    }

    /**
     * The maximum sum path from root to one of leaf nodes. Top-bottom
     *
     * @param root
     * @return
     */
    public int maxSumPathIII(TreeNode root) {
        int[] max = new int[1];
        helper(root, 0, max);
        return max[0];
    }

    public void helper(TreeNode root, int sum, int[] max) {
        if (root == null) {
            return;
        }

        sum = Math.max(root.val, root.val + sum);
        max[0] = Math.max(max[0], sum);
        helper(root.left, sum, max);
        helper(root.right, sum, max);
    }

    public TreeNode binaryTree2DoubleLinkedList(TreeNode root) {
        // node[0] represents head, node[1] represents pre
        TreeNode[] node = new TreeNode[2];
        helper(root, node);
        return node[0];
    }

    public void helper(TreeNode root, TreeNode[] node) {
        if (root == null) {
            return;
        }

        helper(root.left, node);
        TreeNode prev = node[1];
        if (prev == null) {
            node[0] = root;
        } else {
            prev.right = root;
            root.left = prev;
        }
        node[1] = root;
        helper(root.right, node);
    }

    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.equals("")) {
            return false;
        }
        String[] arr = preorder.split(",");
        int last = helper(arr, 0);
        return last == arr.length - 1;
    }

    public int helper(String[] preorder, int index) {
        if (index >= preorder.length) {
            return -1;
        }
        if (preorder[index].equals("#")) {
            return index;
        }

        int leftMost = helper(preorder, index + 1);

        if (leftMost == -1) {
            return -1;
        }
        return helper(preorder, leftMost + 1);
    }

    /**
     * key point: how to define each level and how many state you will put on each level
     * n levels + binary tree
     */
    public List<List<Character>> allSubsets(char[] arr) {
        List<List<Character>> res = new ArrayList<>();
        if (arr == null || arr.length == 0) {
            return res;
        }

        dfs(arr, 0, res, new ArrayList<>());

        return res;
    }

    public void dfs(char[] arr, int level, List<List<Character>> res, List<Character> path) {
        // base case
        if (level >= arr.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        path.add(arr[level]);
        dfs(arr, level + 1, res, path);
        path.remove(path.size() - 1);
        // we skip all the duplicated items to make sure we put number of duplicate item once
        while (level < arr.length - 1 && arr[level + 1] == arr[level]) level++;
        dfs(arr, level + 1, res, path);
    }

    /**
     * each level forms one kind of subsets and n-nary trees,
     *
     * @param arr
     * @return
     */
    public List<List<Integer>> allSubsetsII(int[] arr) {
        List<List<Integer>> res = new ArrayList<>();
        if (arr == null || arr.length == 0) {
            return res;
        }

        dfs(arr, 0, new ArrayList<>(), res);
        return res;
    }

    /**
     * Each level will generate one subset. we will always sort the original array to avoid duplicate items. The path direction is
     * always performing from left to right.
     *
     * @param arr
     * @param index
     * @param path
     * @param res
     */
    private void dfs(int[] arr, int index, List<Integer> path, List<List<Integer>> res) {
        res.add(new ArrayList<>(path));

        for (int i = index; i < arr.length; i++) {
            if (i != index && arr[i] == arr[i - 1]) {
                continue;
            }
            path.add(arr[i]);
            dfs(arr, i + 1, path, res);
            path.remove(path.size() - 1);
        }
    }

    /**
     * We need to make sure that we only put duplicate items once on each level. Swap changed the array's order, so we cannot
     * just depend on the swaped array to avoid duplicate. One extra boolean[] or HashSet will be needed to avoid duplicates.
     * n levels + n-nary tree
     *
     * @param str
     * @return
     */
    public List<String> allPermutations(String str) {
        List<String> res = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return res;
        }

        helper(str.toCharArray(), 0, res);

        return res;
    }

    public void helper(char[] str, int index, List<String> res) {
        if (index >= str.length) {
            res.add(new String(str));
            return;
        }

        HashSet<Character> set = new HashSet<>();
        // n-nary tree
        for (int i = index; i < str.length; i++) {
            if (set.add(str[i])) {
                Utilies.swap(str, index, i);
                helper(str, index + 1, res);
                Utilies.swap(str, index, i);
            }
        }
    }

    /**
     * Assumption: no duplicates KCombinations is just subset problems.
     *
     * @param arr
     * @return
     */
    public List<List<Integer>> kCombinations(int[] arr, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) {
            return res;
        }

        dfs(arr, k, 0, res, new ArrayList<>());

        return res;
    }

    /**
     * SubSet problem
     * @param arr
     * @param k
     * @param index
     * @param res
     * @param path
     */
    private void dfs(int[] arr, int k, int index,  List<List<Integer>> res, List<Integer> path) {
        // add the base case into the result
        if(path.size() == k){
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = index; i < arr.length; i++) {
            if (i != index && arr[i] == arr[i - 1]) {
                continue;
            }
            path.add(arr[i]);
            dfs(arr, k, i + 1, res, path);
            path.remove(path.size() - 1);
        }
    }

    /**
     * Each dfs tree map will have its own special potential condition that can be used to simplify the code logic.
     * @param coins
     * @param target
     * @return
     */
    public List<List<Integer>> coinsOfCombinations(int[] coins, int target){
        List<List<Integer>> res = new ArrayList<>();
        if(coins == null || coins.length == 0){
            return res;
        }
        dfss(coins, 0, target, new ArrayList<>(), res);
        return res;
    }

    private void dfss(int[] coins, int level, int target, List<Integer> path, List<List<Integer>> res){
        if(target == 0){
            res.add(new ArrayList<>(path));
            return;
        }

        if(level >= coins.length){
            return;
        }
        int num = target / coins[level];
        for(int i =0; i <= num; i++){
            path.add(i);
            dfss(coins, level + 1, target - coins[level] * i, path, res);
            path.remove(path.size() - 1);
        }
    }

    /**
     * Usually we always use the factors as each level to do the dfs.
     * @param num
     * @return
     */
    public List<List<String>> factors(int num){
        List<List<String>> res = new ArrayList<>();
        List<Integer> factors = getFactors(num);
        if(factors.size() == 0){
            return res;
        }
        dfs(factors, 0, num, new ArrayList<>(), res);
        return res;
    }


    private void dfs(List<Integer> coins, int level, int target, List<String> path, List<List<String>> res){
        if(target == 1){
            res.add(new ArrayList<>(path));
            return;
        }

        if(level >= coins.size()){
            return;
        }
        int num = 0;
        int tmp = target;
        // determine how many of this current factors we can put on each level.
        while (tmp !=0 && tmp/coins.get(level) != 0 && tmp % coins.get(level) == 0){
            tmp /= coins.get(level);
            num++;
        }
        // n-nary tree
        for(int i =0; i <= num; i++){
            path.add(i + "*" + coins.get(level));
            dfs(coins, level + 1, i == 0 ? target : target / coins.get(level) / i, path, res);
            path.remove(path.size() - 1);
        }
    }

    private List<Integer> getFactors(int num){
        List<Integer> factors = new ArrayList<>();
        for(int i =2; i < num; i++){
            if(num % i == 0){
                factors.add(i);
            }
        }

        return factors;
    }
}