package stu.jzhang.algorithm;

import stu.jzhang.algorithm.util.Utilies;

import java.util.*;

/**
 * Hints: Start with the smallest input and try to find the induction
 * Created by hellen on 6/17/17.
 */
public class Randomization {

    private static Random generator = new Random();
    public static void main(String[] args){
        Randomization test = new Randomization();
//        Utilies.printArray(test.randomGetKElements(new int[]{1, 2, 3, 4, 5, 6,7,8,9}, 7));
//        test.getSamplingForUnlimitedData();
//        test.getSamplingForUnlimitedData(10);
//        System.out.println(test.getRandom7FromRandom5());
        System.out.println(test.getRandom1MFromRandom2());
    }

    /**
     * Randomly get the k elements from the array
     * @param arr
     * @param k
     * @return
     */
    public List<Integer> randomGetKElements(int[] arr, int k){
        List<Integer> result = new ArrayList<>();
        if(k <= 0 || k > arr.length){
            return result;
        }

        // cache all the indexes to avoid to change the original array
        int[] indexes = new int[arr.length];
        for(int i =0; i < indexes.length; i++){
            indexes[i] = i;
        }

        int pivot = indexes.length;
        Random generator = new Random();
        while (k-- > 0){
            int select = generator.nextInt(pivot--);
            // Swap the rightmost and the selected
            Utilies.swap(indexes, pivot, select);
            result.add(arr[indexes[pivot]]);
        }

        return result;
    }

    /**
     * keep one counter and sample
     */
    public void getSamplingForUnlimitedData(){
        int counter = 1;
        int resutl_so_far = -1;
        Scanner scanner = new Scanner(System.in);
        Random generator = new Random();
        int newItem = 0;
        while(newItem != -1){
            System.out.println("Enter your number:");
            newItem = scanner.nextInt();
            // Key point: keep the current new item's possibility as 1/n
            int r = generator.nextInt(counter++);
            if(r == 0){
                resutl_so_far = newItem;
            }
            System.out.println("ResultSoFar: " + resutl_so_far);
        }
    }

    public void getSamplingForUnlimitedData(int k){
        if(k < 1){
            return;
        }
        int counter = 1;
        int[] selected = new int[k];
        Scanner scanner = new Scanner(System.in);
        Random generator = new Random();
        int newItem = 0;
        while(newItem != -1){
            System.out.println("Enter your number:");
            newItem = scanner.nextInt();
            // Key point: keep the current new item's possibility as 1/n
            int r = generator.nextInt(counter++);
            if(r < k){
                selected[r] = newItem;
            }
            System.out.println("ResultSoFar: " + Arrays.toString(selected));
        }
    }

    /**
     * Hints: focus on what you want.
     * @return
     */
    public int getRandom7FromRandom5(){
        int num = random25();
        while (num > 20){
            num = random25();
        }

        return num % 7;
    }

    private int getRandom1MFromRandom2(){
        int num = 1 << 20;
        while (num > 999999){
            int counter = 0;
            int newNum = 0;
            // Regenerate a new number
            while (counter < 20){
                if(generator.nextInt(2) == 1){
                    newNum = newNum | (1 << counter);
                }
                counter++;
            }
            num = newNum;
        }

        return num;
    }
    private int random25(){
        return 5 *generator.nextInt(5) + generator.nextInt(5);
    }
}
