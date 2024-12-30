package chapter05.hash;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>数组中重复的数据</p>
 */
public class FindDuplicateNumber {

    /**
     * <p>哈希表: O(n) 空间复杂度</p>
     */
    public static List<Integer> findDuplicatesHash(int[] nums) {
        boolean[] hash = new boolean[nums.length];
        List<Integer> disappear = new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            if (hash[nums[index] - 1]) {
                disappear.add(nums[index]);
            } else {
                hash[nums[index] - 1] = true;
            }
        }
        return disappear;
    }

    /**
     * <p>原地哈希: O(1) 空间复杂度, 采用正负作为标记</p>
     */
    public static List<Integer> findDuplicatesHashInPlace(int[] nums) {
        List<Integer> disappear = new ArrayList<>();
        for (int index = nums.length - 1; index >= 0; index--) {
            int current = Math.abs(nums[index]) - 1;
            if (nums[current] > 0) {
                nums[current] = -nums[current];
            } else {
                disappear.add(current + 1);
            }
        }
        return disappear;
    }

    /**
     * <p>置换: O(1) 空间复杂度, 采用将元素放回到对应的位置上去的方法</p>
     */
    public static List<Integer> findDuplicatesSwap(int[] nums) {
        List<Integer> disappear = new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            // NOTE: 不断交换直到将元素放到对应的位置
            while (nums[index] != nums[nums[index] - 1]) {
                swap(nums, index, nums[index] - 1);
            }
        }
        // NOTE: 只要不在自己位置上的元素, 就必然是重复的元素
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != index + 1) {
                disappear.add(nums[index]);
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
