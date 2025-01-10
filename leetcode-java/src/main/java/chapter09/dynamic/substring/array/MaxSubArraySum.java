package chapter09.dynamic.substring.array;

/**
 * <p>最大子数组和</p>
 */
public class MaxSubArraySum {

    /**
     * <p>记忆化搜索</p>
     * <p>1. 大多数子数组的记忆化搜索都是没有办法在单次递归中完成的 [本题除外]</p>
     * <p>2. 因为无法在递归中同时做出返回当前的值和重新开始继续向下递归的操作, 所以只能在最外层套循环解决</p>
     * <p>3. 大多数子数组的记忆化搜索的外层循环顺序需要好好思考下, 否则可能无法很好利用记忆化表</p>
     */
    public static int maxSubArrayMemory(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        Integer[] dp = new Integer[nums.length];
        // NOTE: 逆序遍历会更好利用记忆化表
        for (int index = nums.length - 1; index >= 0; index--) {
            maxSum = Math.max(maxSum, maxSubArrayMemory(index, nums, dp));
        }
        return maxSum;
    }

    public static int maxSubArrayMemory(int index, int[] nums, Integer[] dp) {
        if (index == nums.length) {
            return 0;
        }
        if (dp[index] != null) {
            return dp[index];
        }
        // NOTE: 返回当前值 或者 继续向下递归
        dp[index] = Math.max(nums[index], nums[index] + maxSubArrayMemory(index + 1, nums, dp));
        return dp[index];
    }

    /**
     * <p>动态规划: 正序</p>
     */
    public static int maxSubArrayForward(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        // NOTE: dp[i] 表示从 0 开始到 i 的最大子数组和, 这个子数组不可拆分
        int[] dp = new int[nums.length];
        for (int index = 0; index < nums.length; index++) {
            dp[index] = index == 0 ? nums[index] : Math.max(nums[index], dp[index - 1]);
            maxSum = Math.max(dp[index], maxSum);
        }
        return maxSum;
    }

    /**
     * <p>动态规划: 逆序 [推荐]</p>
     */
    public static int maxSubArrayBackward(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        // NOTE: dp[i] 表示以 i 开始的子数组的最大子数组和, 这个子数组不可拆分
        int[] dp = new int[nums.length + 1];
        for (int index = nums.length - 1; index >= 0; index--) {
            dp[index] = Math.max(nums[index], dp[index + 1] + nums[index]);
            maxSum = Math.max(dp[index], maxSum);
        }
        return maxSum;
    }

}
