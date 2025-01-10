package chapter09.dynamic.robhome;

/**
 * <p>打家劫舍</p>
 */
public class RobHomeStraight {

    /**
     * <p>记忆化搜索</p>
     */
    public static int robMemory(int[] nums) {
        Integer[] dp = new Integer[nums.length];
        return robMemory(0, nums, dp);
    }

    public static int robMemory(int index, int[] nums, Integer[] dp) {
        if (index >= nums.length) {
            return 0;
        }
        if (dp[index] != null) {
            return dp[index];
        }
        dp[index] = Math.max(
                robMemory(index + 1, nums, dp),
                robMemory(index + 2, nums, dp) + nums[index]
        );
        return dp[index];
    }

    /**
     * <p>动态规划: 正向 [推荐]</p>
     */
    public static int robForward(int[] nums) {
        // NOTE: dp[i] 表示从 0 开始偷到 i 的最大利润
        int[] dp = new int[nums.length];
        for (int index = 0; index < nums.length; index++) {
            if (index == 0 || index == 1) {
                dp[index] = index == 0 ? nums[index] : Math.max(nums[0], nums[1]);
                continue;
            }
            // TODO:
            dp[index] = Math.max(dp[index - 1], dp[index - 2] + nums[index]);
        }
        return dp[nums.length - 1];
    }

    /**
     * <p>动态规划: 逆向</p>
     */
    public static int robBackward(int[] nums) {
        // NOTE: dp[i] 表示从 i 开始偷的最大利润
        int[] dp = new int[nums.length];
        for (int index = nums.length - 1; index >= 0; index--) {
            if (index == nums.length - 1 || index == nums.length - 2) {
                dp[index] = index == nums.length - 1 ? nums[index] : Math.max(nums[index], nums[index + 1]);
                continue;
            }
            // NOTE: dp[index] 依赖 dp[index + 1] 和 dp[index + 2] + nums[index], 如果没有偷窃, 那么就依赖下一个的房子, 如果偷窃就依赖后两个房子
            dp[index] = Math.max(dp[index + 1], dp[index + 2] + nums[index]);
        }
        return dp[0];
    }

}
