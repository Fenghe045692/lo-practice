package stu.hellen.algorithm.LinkedListSolutions;

public class ListNode {
    int val;
    ListNode next;

    public ListNode(int val){
        this.val = val;
        next = null;
    }

    public String toString(){
        String str = " ";

        ListNode current = this;
        while(current != null){
            str += current.val + " ";
            current = current.next;
        }

        return str;
    }
}
