package stu.hellen.algorithm.BinaryTreeaAndBinarySearchTreeSolutions;

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

        System.out.println(tt.preOrderII(tt.root));
    }

    public void testinOrder(){
        tt.root = new TreeNode(10);

        tt.root.left = new TreeNode(5);
        tt.root.right = new TreeNode(15);

        tt.root.left.left = new TreeNode(2);
        tt.root.left.right = new TreeNode(7);

        tt.root.right.left = new TreeNode(12);
        tt.root.right.right = new TreeNode(20);

        System.out.println(tt.inOrder(tt.root));
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
    }

    public static void main(String args[]){
        TestTreeItems tti = new TestTreeItems();

        //tti.testpreOrder();

        //tti.testinOrder();

        tti.testPostOrder();
    }
}
