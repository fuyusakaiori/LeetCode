package chapter05.hash;

/**
 * <p>寻找缺失的第一个正数</p>
 */
public class FindFirstMissingPositiveNumber {

    /**
     * <p>原地哈希: 空间复杂度 O(1)</p>
     */
    public static int firstMissingPositiveHashInPlace(int[] nums) {
        // NOTE: 去除所有非负整数
        for (int index = 0; index < nums.length; index++) {
            nums[index] = nums[index] <= 0 ? nums.length + 1: nums[index];
        }
        // NOTE: 依然采用正负标记
        for (int index = 0; index < nums.length; index++) {
            int current = Math.abs(nums[index]) - 1;
            if (current < nums.length && nums[current] > 0) {
                nums[current] = -nums[current];
            }
        }
        // NOTE: 只要找到第一个位置上的数不为负数, 那么就证明这个位置的正数是缺失的
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] > 0) {
                return index + 1;
            }
        }
        return nums.length + 1;
    }


    /**
     * <p>置换: 空间复杂度为 O(1)</p>
     */
    public static int firstMissingPositiveSwap(int[] nums) {
        // NOTE: 去除所有非负整数
        for (int index = 0; index < nums.length; index++) {
            nums[index] = nums[index] <= 0 ? nums.length + 1: nums[index];
        }
        // NOTE: 开始将元素放回到对应的位置
        for (int index = 0; index < nums.length; index++) {
            // NOTE: 不停交换直到元素回到自己正确的位置上
            while (nums[index] - 1 < nums.length && nums[index] != nums[nums[index] - 1]) {
                swap(nums, index, nums[index] - 1);
            }
        }
        // NOTE: 只要找到第一个索引和元素不对应的, 就直接返回
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != index + 1) {
                return index + 1;
            }
        }
        return nums.length + 1;
    }

    private static void swap(int[] nums, int first, int second) {
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

}
