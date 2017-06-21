package stu.jzhang.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hellen on 6/20/17.
 */
public class Recursions {
    public static void main(String[] args){
        Recursions test = new Recursions();
        test.printBlocks(3);
    }

    /**
     * {{}}{{}}
     * @param n
     */
    public void printBlocks(int n){
        List<String> result = new ArrayList<>();
        StringBuffer path = new StringBuffer();
        helper(n, 0, result, path, 0, 0);
    }

    private void helper(int n, int level, List<String> result, StringBuffer path, int left, int right){
        // Base case
        if(level >= 2*n){
            result.add(path.toString());
            System.out.println(path.toString());
            return;
        }

        if(left < right){
            return;
        }

        // recursive rule
        // Only one space to add possible elements
        if(left < n) {
            path.append("{");
            helper(n, level + 1, result, path, left + 1, right);
            path.deleteCharAt(path.length() - 1);
        }

        if(right < n){
            path.append("}");
            helper(n, level+1, result, path, left, right+1);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
