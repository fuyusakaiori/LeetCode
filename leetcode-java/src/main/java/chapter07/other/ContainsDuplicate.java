package chapter07.other;

import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>存在重复元素</p>
 * <p>存在重复元素 II</p>
 * <p>存在重复元素 III</p>
 */
public class ContainsDuplicate {

    /**
     * <p>存在重复元素: 本题和滑动窗口没有任何关系, 仅题目名字相同整理在一起</p>
     */
    public static class ContainsDuplicateI {

        public static boolean containsDuplicate(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int index = 0; index < nums.length; index++) {
                if (!set.add(nums[index])) {
                    return true;
                }
            }
            return false;
        }

    }

    /**
     * <p>存在重复元素 II</p>
     */
    public static class ContainsDuplicateII {

        /**
         * <p>滑动窗口: 固定窗口大小; 不建议在外初始化</p>
         * <p>1. 滑动窗口固定大小时, 左指针的移动条件: </p>
         * <p>2. 如果在外初始化滑动窗口, 那么开始移动滑动窗口时就不需要提供判断条件</p>
         * <p>3. 如果不在外初始化滑动窗口, 那么开始移动滑动窗口时就需要提供判断条件, 相当于合并</p>
         * <p>4. 存在重复元素系列建议不要在外初始化, 因为窗口的大小可能比数组长度要大, 所以会导致初始化越界</p>
         */
        public static boolean containsNearbyDuplicate(int[] nums, int count) {
            // 1. 滑动窗口
            Set<Integer> windows = new HashSet<>();
            // 2. 滑动窗口左右指针
            for (int left = 0, right = 0; right < nums.length; right++) {
                // 3. 滑动窗口左指针移动条件
                if (right - left > count) {
                    windows.remove(nums[left++]);
                }
                // 4. 判断是否在滑动窗口内存在相同的元素
                if (windows.contains(nums[right])) {
                    return true;
                }
                windows.add(nums[right]);
            }
            return false;
        }

    }

    /**
     * <p>存在重复元素 III</p>
     */
    public static class ContainsDuplicateIII {

        /**
         * <p>滑动窗口: 固定窗口大小; 不建议在外初始化</p>
         * <p>1. 存在重复元素 II 是在滑动窗口中找重复的元素, 可以直接使用哈希表</p>
         * <p>2. 存在重复元素 III 是在滑动窗口中找差值最接近目标值的元素组, 可以直接使用有序哈希表</p>
         */
        public static boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
            // 1. 滑动窗口
            NavigableSet<Integer> windows = new TreeSet<>();
            // 2. 滑动窗口左右指针
            for (int left = 0, right = 0; right < nums.length; right++) {
                if (right - left > indexDiff) {
                    windows.remove(nums[left++]);
                }
                // 3. 获取最接近当前元素的两个值
                Integer floor = windows.floor(nums[right]);
                Integer ceiling = windows.ceiling(nums[right]);
                // 4. 判断两数之差是否在范围内
                if (floor != null && nums[right] - floor <= valueDiff) {
                    return true;
                }
                if (ceiling != null && ceiling - nums[right] <= valueDiff) {
                    return true;
                }
                windows.add(nums[right]);
            }
            return false;
        }


    }

}
