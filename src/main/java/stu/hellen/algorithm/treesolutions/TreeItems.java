package stu.hellen.algorithm.treesolutions;

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

    public List<Integer> preOrderIII(TreeNode root){
        List<Integer> result = new ArrayList<>();

        Deque<TreeNode> stack = new ArrayDeque<>();

        TreeNode p = root;

        while(!stack.isEmpty() || p != null){
            if(p != null){
                stack.push(p);
                result.add(p.val);
                p = p.left;
            }else{
                TreeNode pNode = stack.pop();
                p = pNode.right;
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

            TreeNode p = pNode.right;

            while(p != null){
                stack.push(p);
                p = p.left;
            }


        }

        return result;
    }


    public List<Integer> inOrderIII(TreeNode root){
        List<Integer> result = new ArrayList<>();

        Deque<TreeNode> stack = new ArrayDeque<>();

        TreeNode p = root;

        while(!stack.isEmpty() || p != null){
            if(p != null){
                stack.push(p);
                p = p.left;
            }else{
                TreeNode pNode = stack.pop();
                result.add(pNode.val);

                p = pNode.right;
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
        LinkedList<Integer> result = new LinkedList<>();

        if(root == null){
            return result;
        }

        stack.push(root);

        while(!stack.isEmpty()){
            TreeNode pNode = stack.pop();
            result.addFirst(pNode.val);

            if(pNode.left != null){
                stack.push(pNode.left);
            }

            if(pNode.right != null){
                stack.push(pNode.right);
            }

        }

        return result;
    }

    public List<Integer> postOrderIII(TreeNode root){
        Deque<TreeNode> stack = new ArrayDeque<>();
        LinkedList<Integer> result = new LinkedList<>();

        TreeNode p = root;

        while(!stack.isEmpty() || p != null){
            if(p != null){
                stack.push(p);
                result.addFirst(p.val);
                p = p.right;
            }else{
                TreeNode pNode = stack.pop();
                p = pNode.left;
            }
        }

        return result;
    }

    /**
     * balance binary Tree
     */
    public boolean isBalanceTree(TreeNode root){
        if(root == null){
            return true;
        }

        if(Math.abs(treeHeight(root.left) - treeHeight(root.right)) > 1){
            return false;
        }

        return isBalanceTree(root.left) & isBalanceTree(root.right);
    }

    public int treeHeight(TreeNode root){
        if(root == null){
            return 0;
        }

        return Math.max(treeHeight(root.left), treeHeight(root.right)) + 1;
    }

    /**
     * invertTree
     */

    public TreeNode invertTree(TreeNode root){
        if(root == null){
            return null;
        }

        if(root.left != null || root.right != null){
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
        }

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    /**
     * Binary Tree Level Order Travels
     */

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
//        List<Integer> list = new ArrayList<>(); shouldn't be implemented outside.

        if(root == null){
            return result;
        }

        Deque<TreeNode> deque = new LinkedList<>();

        deque.addFirst(root);

        while(!deque.isEmpty()){
            List<Integer> list = new ArrayList<>();
            int size = deque.size();

            while(size-- != 0){
                TreeNode pNode = deque.pollLast();
                list.add(pNode.val);

                if(pNode.left != null){
                    deque.addFirst(pNode.left);
                }

                if(pNode.right != null){
                    deque.addFirst(pNode.right);
                }
            }

            result.add(list);
        }

        return result;
    }



}
