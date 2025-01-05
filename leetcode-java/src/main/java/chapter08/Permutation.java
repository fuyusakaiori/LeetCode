package chapter08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>全排列</p>
 * <p>1. 全排列</p>
 * <p>2. 全排列 II</p>
 */
public class Permutation {

    /**
     * <p>全排列: 不存在重复元素</p>
     */
    public static class PermutationI {

        // 使用哈希表标记是否选择过也是可以的
        private final static int INF = Integer.MIN_VALUE;

        /**
         * <p>回溯: 因为可以向前选择, 所以只能采用循环</p>
         */
        public static List<List<Integer>> permute(int[] nums) {
            List<Integer> permutation = new ArrayList<>();
            List<List<Integer>> permutations = new ArrayList<>();
            permute(nums, permutation, permutations);
            return permutations;
        }

        public static void permute(int[] nums, List<Integer> permutation, List<List<Integer>> permutations) {
            if (permutation.size() == nums.length) {
                permutations.add(new ArrayList<>(permutation));
                return;
            }
            for (int index = 0; index < nums.length; index++) {
                if (nums[index] != INF) {
                    int origin = nums[index];
                    nums[index] = INF;
                    permutation.add(origin);
                    permute(nums, permutation, permutations);
                    permutation.remove(permutation.size() - 1);
                    nums[index] = origin;
                }
            }
        }

    }

    /**
     * <p>全排列 II: 存在重复元素</p>
     */
    public static class PermutationII {

        // 使用哈希表标记是否选择过也是可以的
        private final static int INF = Integer.MIN_VALUE;

        /**
         * <p>回溯: 排序 + 判断前后元素是否相同 => 通用去重手段</p>
         */
        public static List<List<Integer>> permuteUnique(int[] nums) {
            List<Integer> permutation = new ArrayList<>();
            List<List<Integer>> permutations = new ArrayList<>();
            Arrays.sort(nums);
            permuteUnique(nums, permutation, permutations);
            return permutations;
        }

        public static void permuteUnique(int[] nums, List<Integer> permutation, List<List<Integer>> permutations) {
            if (permutation.size() == nums.length) {
                permutations.add(new ArrayList<>(permutation));
                return;
            }
            for (int index = 0; index < nums.length; index++) {
                // NOTE: 每次填入数字时确保重复数字只会填入一次, 只要遇到相同的数字不选择就可以达到去重的效果
                if (index > 0 && nums[index] == nums[index - 1]) {
                    continue;
                }
                int origin = nums[index];
                nums[index] = INF;
                permutation.add(origin);
                permuteUnique(nums, permutation, permutations);
                permutation.remove(permutation.size() - 1);
                nums[index] = origin;
            }
        }

    }

}
