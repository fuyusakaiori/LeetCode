package chapter07.optimum.array;

/**
 * <p>长度最小的子数组</p>
 */
public class MinLengthSubArray {

    /**
     * <p>滑动窗口</p>
     */
    public static int minSubArrayLen(int target, int[] nums) {
        int minLength = 0;
        int current = 0;
        // 1. 滑动窗口左右指针: 不需要使用数据结构维护
        for (int left = 0, right = 0; right < nums.length; right++) {
            current += nums[right];
            // 2. 滑动窗口左指针移动条件: 当前的和大于目标值, 就需要移动左指针缩减和
            while (left <= right && current >= target) {
                // NOTE: 判断条件需要使用等号, 否则会有判断不到的情况
                if (minLength == 0 || minLength > right - left + 1) {
                    minLength = right - left + 1;
                }
                current -= nums[left++];
            }
        }
        return minLength;
    }

}
