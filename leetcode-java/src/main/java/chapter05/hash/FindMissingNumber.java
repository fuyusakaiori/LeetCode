package chapter05.hash;

/**
 * <p>丢失的数字</p>
 * <p>1. 排序</p>
 * <p>2. 哈希表</p>
 * <p>3. 原地哈希</p>
 * <p>4. 置换</p>
 * <p>5. 位运算</p>
 * <p>6. 数学</p>
 */
public class FindMissingNumber {

    /**
     * <p>原地哈希: 空间复杂度 O(1), 采用数组长度标记</p>
     */
    public static int missNumberHashInPlace(int[] nums) {
        // NOTE: length + 1; 因为直接采用数组长度会无法区分已经标记的元素和本身大小就是数组长度的元素
        int mark = nums.length + 1;
        for (int index = 0; index < nums.length; index++) {
            // NOTE: 取模的目的是还原之前的数字大小
            int current = nums[index] % mark;
            // NOTE: 因为会有恰好和数组长度大小相同的元素, 所以需要判断
            if (current < nums.length && nums[current] < mark) {
                nums[current] += mark;
            }
        }
        for (int index = 0; index < nums.length; index++) {
            // NOTE: 只要元素长度比数组长度大, 那么就是被标记过的
            if (nums[index] < mark) {
                return index;
            }
        }
        return nums.length;
    }

    /**
     * <p>置换: 空间复杂度 O(1)</p>
     */
    public static int missNumberSwap(int[] nums) {
        for (int index = 0; index < nums.length; index++) {
            while (nums[index] < nums.length && nums[index] != nums[nums[index]]) {
                swap(nums, index, nums[index]);
            }
        }
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != index) {
                return index;
            }
        }
        return nums.length;
    }

    private static void swap(int[] nums, int first, int second) {
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }


}
