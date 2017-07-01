package stu.jzhang.algorithm;

import stu.jzhang.algorithm.util.Utilies;

import java.util.*;

/**
 * Created by hellen on 6/21/17.
 */
public class AdvancedPractice {
    public static void main(String[] args){
        AdvancedPractice test = new AdvancedPractice();
//        test.printSkipListNode(test.copyNode(test.generateSkipListNode()));
//        System.out.println(test.pow(1.00000,
//                -2147483648));
//        for(List<Integer> list : test.fourSum(new int[]{-3,-2,-1,0,0,1,2,3}, 0)){
//            Utilies.printArrayList(list);
//        }
        //System.out.println(test.allParenthesis(3, 0, 0));
        Utilies.printArrayList(test.common(Arrays.asList(5,1,6,5,2,4,6,6,5), Arrays.asList(5,5,1,3,6)));
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

    /**
     * We cannot implement this with O(n^2) time complexity. Since each time you have to iterate the list of pair items
     * to generate the combination.
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> res = new ArrayList<>();
        if(nums == null || nums.length < 3){
            return res;
        }
        Arrays.sort(nums);
        HashMap<Long, List<Pair>> map = new HashMap<>();
        HashSet<Combination> com = new HashSet<>();
        for(int j=1; j < nums.length; j++){
            for(int i = 0; i < j; i++){
                long pairSum = nums[i] + nums[j];
                long subPairSum = target - pairSum;
                if(map.containsKey(subPairSum)){
                    for(Pair item : map.get(subPairSum)){
                        if(item.right < i){
                            com.add(new Combination(nums[item.left], nums[item.right], nums[i], nums[j]));
                        }
                    }
                }
                if(!map.containsKey(pairSum)){
                    map.put(pairSum, new ArrayList<>(Arrays.asList(new Pair(i, j))));
                }else{
                    map.get(pairSum).add(new Pair(i,j));
                }
            }
        }
        for(Combination item : com){
            res.add(item.asList());
        }
        return res;
    }

    public static class Pair{
        int left;
        int right;

        public Pair(int left, int right){
            this.left = left;
            this.right = right;
        }

    }

    public class Combination{
        int[] items;

        public Combination(int t1, int t2, int t3, int t4){
            items = new int[]{t1, t2, t3, t4};
        }


        /**
         * A very general way to implement the hash code for a few interconnected numbers
         * @return
         */
        @Override
        public int hashCode(){
            int res = 17;
            res = res * 31 + items[0];
            res = res * 31 + items[1];
            res = res * 31 + items[2];
            res = res * 31 + items[3];
            return res;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Combination) {
                Combination com = (Combination) obj;
                return (items[0] == com.items[0] && items[1] == com.items[1] && items[2] == com.items[2] && items[3] == com.items[3]);
            }
            return false;
        }

        public List<Integer> asList(){
            return Arrays.asList(items[0], items[1], items[2], items[3]);
        }
    }

    public List<String> allParenthesis(int i, int j, int k){
        int[][] parenthesis = new int[][]{{i, 0, 0}, {j, 0, 0}, {k, 0, 0}};
        char[][] chars = new char[][]{{'{', '}'}, {'[', ']'}, {'(', ')'}};
        List<String> res = new ArrayList<>();
        dfs(parenthesis, chars, 0, res, new StringBuilder(), new Stack<>());

        return res;
    }

    private void dfs(int[][] parenthesis, char[][] chars, int level, List<String> res, StringBuilder path, Stack<Character> stack){
        if(level >= 2 * (parenthesis[0][0] + parenthesis[1][0] + parenthesis[2][0])){
            res.add(path.toString());
            return;
        }

        // try all the possibility on the current level
        for(int i =0; i < parenthesis.length; i++){
            // add left side
            if(parenthesis[i][1] < parenthesis[i][0]){
                parenthesis[i][1]++;
                path.append(chars[i][0]);
                stack.add(chars[i][0]);
                dfs(parenthesis, chars, level + 1, res, path, stack);
                parenthesis[i][1]--;
                path.deleteCharAt(path.length() -1);
                stack.pop();
            }

            // add right side
            if(!stack.isEmpty() && stack.peek() == chars[i][0]){
                parenthesis[i][2]++;
                path.append(chars[i][1]);
                stack.pop();
                dfs(parenthesis, chars, level + 1, res, path, stack);
                parenthesis[i][2]--;
                path.deleteCharAt(path.length() -1);
                stack.add(chars[i][0]);
            }
        }
    }

    public List<Integer> common(List<Integer> a, List<Integer> b) {
        List<Integer> res = new ArrayList<>();
        if(a == null || b == null || a.size() == 0 || b.size() == 0){
            return res;
        }

        Collections.sort(a);
        Collections.sort(b);
        for(int i = 0, j = 0; i < a.size() && j < b.size();){
            if(a.get(i) == b.get(j)){
                res.add(a.get(i));
                i++;
                j++;
            }else if(a.get(i) > b.get(j)){
                j++;
            }else{
                i++;
            }
        }

        return res;
    }

    public List<Integer> common(int[] a, int[] b, int[] c) {
        List<Integer> res = new ArrayList<>();
        if(a == null || b == null || c == null || a.length == 0 || b.length == 0 || c.length == 0){
            return res;
        }

        for(int i = 0, j = 0, k = 0; i < a.length && j < b.length && k < c.length;){
            if(a[i] == b[j] && b[j] == c[k]){
                res.add(a[i]);
                i++;
                j++;
                k++;
                continue;
            }
//              we donot need to move the elements inside one single loop since the overall number of movement will be the same if we only move
            // one step each loop, then the code will be cleaner.
//            if(a[i] < b[j]){
//                i++;
//                if(b[j] < c[k]){
//                    j++;
//                }else if(b[j] > c[k]){
//                    k++;
//                }
//            }else if(a[i] == b[j]){
//                if(a[i] > c[k]){
//                    k++;
//                }else{
//                    i++;
//                    j++;
//                }
//            }else{
//                j++;
//                if(a[i] < c[k]){
//                    i++;
//                }else if(a[i] > c[k]){
//                    k++;
//                }
//            }
            if(a[i] <= b[j] && a[i] <= c[k]){
                i++;
            }else if(b[j] <= a[i] && b[j] <= c[k]){
                j++;
            }else{
                k++;
            }
        }

        return res;
    }

    /**
     * Assumption: all the letters in the world dropped in 'a' - 'z'. There is no null in the dict, and the length of  the dict
     * will be greater than 2;
     * @param dict
     * @return
     */
    public int largestProduct(String[] dict){
        int largest = 0;
        HashMap<String, Integer> bitMaskMap = getBitMask(dict);
        Arrays.sort(dict, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        for(int i = 1; i < dict.length; i++){
            for(int j = 0; j < i; j++){
                int product = dict[i].length() * dict[j].length();
                // early break if the product is already smaller than
                // the largest since the product on the inner loop is decreasing order.
                if(product <= largest){
                    break;
                }

                int iBitMask = bitMaskMap.get(dict[i]);
                int jBitMask = bitMaskMap.get(dict[j]);
                // if these two strings have the same letters, its respective BitMask will have the same 1 in the bits.
                // After we do the '&', it will never have a change to be zero.
                if((iBitMask & jBitMask) == 0){
                    largest = product;
                }

            }
        }

        return largest;
    }

    private HashMap<String, Integer> getBitMask(String[] dict){
        HashMap<String, Integer> bitMaskMap = new HashMap<>();
        for(String str : dict){
            if(!bitMaskMap.containsKey(str)){
                // use lowest 26 bits of 32 bits of integer to represents the presence of the character X. Since the letters
                // within 'a' - 'z' is consecutive, then every letter will have one corresponding position within
                // the 26 bits. Since we just care if the letters exist, no need to concern duplicate letters.
                int bitMask = 0;
                for(int i = 0; i < str.length(); i++){
                    bitMask |= 1 << (str.charAt(i) - 'a');
                }

                bitMaskMap.put(str, bitMask);
            }
        }

        return bitMaskMap;
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
