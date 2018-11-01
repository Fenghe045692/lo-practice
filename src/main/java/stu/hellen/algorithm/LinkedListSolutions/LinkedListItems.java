package stu.hellen.algorithm.LinkedListSolutions;

public class LinkedListItems {

    /**
     * reverse Linked list, Time = O(n), space = O(n)
     * 1->2->3->4->5->NULL
     * */


    // 1. insert a ListNode into a sorted LinkedList 4, 1-2-3-7  => 1-2-3-4-7



    // 2. delete the duplicate nodes from sorted linkedList  1-2-2-3  =>  1-2-3



    // 3. reverse by the mid node 1-2-5-3-4  => 2-1-5-4-3
    // the linkedlist always only odd count



    // 1. merge tow sorted linkedlist into one long sorted linkedlist: dummy node

    /**
     lastnode-> 1->3 7 9  11
     *           a1
     *         2 4 5 10 13
     *
     * lastNode->null
     *
     * @param a1
     * @param b1
     * @return
     */


    // 2. N1->N2->N3->N4->N5->N6->.........-> Nn->null convert
    // (N1->Nn)->(N2->Nn-1)->(N3->Nn-2)->.....



    // 3. partition list  1->6->3->2a->5->2b target x = 4  1->3->2a->2b->6->5


}
