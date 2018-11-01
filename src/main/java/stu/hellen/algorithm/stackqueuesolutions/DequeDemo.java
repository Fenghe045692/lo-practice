package stu.hellen.algorithm.stackqueuesolutions;

import java.util.Deque;
import java.util.LinkedList;

public class DequeDemo {

    public static void main(String args[]){
        Deque<Integer> deque = new LinkedList<>();

        deque.push(1);
        deque.push(2);
        System.out.println(deque);

        deque.add(3);
        deque.add(4);
        System.out.println(deque);

        deque.addFirst(5);
        deque.addLast(6);
        System.out.println(deque);

        System.out.println(deque.getFirst());
        System.out.println(deque.getLast());

        System.out.println(deque.poll());
        System.out.println(deque);

        System.out.println(deque.pollFirst());
        System.out.println(deque.pollLast());
        System.out.println(deque);

        System.out.println(deque.peek());
        System.out.println(deque.peekFirst());
        System.out.println(deque.peekLast());

        System.out.println(deque.remove());
        System.out.println(deque.remove());
        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeLast());
        System.out.println(deque);

        System.out.println(deque.pollFirst());
        System.out.println(deque.pollLast());
        System.out.println(deque);
    }

}
