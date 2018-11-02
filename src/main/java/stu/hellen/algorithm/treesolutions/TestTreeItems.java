package stu.hellen.algorithm.treesolutions;

public class TestTreeItems {
    TreeItems tt = new TreeItems();

    public void testpreOrder(){
        tt.root = new TreeNode(10);

        tt.root.left = new TreeNode(5);
        tt.root.right = new TreeNode(15);

        tt.root.left.left = new TreeNode(2);
        tt.root.left.right = new TreeNode(7);

        tt.root.right.left = new TreeNode(12);
        tt.root.right.right = new TreeNode(20);

        //System.out.println(tt.preOrder(tt.root));

        System.out.println(tt.preOrder(tt.root));
//        System.out.println(tt.preOrderII(tt.root));
//        System.out.println(tt.preOrderIII(tt.root));
    }

    public void testinOrder(){
//        tt.root = new TreeNode(10);
//
//        tt.root.left = new TreeNode(5);
//        tt.root.right = new TreeNode(15);
//
//        tt.root.left.left = new TreeNode(2);
//        tt.root.left.right = new TreeNode(7);
//
//        tt.root.right.left = new TreeNode(12);
//        tt.root.right.right = new TreeNode(20);

        tt.root = new TreeNode(1);
        tt.root.right = new TreeNode(2);
        tt.root.right.left = new TreeNode(3);

        System.out.println(tt.inOrder(tt.root));
        System.out.println(tt.inOrderII(tt.root));
        System.out.println(tt.inOrderIII(tt.root));
    }

    public void testPostOrder(){
        tt.root = new TreeNode(10);

        tt.root.left = new TreeNode(5);
        tt.root.right = new TreeNode(15);

        tt.root.left.left = new TreeNode(2);
        tt.root.left.right = new TreeNode(7);

        tt.root.right.left = new TreeNode(12);
        tt.root.right.right = new TreeNode(20);

        System.out.println(tt.postOrder(tt.root));
        System.out.println(tt.postOrderII(tt.root));
        System.out.println(tt.postOrderIII(tt.root));
    }

    public void testBalanceTree(){
        tt.root = new TreeNode(10);

        tt.root.left = new TreeNode(5);
//        tt.root.right = new TreeNode(15);

        tt.root.left.left = new TreeNode(2);
        tt.root.left.right = new TreeNode(7);

//        tt.root.right.left = new TreeNode(12);
//        tt.root.right.right = new TreeNode(20);

        System.out.println(tt.isBalanceTree(tt.root));
    }

    public void testInvertTree(){
        tt.root = new TreeNode(10);

        tt.root.left = new TreeNode(5);
        tt.root.right = new TreeNode(15);

        tt.root.left.left = new TreeNode(2);
        tt.root.left.right = new TreeNode(7);

        tt.root.right.left = new TreeNode(12);
        tt.root.right.right = new TreeNode(20);

        System.out.println(tt.invertTree(tt.root));
    }

    public void testLevelOrder(){
        tt.root = new TreeNode(10);

        tt.root.left = new TreeNode(5);
        tt.root.right = new TreeNode(15);

        tt.root.left.left = new TreeNode(2);
        tt.root.left.right = new TreeNode(7);

        tt.root.right.left = new TreeNode(12);
        tt.root.right.right = new TreeNode(20);

        System.out.println(tt.levelOrder(tt.root));
    }

    public static void main(String args[]){
        TestTreeItems tti = new TestTreeItems();

        tti.testLevelOrder();

//        tti.testpreOrder();
//        tti.testinOrder();
//        tti.testPostOrder();
//        tti.testBalanceTree();
//        tti.testInvertTree();
    }
}
