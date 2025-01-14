package chapter07.optimum.array;

/**
 * <p>最大连续 1 的个数</p>
 */
public class MaxConsecutiveOne {

    /**
     * <p>滑动窗口</p>
     */
    public static int longestOnes(int[] nums, int count) {
        int maxValue = 0;
        // 1. 滑动窗口
        int windows = 0;
        // 2. 滑动窗口左右指针
        for (int left = 0, right = 0; right < nums.length; right++) {
            windows += 1 - nums[right];
            // 3. 滑动窗口指针的移动条件: 只要将 0 替换成 1 的机会耗尽, 那么就需要移动左指针去恢复次数
            while (windows > count) {
                windows -= 1 - nums[left++];
            }
            maxValue = Math.max(maxValue, right - left + 1);
        }
        return maxValue;
    }

}
