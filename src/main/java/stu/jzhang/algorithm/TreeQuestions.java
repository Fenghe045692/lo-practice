package stu.jzhang.algorithm;

import sun.security.krb5.KrbTgsRep;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by hellen on 6/20/17.
 */
public class TreeQuestions {

    public static void main(String[] args){
        TreeQuestions test = new TreeQuestions();
        System.out.println(test.maxPathSum(generateTree()));
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
                System.out.println(tmp.value);
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

        max[0] = Math.max(max[0], leftPathSum + root.value + rightPathSum);
        return Math.max(leftPathSum, rightPathSum) + root.value;
    }

    private static TreeNode generateTree(){
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.left = left;
        root.right = right;

        return root;
    }

    public static class TreeNode{
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    public class KTreeNode{
        int value;
        List<KTreeNode> children;
    }
}
