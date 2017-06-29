package stu.jzhang.algorithm;

/**
 * Hints:
 * 1. 一维的original data(such as a rope, one word), 求MAX or MIN
 *      1.1. if each of the smallest elements is identical/similar.
 *           1.1.1 a meter of rope
 *           2.2.2 a letter
 *          linear scan and look back
 *      1.2. if each of the smallest elements is not same:
 *           1.2.1 cut wood
 *           1.2.3 沙子归并
 *           从中心开花，[index = 0,1, ....N-1], for each M[i,j], we usually try out all the possible
 *           k(i<k<j, M[i,j] = Min(M[i,k] + /-/ + M[k,j])
 * Created by hellen on 6/22/17.
 */
public class DynamicProgramming {
    public static void main(String[] args){
        DynamicProgramming test = new DynamicProgramming();
        System.out.println(test.cutWood(new int[]{1,2, 3, 4,5,6, 7,8,9}, 10));
    }

    /**
     * 0   1   2     3      4 -> M
     * 0   2   4     7     10 -> cutsArr
     * 0 1 2 3 4 5 6 7 8 9 10 -> wood
     * @param cuts
     * @param length
     * @return
     */
    public int cutWood(int[] cuts, int length){
        int[][] M = new int[cuts.length + 2][cuts.length + 2];
        int[] cutsArr = new int[cuts.length + 2];

        // construct the extra array for calculation
        for(int i = 0; i < cuts.length; i++){
            cutsArr[i+1] = cuts[i];
        }
        cutsArr[cutsArr.length - 1] = length;

        // initialize size = 1
        for(int i = 0; i < cutsArr.length - 1; i++){
            M[i][i+1] = 0;
        }

        for(int i = 2; i < cutsArr.length; i++){
            for(int j =0; j < cutsArr.length && j+i < cutsArr.length; j++){
                int min = Integer.MAX_VALUE;
                for(int k = j+i-1; k > j; k--){
                    min = Math.min(min, M[j][k] + M[k][i+j]);
                }

                M[j][j+i] = cutsArr[i+j] - cutsArr[j] + min;
            }
        }

        return M[0][M.length - 1];
    }
}
