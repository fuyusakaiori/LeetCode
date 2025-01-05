package chapter08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>子集问题</p>
 * <p>1. 子集</p>
 * <p>2. 子集 II</p>
 */
public class SubSet {

    /**
     * <p>子集: 没有重复的元素, 不允许包含重复的子集</p>
     */
    public static class SubSetI {

        /**
         * <p>回溯: 无法使用循环, 需要不选的情况作为结果的</p>
         */
        public static List<List<Integer>> subsets(int[] nums) {
            List<Integer> subset = new ArrayList<>();
            List<List<Integer>> subsets = new ArrayList<>();
            subsets(0, nums, subset, subsets);
            return subsets;
        }

        public static void subsets(int index, int[] nums, List<Integer> subset, List<List<Integer>> subsets) {
            if (index == nums.length) {
                subsets.add(new ArrayList<>(subset));
                return;
            }
            subsets(index + 1, nums, subset, subsets);
            subset.add(nums[index]);
            subsets(index + 1, nums, subset, subsets);
            subset.remove(subset.size() - 1);
        }

    }

    /**
     * <p>子集 II: 有重复的元素, 不允许包含重复的子集</p>
     */
    public static class SubSetII {

        /**
         * <p>回溯: 无法使用循环, 需要不选的情况作为结果的</p>
         */
        public static List<List<Integer>> subsetsWithDup(int[] nums) {
            List<Integer> subset = new ArrayList<>();
            List<List<Integer>> subsets = new ArrayList<>();
            Arrays.sort(nums);
            subsetsWithDup(0, false, nums, subset, subsets);
            return subsets;
        }

        public static void subsetsWithDup(int index, boolean flag, int[] nums, List<Integer> subset, List<List<Integer>> subsets) {
            if (index == nums.length) {
                subsets.add(new ArrayList<>(subset));
                return;
            }
            subsetsWithDup(index + 1, false, nums, subset, subsets);
            if (!flag && index > 0 && nums[index] == nums[index - 1]) {
                return;
            }
            subset.add(nums[index]);
            subsetsWithDup(index + 1, true, nums, subset, subsets);
            subset.remove(subset.size() - 1);
        }

    }

}
