package chapter09.dynamic.robhome;

/**
 * <p>打家劫舍 II</p>
 */
public class RobHomeCircle {

    /**
     * <p>记忆化搜索</p>
     * <p>1. 环形偷取如何转化?</p>
     * <p>1.1 因为最后一间房和第一间房是相邻的, 所以两间房不能同时偷取</p>
     * <p>1.2 如果偷取第一间房, 那么无论是偷还是不偷, 都是不能选择的, 所以可以将偷取范围变为 [0:n-1]</p>
     * <p>1.3 如果偷取最后一间房, 那么无论第一间偷还是不偷, 都是不能选择的, 所以可以将偷取范围变为[2:n]</p>
     * <p>2. 环形偷取是否需要考虑?</p>
     * <p>2.1 考虑偷取第一间房的情况:</p>
     * <p>2.1.1 如果偷取第一间房, 那么在偷取最后一间房时是无法绕回到开头的, 因为第一间房偷取过了</p>
     * <p>2.1.2 如果不偷取第一间房, 那么在偷取最后一间房时就可以绕回开头, 但是也就认为第一间房可以偷取</p>
     */
    public static int robMemory(int[] nums) {
        // NOTE: 问题被拆分为两个线性打家劫舍问题, 需要使用两张不同的记忆化表
        Integer[] dp1 = new Integer[nums.length];
        Integer[] dp2 = new Integer[nums.length];
        return Math.max(
                robMemory(0, nums.length - 1, nums, dp1),
                robMemory(1, nums.length, nums, dp2));
    }

    public static int robMemory(int start, int end, int[] nums, Integer[] dp) {
        if (start >= end) {
            return 0;
        }
        if (dp[start] != null) {
            return dp[start];
        }
        dp[start] = Math.max(
                robMemory(start + 1, end, nums, dp),
                robMemory(start + 2, end, nums, dp) + nums[start]
        );
        return dp[start];
    }

    /**
     * <p>动态规划: 正向 [推荐]</p>
     */
    public static int robForward(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp1 = new int[nums.length - 1];
        int[] dp2 = new int[nums.length - 1];
        for (int index = 0; index <= nums.length - 2; index++) {
            if (index == 0 || index == 1) {
                dp1[index] = index == 0 ? nums[index] : Math.max(nums[index], nums[index - 1]);
                dp2[index] = index == 0 ? nums[index + 1] : Math.max(nums[index], nums[index + 1]);
            } else {
                dp1[index] = Math.max(dp1[index - 1], dp1[index - 2] + nums[index]);
                dp2[index] = Math.max(dp2[index - 1], dp2[index - 2] + nums[index + 1]);
            }
        }
        return Math.max(dp1[dp1.length - 1], dp2[dp2.length - 1]);
    }

    /**
     * <p>动态规划: 逆向</p>
     */
    public static int robBackward(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp1 = new int[nums.length - 1];
        int[] dp2 = new int[nums.length - 1];
        // NOTE: 最好拆分成两个循环, 更直观
        for (int index = nums.length - 2; index >= 0; index--) {
            if (index == nums.length - 2 || index == nums.length - 3) {
                dp1[index] = index == nums.length - 2 ? nums[index] : Math.max(nums[index], nums[index + 1]);
                dp2[index] = index == nums.length - 2 ? nums[index + 1] : Math.max(nums[index + 1], nums[index + 2]);
            } else {
                // NOTE: [nums.length - 2, 0]
                dp1[index] = index == nums.length - 2 ? nums[index] : Math.max(dp1[index + 1], dp1[index + 2] + nums[index]);
                // NOTE: [nums.length - 1, 1]
                dp2[index] = index == nums.length - 2 ? nums[index + 1] : Math.max(dp2[index + 1], dp2[index + 2] + nums[index + 1]);
            }
        }
        return Math.max(dp1[0], dp2[0]);
    }
}
