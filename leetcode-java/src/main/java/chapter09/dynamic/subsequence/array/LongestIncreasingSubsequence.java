package chapter09.dynamic.subsequence.array;

/**
 * <p>最长递增子序列</p>
 */
public class LongestIncreasingSubsequence {

    /**
     * <p>记忆化搜索</p>
     */
    public static int lengthOfLISMemory(int[] nums) {
        int maxLength = 0;
        int[] dp = new int[nums.length];
        for (int index = 0; index < nums.length - 1; index++) {
            maxLength = Math.max(maxLength, lengthOfLISMemory(index, nums, dp) + 1);
        }
        return maxLength;
    }

    public static int lengthOfLISMemory(int start, int[] nums, int[] dp) {
        if (start == nums.length) {
            return 0;
        }
        if (dp[start] != 0) {
            return dp[start];
        }
        int maxLength = 0;
        for (int index = start + 1; index < nums.length; index++) {
            if (nums[start] < nums[index]) {
                maxLength = Math.max(maxLength, lengthOfLISMemory(index, nums, dp) + 1);
            }
        }
        dp[start] = maxLength;
        return dp[start];
    }

    /**
     * <p>动态规划: 正序</p>
     */
    public static int lengthOfLISForward(int[] nums) {
        int maxLength = 0;
        int[] dp = new int[nums.length];
        for (int index = 0; index < nums.length; index++) {
            dp[index] = 1;
            for (int current = 0; current < index; current++) {
                if (nums[index] > nums[current]) {
                    dp[index] = Math.max(dp[index], dp[current] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[index]);
        }
        return maxLength;
    }

    /**
     * <p>动态规划: 逆序</p>
     */
    public static int lengthOfLISBackward(int[] nums) {
        int maxLength = 0;
        int[] dp = new int[nums.length];
        for (int index = nums.length - 1; index >= 0; index--) {
            dp[index] = 1;
            for (int current = index + 1; current < nums.length; current++) {
                if (nums[index] < nums[current]) {
                    dp[index] = Math.max(dp[index], dp[current] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[index]);
        }
        return maxLength;
    }

}
