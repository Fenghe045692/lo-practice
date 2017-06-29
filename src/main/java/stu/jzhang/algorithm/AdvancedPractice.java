package stu.jzhang.algorithm;

import java.util.*;

/**
 * Created by hellen on 6/21/17.
 */
public class AdvancedPractice {
    public static void main(String[] args){
        AdvancedPractice test = new AdvancedPractice();
//        test.printSkipListNode(test.copyNode(test.generateSkipListNode()));
        System.out.println(test.pow(1.00000,
                -2147483648));
    }

    SkipListNode generateSkipListNode(){
        SkipListNode s0 = new SkipListNode(0);
        SkipListNode s1 = new SkipListNode(1);
        SkipListNode s2 = new SkipListNode(2);
        SkipListNode s3 = new SkipListNode(3);
        s0.next = s1;
        s1.next = s2;
        s2.next = s3;
        s0.forward = s0;
        s1.forward = s3;
        s2.forward = s1;
        s3.forward = s0;

        return s0;
    }

    public void printSkipListNode(SkipListNode head){
        while (head != null){
            System.out.println(head.value + " ---> " + (head.forward == null ? "NULL" : head.forward.value));
            head = head.next;
        }
    }

    public SkipListNode copyNode(SkipListNode head){
        if(head == null){
            return null;
        }

        HashMap<SkipListNode, SkipListNode> map = new HashMap<>();
        SkipListNode newHead = new SkipListNode(head.value);
        SkipListNode p2 = newHead;
        map.put(head, newHead);
        // copy next node
        while (head != null){
            // copy next
            if(head.next != null){
                if(map.containsKey(head.next)){
                    p2.next = map.get(head.next);
                }else{
                    SkipListNode newNext = new SkipListNode(head.next.value);
                    map.put(head.next, newNext);
                    p2.next = newNext;
                }
            }

            // copy forward
            if(head.forward != null){
                SkipListNode forward = head.forward;
                if(map.containsKey(forward)){
                    p2.forward = map.get(forward);
                }else{
                    SkipListNode newForward = new SkipListNode(forward.value);
                    map.put(forward, newForward);
                    p2.forward = newForward;
                }
            }
            head = head.next;
            p2 = p2.next;
        }

        return newHead;
    }

    public UndirectedGraphNode cloneGraphBFS(UndirectedGraphNode node) {
        if(node == null){
            return null;
        }

        // record the link from the original node to the replica
        HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        map.put(node, new UndirectedGraphNode(node.label));
        queue.offer(node);
        while (!queue.isEmpty()){
            int size = queue.size();
            while (size > 0){
                UndirectedGraphNode tmp = queue.poll();
                UndirectedGraphNode replica = map.get(tmp);
                // copy neighbors
                for(UndirectedGraphNode neighbor : tmp.neighbors){
                    if(map.containsKey(neighbor)){
                        replica.neighbors.add(map.get(neighbor));
                    }else{
                        UndirectedGraphNode newNeighbor = new UndirectedGraphNode(neighbor.label);
                        map.put(neighbor, newNeighbor);
                        replica.neighbors.add(newNeighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return map.get(node);
    }

    public UndirectedGraphNode cloneGrapshDFS(UndirectedGraphNode node){
        return helper(node, new HashMap<>());
    }

    public UndirectedGraphNode helper(UndirectedGraphNode node, Map<UndirectedGraphNode, UndirectedGraphNode> map){
        if(node == null){
            return null;
        }
        // copy the first node
        UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
        map.put(node, newNode);
        // copy its neighbors
        for(UndirectedGraphNode neighbor : node.neighbors){
            if(map.containsKey(neighbor)){
                newNode.neighbors.add(map.get(neighbor));
            }else{
                newNode.neighbors.add(helper(neighbor, map));
            }
        }

        return newNode;
    }

    /**
     * exp could be represented as a binary representation
     * like 1 0 0 0 1 for 4^17 = 1*4^16 * 0*4^8 .....1*4^0
     * we just simulate each position's factors to get the result.
     * @param base
     * @param exp
     * @return
     */
    public double pow(double base, int exp){
        if(exp == 0){
            return 1;
        }

        if(exp < 0){
            if(exp == Integer.MIN_VALUE){
                return pow(base, exp+1) / pow(base, 1);
            }
            return 1.0 / pow(base, (-1)*exp);
        }

        if(base == 0){
            if(exp < 0){
                throw new IllegalArgumentException();
            }

            return 0;
        }

        int result = 1;
        while (exp > 0){
            if((exp & 1) == 1){
                result *= base;
            }
            exp >>= 1;
            base *= base; // change x to x^2
        }

        return result;
    }



    public static class SkipListNode{
        int value;
        SkipListNode next;
        SkipListNode forward;

        public SkipListNode(int value) {
            this.value = value;
        }
    }

    static class UndirectedGraphNode {
        int label;
        List<UndirectedGraphNode> neighbors;
        UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
    }
}
