package chapter09.dynamic.substring.array;

import java.util.Arrays;

/**
 * <p>乘积最大子数组</p>
 */
public class MaxSubArrayProduct {

    /**
     * <p>记忆化搜索</p>
     * <p>1. 因为负数 x 负数可能得到正数, 所以不能仅将最大值返回, 还需要同时返回最小值</p>
     * <p>2. 因为需要返回两个值, 所以记忆化表也会从一维直接升维成二维</p>
     */
    public static int maxProductMemory(int[] nums) {
        int maxProduction = Integer.MIN_VALUE;
        int[][] dp = new int[nums.length][2];
        for (int index = 0; index < dp.length; index++) {
            Arrays.fill(dp[index], Integer.MIN_VALUE);
        }
        for (int index = nums.length - 1; index >= 0; index--) {
            maxProduction = Math.max(maxProduction, maxProductMemory(index, nums, dp)[1]);
        }
        return maxProduction;
    }

    public static int[] maxProductMemory(int index, int[] nums, int[][] dp) {
        if (index == nums.length) {
            return new int[]{1, 1};
        }
        if (dp[index][0] != Integer.MIN_VALUE && dp[index][1] != Integer.MIN_VALUE) {
            return dp[index];
        }
        // NOTE: 返回最大和最小
        int[] values = maxProductMemory(index + 1, nums, dp);
        // NOTE: 当前值, 当前值 x 最大值, 当前值 x 最小值 比较
        int maxValue = Math.max(nums[index], Math.max(nums[index] * values[0], nums[index] * values[1]));
        int minValue = Math.min(nums[index], Math.min(nums[index] * values[0], nums[index] * values[1]));
        dp[index] = new int[]{minValue, maxValue};
        return dp[index];
    }

    /**
     * <p>动态规划: 正序</p>
     */
    public static int maxProductForward(int[] nums) {
        int maxProduction = Integer.MIN_VALUE;
        int[][] dp = new int[nums.length][2];
        for (int index = 0; index < nums.length; index++) {
            dp[index][0] = index == 0 ? nums[index]
                    : Math.max(nums[index], Math.max(dp[index + 1][0] * nums[index], dp[index + 1][1] * nums[index]));
            dp[index][1] = index == 0 ? nums[index]
                    : Math.min(nums[index], Math.min(dp[index + 1][0] * nums[index], dp[index + 1][1] * nums[index]));
            maxProduction = Math.max(maxProduction, dp[index][0]);
        }
        return maxProduction;
    }

    /**
     * <p>动态规划: 逆序 [推荐]</p>
     */
    public static int maxProductBackward(int[] nums) {
        int maxProduction = Integer.MIN_VALUE;
        int[][] dp = new int[nums.length][2];
        for (int index = nums.length - 1; index >= 0; index--) {
            dp[index][0] = index == nums.length - 1 ? nums[index]
                    : Math.max(nums[index], Math.max(dp[index + 1][0] * nums[index], dp[index + 1][1] * nums[index]));
            dp[index][1] = index == nums.length - 1 ? nums[index]
                    : Math.min(nums[index], Math.min(dp[index + 1][0] * nums[index], dp[index + 1][1] * nums[index]));
            maxProduction = Math.max(maxProduction, dp[index][0]);
        }
        return maxProduction;
    }

}
