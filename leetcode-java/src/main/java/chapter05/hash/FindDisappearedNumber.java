package chapter05.hash;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>找到所有数组中消失的数字</p>
 */
public class FindDisappearedNumber {

    /**
     * <p>哈希表: O(n) 空间复杂度</p>
     */
    public static List<Integer> findDisappearedNumbersHash(int[] nums) {
        boolean[] hash = new boolean[nums.length];
        List<Integer> disappear = new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            hash[nums[index] - 1] = true;
        }
        for (int index = 0; index < hash.length; index++) {
            if (hash[index]) {
                disappear.add(index + 1);
            }
        }
        return disappear;
    }

    /**
     * <p>原地哈希: O(1) 空间复杂度, 采用正负作为标记</p>
     */
    public static List<Integer> findDisappearedNumbersHashInPlace(int[] nums) {
        List<Integer> disappear = new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            // NOTE: 找到当前元素应该放置的位置
            int current = Math.abs(nums[index]) - 1;
            // NOTE: 判断应该放置的位置是否已经被标记为负数;如果已经标记过, 那么就不需要重复标记
            if (nums[current] > 0) {
                // NOTE: 如果没有标记, 那么就标记为负数, 代表当前的元素已经出现过
                nums[current] = -nums[current];
            }
        }
        for (int index = 0; index < nums.length; index++) {
            // NOTE: 只要该位置的元素仍然是正数, 那么就表示该位置对应的元素是缺失的
            if (nums[index] > 0) {
                disappear.add(index + 1);
            }
        }
        return disappear;
    }

    /**
     * <p>置换: O(1) 空间复杂度, 采用将元素放回到对应的位置上去的方法</p>
     */
    public static List<Integer> findDisappearedNumbersSwap(int[] nums) {
        List<Integer> disappear = new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            while (nums[index] != nums[nums[index] - 1]) {
                swap(nums, index, nums[index] - 1);
            }
        }
        for (int index = nums.length - 1; index >= 0; index--) {
            if (nums[index] != index + 1) {
                disappear.add(index + 1);
            }
        }
        return disappear;
    }

    private static void swap(int[] nums, int first, int second) {
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

}
