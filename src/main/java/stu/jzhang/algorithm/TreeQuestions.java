package stu.jzhang.algorithm;

import stu.jzhang.algorithm.model.TreeNode;
import stu.jzhang.algorithm.util.Utilies;

import java.util.*;

/**
 * Created by hellen on 6/20/17.
 */
public class TreeQuestions {

    public static void main(String[] args){
        TreeQuestions test = new TreeQuestions();
//        System.out.println(test.maxPathSum(generateTree()));
        for(List<Integer> item : test.verticalPrint(test.generateVerticalTree2())){
            Utilies.printArrayList(item);
        }
    }

    /**
     * Deque class is used. addFirst and pollLast
     * @param root
     */
    public void printZigZag(TreeNode root){
        if(root == null){
            return;
        }

        int level = 1;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()){
            int size = queue.size();

            while (size-- > 0){
                TreeNode tmp;
                if(level++ % 2 == 0){
                    tmp = queue.pollLast();
                    if(tmp.left != null){
                        queue.addFirst(tmp.left);
                    }

                    if(tmp.right != null){
                        queue.addFirst(tmp.right);
                    }
                }else {
                    tmp = queue.pop();
                    if(tmp.left != null){
                        queue.add(tmp.left);
                    }

                    if(tmp.right != null){
                        queue.add(tmp.right);
                    }
                }
                System.out.println(tmp.val);
            }
        }
    }

    /**
     * Same as regular LCA.
     * @param root
     * @param nodes
     * @return
     */
    public TreeNode LCA(TreeNode root, Set<TreeNode> nodes){
        if(root == null || nodes.contains(root)){
            return root;
        }

        TreeNode left = LCA(root.left, nodes);
        TreeNode right = LCA(root.right, nodes);

        if(left != null && right != null){
            return root;
        }

        return left != null ? left : right;
    }

    public KTreeNode LCAKBinary(KTreeNode root, KTreeNode a, KTreeNode b){
        if(root == null || root == a || root == b){
            return root;
        }

        KTreeNode last = null;
        int counter = 0;
        for (KTreeNode node : root.children){
            KTreeNode tmp = LCAKBinary(node, a, b);
            if(tmp != null){
                counter++;
                last = tmp;
            }
        }

        if(counter == 2){
            return root;
        }

        return last;
    }

    public KTreeNode LCAKBinary(KTreeNode root, Set<KTreeNode> nodes){
        if(root == null || nodes.contains(root)){
            return root;
        }

        KTreeNode last = null;
        int counter = 0;
        for (KTreeNode node : root.children){
            KTreeNode tmp = LCAKBinary(node, nodes);
            if(tmp != null){
                counter++;
                last = tmp;
            }
        }

        if(counter >= 2){
            return root;
        }

        return last;
    }

    public int maxPathSum(TreeNode root){
        int[] max = new int[]{Integer.MIN_VALUE};
        helper(root, max);
        return max[0];
    }

    public int helper(TreeNode root, int[] max){
        if(root == null){
            return 0;
        }

        int leftPathSum = helper(root.left, max);
        int rightPathSum = helper(root.right, max);

        if(root.left == null){
            return rightPathSum + root.val;
        }else if(root.right == null){
            return leftPathSum + root.val;
        }

        max[0] = Math.max(max[0], leftPathSum + root.val + rightPathSum);
        return Math.max(leftPathSum, rightPathSum) + root.val;
    }

    public int maxPathSumII(TreeNode root){
        int[] max = new int[]{Integer.MIN_VALUE};
        helperII(root, max);
        return max[0];
    }

    public int helperII(TreeNode root, int[] max){
        if(root == null){
            return 0;
        }

        int leftPathSum = helper(root.left, max);
        int rightPathSum = helper(root.right, max);

        int newSum = Math.max(0, Math.max(leftPathSum, rightPathSum)) + root.val;
        max[0] = Math.max(max[0], Math.max(newSum, leftPathSum + root.val + rightPathSum));

        return newSum;
    }

    public int closestValue(TreeNode root, double target) {
        int[] closest = new int[]{root.val};
        helper(root, target, closest);
        return closest[0];
    }

    private void helper(TreeNode root, double target, int[] closest){
        if(root == null){
            return;
        }
        if(Math.abs(target - root.val) < Math.abs(target - closest[0])){
            closest[0] = root.val;
        }
        if(target - root.val > 0){
            helper(root.right, target, closest);
        }else{
            helper(root.left, target, closest);
        }
    }

    public TreeNode deleteNode(TreeNode root, int key){
        if(root == null){
            return null;
        }

        if(key > root.val){
            root.right = deleteNode(root.right, key);
        }else if(key < root.val){
            root.left = deleteNode(root.left, key);
        }else if(root.left == null && root.right == null){
            return null;
        }else if(root.left == null || root.right == null){
            return root.left == null ? root.right : root.left;
        }else{
            TreeNode smallest = findSmallest(root.right);
            root.val = smallest.val;
            root.right = deleteNode(root.right, smallest.val);
        }
        return root;
    }

    private TreeNode findSmallest(TreeNode root){
        if(root == null){
            return null;
        }

        while (root.left != null) root = root.left;

        return root;
    }


    private static TreeNode generateTree(){
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.left = left;
        root.right = right;

        return root;
    }

    public List<List<Integer>> verticalPrint(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if(root == null){
            return result;
        }
        Map<Integer, List<Integer>> map = new HashMap<>();

        // Record all the columns to make the traverse of the result easier
        int min = 0;
        int max = 0;

        // BFS all the nodes
        Queue<TreeNode> nodes = new LinkedList<>();
        // record the corresponding column
        Queue<Integer> columns = new LinkedList<>();

        nodes.offer(root);
        columns.offer(0);
        while (!nodes.isEmpty()){
            TreeNode tmp = nodes.poll();
            int col = columns.poll();

            if(!map.containsKey(col)){
                map.put(col, new ArrayList<>());
            }
            map.get(col).add(tmp.val);
            if(tmp.left != null){
                nodes.offer(tmp.left);
                columns.offer(col-1);
                min = Math.min(min, col-1);
            }

            if(tmp.right != null){
                nodes.offer(tmp.right);
                columns.offer(col+1);
                max = Math.max(max, col+1);
            }
        }

        for(int i = min; i <= max; i++){
            result.add(map.get(i));
        }

        return result;
    }



    private TreeNode generateVerticalTree(){
        TreeNode t1 = new TreeNode(7);
        TreeNode t2 = new TreeNode(4);
        TreeNode t3 = new TreeNode(6);
        TreeNode t4 = new TreeNode(5);
        TreeNode t5 = new TreeNode(12);
        TreeNode t6 = new TreeNode(9);
        TreeNode t7 = new TreeNode(8);
        TreeNode t8 = new TreeNode(-1);
        t1.left = t2;
        t1.right = t6;
        t2.left = t3;
        t2.right = t4;
        t4.left = t5;
        t6.left = t7;
        t6.right = t8;

        return t1;
    }

    /**
     *https://leetcode.com/problems/binary-tree-vertical-order-traversal/#/description
     *
     * @return
     */
    private TreeNode generateVerticalTree2(){
        TreeNode t1 = new TreeNode(3);
        TreeNode t2 = new TreeNode(9);
        TreeNode t3 = new TreeNode(8);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(0);
        TreeNode t6 = new TreeNode(2);
        TreeNode t7 = new TreeNode(1);
        TreeNode t8 = new TreeNode(7);
        TreeNode t9 = new TreeNode(5);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t5.right = t6;
        t3.left = t7;
        t3.right = t8;
        t7.left = t9;

        return t1;
    }

    public static class Element{
        int value;
        int level;

        public Element(int value, int level) {
            this.value = value;
            this.level = level;
        }

        @Override
        public String toString() {
           return String.valueOf(value);
        }
    }
    public class KTreeNode{
        int value;
        List<KTreeNode> children;
    }
}
