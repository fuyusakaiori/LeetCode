package chapter09.dynamic.substring.array;

/**
 * <p>最长连续递增序列</p>
 */
public class MaxSubArrayLCS {

    /**
     * <p>记忆化搜索</p>
     */
    public static int findLengthOfLCISMemory(int[] nums) {
        int maxLength = 0;
        Integer[] dp = new Integer[nums.length];
        for (int index = nums.length - 1; index >= 0; index--) {
            maxLength = Math.max(maxLength, findLengthOfLCISMemory(index, nums, dp));
        }
        return maxLength;
    }

    public static int findLengthOfLCISMemory(int index, int[] nums, Integer[] dp) {
        if (index == nums.length) {
            return 0;
        }
        if (dp[index] != null) {
            return dp[index];
        }
        if (index < nums.length - 1 && nums[index] < nums[index + 1]) {
            dp[index] = findLengthOfLCISMemory(index + 1, nums, dp) + 1;
            return dp[index];
        }
        dp[index] = 0;
        return dp[index];
    }

    /**
     * <p>动态规划: 正序</p>
     */
    public static int findLengthOfLCISForward(int[] nums) {
        int maxLength = 0;
        int[] dp = new int[nums.length];
        for (int index = 0; index < nums.length; index++) {
            if (index > 0 && nums[index - 1] < nums[index]) {
                dp[index] = dp[index - 1] + 1;
            } else {
                dp[index] = 1;
            }
            maxLength = Math.max(maxLength, dp[index]);
        }
        return maxLength;
    }

    /**
     * <p>动态规划: 逆序 [推荐]</p>
     */
    public static int findLengthOfLCISBackward(int[] nums) {
        int maxLength = 0;
        int[] dp = new int[nums.length];
        for (int index = nums.length - 1; index >= 0; index--) {
            if (index < nums.length - 1 && nums[index] < nums[index + 1]) {
                dp[index] = dp[index + 1] + 1;
            } else {
                dp[index] = 1;
            }
            maxLength = Math.max(maxLength, dp[index]);
        }
        return maxLength;
    }

}
