package stu.jzhang.algorithm.smallclass.thur;

import stu.jzhang.algorithm.util.Utilies;

import java.util.*;

/**
 * Created by hellen on 7/13/17.
 */
public class Thur170713 {
    public static void main(String[] args){
        Thur170713 test = new Thur170713();
//        System.out.println(Arrays.toString(test.getMaxWindow(new int[]{3,4,1,3}, 2)));
//        System.out.println(Utilies.hightsOneBits(7));
//        System.out.println(Arrays.toString(test.getMaxWindowWithDP(new int[]{3,4,1,3}, 3)));

        // edit distance
        List<Character> str = new ArrayList<>();
        str.add('a');
        str.add('b');
        str.add('b');
        str.add('b');

        List<Character> str2 = new ArrayList<>();
        str2.add('b');
        str2.add('c');
        System.out.println(new StreamOneEditDistanceSolver().isOneEditDistance(str.iterator(), str2.iterator()));
    }

    /**
     * PriorityQueue method to do it
     * @param nums
     * @param k
     * @return
     */
    public int[] getMaxWindow(int[] nums, int k){
        if(nums == null || nums.length == 0 || k <= 0){
            return new int[]{};
        }

        PriorityQueue<Cell> queue = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                return o2.num - o1.num;
            }
        });

        int left = 0, right = 0;
        int[] res = new int[nums.length - k+1];
        int i = 0;
        // each time of loop will have a valid window size from left to right
        while (right < nums.length){
            Cell item = new Cell(nums[right], right);
            queue.add(item);
            if(right >= k-1){
                while (queue.peek().index < left) queue.poll();
                res[i++] = queue.peek().num;
                left++;
            }
            right++;
        }
        return res;
    }

    public int[] getMaxWindowWithDP(int[] nums, int k){
        if(nums == null || nums.length == 0 || k <= 0){
            return new int[]{};
        }

        int hights = Utilies.hightsOneBits(k);
        // dp[i][x] represents the ith max value with the window of its size k
        // dp[i][x] = max(dp[i][x-1], dp[i+2^x - 2^(x-1)][x-1]) k = 2 ^ x (x is the largest int which 2^x < k)
        // last one len might be k - 2^x
        // based on if the k is 2's power, dp column size will be different
        int[][] dp;
        dp = new int[nums.length][hights + 1];
        for(int i = 0; i < nums.length; i++){
            dp[i][0] = nums[i];
        }

        for(int i = 1; i <= hights; i++){
            int len = (1 << i) - (1 << (i-1));
            for(int j = 0; j < nums.length- (1 << i) + 1; j++){
                int max = dp[j][i-1];
                for(int m = j; m <= j+len; m++){
                    max = Math.max(max, dp[m][i-1]);
                }
                dp[j][i] = max;
            }
        }

        int[] res = new int[nums.length - k + 1];
        int n = 0;
        // get the final result
        if((1 << hights) != k){
            int len = k - (1 << hights);
            for(int i = 0; i < nums.length - k + 1; i++){
                int max = dp[i][hights];
                for(int m = i; m <= i+len; m++){
                    max = Math.max(max, dp[m][hights]);
                }
                res[n++] = max;
            }
        }else{
            for(int i = 0; i < nums.length - k + 1; i++) {
                res[n++] = dp[i][hights];
            }
        }
        return res;
    }

    /**
     * We are only allowed to edit the string once, so we just need to care about its previous state and the current change.
     * For each pair of unequal elements, we have three ways to edit the distance. It is a bottom-up way to solve the
     * problem. It will be easier to understand compared with non-stream way.
     */
    public static class StreamOneEditDistanceSolver{
        enum Operation{
            MODIFICATION, INSERTION, DELETION;
        }

        class State{
            Operation op;
            // demonstrate if the state is still valid to be selected
            boolean isValid;
            // demonstrate if the state was selected to change character
            boolean actionTaken;

            public State(Operation op) {
                this.op = op;
                this.isValid = true;
                this.actionTaken = false;
            }
        }

        public boolean isOneEditDistance(Iterator<Character> one, Iterator<Character> two){
            List<State> states = new ArrayList<>();
            states.add(new State(Operation.DELETION));
            states.add(new State(Operation.MODIFICATION));
            states.add(new State(Operation.INSERTION));

            Character prevOne = null;
            Character prevTwo = null;
            while (one.hasNext() || two.hasNext()){
                Character currOne = one.hasNext() ? one.next() : null;
                Character currTwo = two.hasNext() ? two.next() : null;

                // try to apply every one of these three operation
                for(State state : states){
                    processAndChangeState(currOne, currTwo, prevOne, prevTwo, state);
                }

                if(!removeAndCheckState(states)){
                    return false;
                }

                prevOne = currOne;
                prevTwo = currTwo;
            }

            return checkFinalState(states, prevOne, prevTwo);
        }

        /**
         * Example:
         *        Modification : One -> a c b d
         *                       Two -> a t b m
         *
         *       Insertion: One -> a c  t    b  m  k n
         *                  Two -> a c (t)b  m  k  n
         *
         *       Deletion: One -> a c b  m  k  n
         *                 Two -> a c t  b  m  k  n
         *
         * If any of one or two is Null, it means that the stream is stopped. If it is already stopped, we just apply
         * the same logic to the string input. If the current two characters are same, we just skip them.
         * @param one
         * @param two
         * @param prevOne
         * @param prevTwo
         * @param state
         */
        private void processAndChangeState(Character one, Character two, Character prevOne, Character prevTwo, State state){
            switch (state.op) {
                case MODIFICATION:
                    // if one of the stream was terminated or more than one non-equal items found,
                    // then modification is not a possible way.
                    if(one == null || two == null || state.actionTaken && one != two){
                        state.isValid = false;
                    }

                    // selected the current character to change
                    if(one != null && two != null && one != two){
                        state.actionTaken = true;
                    }
                    break;
                case INSERTION:
                    if(one == null || (state.actionTaken && one != prevTwo)){
                        state.isValid = false;
                    }

                    // selected the current character to change
                    if(two == null || (one != null && one != two)){
                        state.actionTaken = true;
                    }
                    break;
                case DELETION:
                    if(two == null || (state.actionTaken && two != prevOne)){
                        state.isValid = false;
                    }

                    // selected the current character to change
                    if(one != null && two != null && one != two){
                        state.actionTaken = true;
                    }
                    break;
                default:
            }
        }

        // based on the way of changing the string, we need to check the final state.
        // Imagine these two string just has a different size, that is where we call this method to do that.
        public boolean checkFinalState(List<State> states, Character lastOne, Character lastTwo){
            for(State state : states){
                switch (state.op){
                    case MODIFICATION:
                        if(lastOne != null && lastTwo != null){
                            return Boolean.TRUE;
                        }
                        break;
                    case INSERTION:
                        if(lastTwo == null){
                            return Boolean.TRUE;
                        }
                        break;
                    case DELETION:
                        if(lastOne == null){
                            return Boolean.TRUE;
                        }
                        break;
                    default:
                }
            }

            return Boolean.FALSE;
        }

        // if all the states are invalid, false will be returned
        private boolean removeAndCheckState(List<State> states){
            Iterator<State> iterator = states.iterator();
            while (iterator.hasNext()){
                if(!iterator.next().isValid){
                    iterator.remove();
                }
            }

            return !states.isEmpty();
        }
    }

    public class Cell{
        int num;
        int index;

        public Cell(int num, int index) {
            this.num = num;
            this.index = index;
        }
    }
}
