package chapter07.optimum.array;

/**
 * <p>子数组最大平均数</p>
 */
public class MaxAverage {

    /**
     * <p>滑动窗口</p>
     */
    public static double findMaxAverage(int[] nums, int count) {
        // 1. 滑动窗口: 不需要使用任何数据结构
        int curValue = 0;
        // 2. 初始化滑动窗口
        for (int index = 0; index < count; index++) {
            curValue += nums[index];
        }
        int maxValue = curValue;
        // 3. 滑动窗口左右指针
        for (int left = 0, right = count; right < nums.length; right++) {
            curValue += nums[right];
            curValue -= nums[left++];
            maxValue = Math.max(maxValue, curValue);
        }
        return (double) maxValue / count;
    }

}
