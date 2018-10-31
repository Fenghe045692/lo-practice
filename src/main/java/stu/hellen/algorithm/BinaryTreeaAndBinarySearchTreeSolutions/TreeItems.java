package stu.hellen.algorithm.BinaryTreeaAndBinarySearchTreeSolutions;

import java.util.*;

/**
 * This class will include all the tree questions
 */
public class TreeItems {
    TreeNode root;

    TreeItems(){
        root = null;
    }

    /**
     * preOrder traverse by using recursion, stack(no-recursion)
     */

    public List<Integer> preOrder(TreeNode root){
        List<Integer> result = new ArrayList<>();

        preOrderHelper(root, result);

        return result;
    }

    public void preOrderHelper(TreeNode root, List<Integer> result){
        if(root == null){
            return ;
        }

        result.add(root.val);
        preOrderHelper(root.left, result);
        preOrderHelper(root.right, result);
    }

    /**
     * preOrder non-recursion, stack
     */

    public List<Integer> preOrderII(TreeNode root){
        List<Integer> result = new ArrayList<>();

        if(root == null){
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();

        stack.push(root);

        while(!stack.empty()){
            TreeNode pNode = stack.pop();
            result.add(pNode.val);

            if(pNode.right != null){
                stack.push(pNode.right);
            }

            if(pNode.left != null){
                stack.push(pNode.left);
            }
        }

        return result;
    }

    /**
     * inOrder traverse by using recursion, stack(no-recursion)
     */
    public List<Integer> inOrder(TreeNode root){
        List<Integer> result = new ArrayList<>();

        inorderHelper(root, result);

        return result;
    }

    public void inorderHelper(TreeNode root, List<Integer> result){
        if(root == null){
            return;
        }

        inorderHelper(root.left, result);
        result.add(root.val);
        inorderHelper(root.right, result);
    }

    /**
     * inOrder non-recursion
     */

    public List<Integer> inOrderII(TreeNode root){
        List<Integer> result = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();

        while(root != null){
            stack.push(root);
            root = root.left;
        }

        while(!stack.empty()){
            TreeNode pNode = stack.pop();
            result.add(pNode.val);

            if(pNode.right == null){

            }else{

            }
        }

        return result;
    }

    /**
     * postOrder traverse by using recursion, stack(no-recursion)
     */

    public List<Integer> postOrder(TreeNode root){
        List<Integer> result = new ArrayList<>();

        postOrderHelper(root, result);

        return result;
    }

    public void postOrderHelper(TreeNode root, List<Integer> result){
        if(root == null){
            return;
        }

        postOrderHelper(root.left, result);
        postOrderHelper(root.right, result);
        result.add(root.val);
    }

    /**
     * postOrder non-recursion, stack, Deque
     */
    public List<Integer> postOrderII(TreeNode root){
        Deque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> result = new LinkedList<>();

        
        return result;
    }

}
