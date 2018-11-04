package stu.hellen.algorithm.treesolutions;

public class TreeNode implements Comparable<TreeNode>{
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val){
        this.val = val;
    }

    @Override
    public int compareTo(TreeNode o) {
        return val - o.val;
    }
}
