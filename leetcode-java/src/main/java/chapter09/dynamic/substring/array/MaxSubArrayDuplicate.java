package chapter09.dynamic.substring.array;

/**
 * <p>最长重复子数组</p>
 */
public class MaxSubArrayDuplicate {

    /**
     * <p>记忆化搜索</p>
     */
    public static int findLengthMemory(int[] nums1, int[] nums2) {
        int maxLength = 0;
        Integer[][] dp = new Integer[nums1.length][nums2.length];
        for (int first = nums1.length - 1; first >= 0; first--) {
            for (int second = nums2.length - 1; second >= 0; second--) {
                maxLength = Math.max(maxLength, findLengthMemory(first, nums1, second, nums2, dp));
            }
        }
        return maxLength;
    }

    public static int findLengthMemory(int first, int[] nums1, int second, int[] nums2, Integer[][] dp) {
        if (first == nums1.length || second == nums2.length) {
            return 0;
        }
        if (dp[first][second] != null) {
            return dp[first][second];
        }
        if (nums1[first] == nums2[second]) {
            dp[first][second] = findLengthMemory(first + 1, nums1, second + 1, nums2, dp) + 1;
            return dp[first][second];
        }
        dp[first][second] = 0;
        return dp[first][second];
    }

    /**
     * <p>动态规划: 正序</p>
     */
    public static int findLengthForward(int[] nums1, int[] nums2) {
        int maxLength = 0;
        int[][] dp = new int[nums1.length][nums2.length];
        for (int first = 0; first < nums1.length; first++) {
            for (int second = 0; second < nums2.length; second++) {
                // NOTE: 如果不满足条件, 那么就认为公共长度为 0; 因为是将子串看成一个整体, 所以不能再去依赖任何状态
                if (nums1[first] == nums2[second]) {
                    dp[first][second] = first > 0 && second > 0 ? dp[first - 1][second - 1] + 1 : 1;
                    maxLength = Math.max(maxLength, dp[first][second]);
                }
            }
        }
        return maxLength;
    }

    /**
     * <p>动态规划: 逆序 [推荐]</p>
     */
    public static int findLengthBackward(int[] nums1, int[] nums2) {
        int maxLength = 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int first = nums1.length - 1; first >= 0; first--) {
            for (int second = nums2.length - 1; second >= 0; second--) {
                // NOTE: 如果不满足条件, 那么就认为公共长度为 0; 因为是将子串看成一个整体, 所以不能再去依赖任何状态
                if (nums1[first] == nums2[second]) {
                    dp[first][second] = dp[first + 1][second + 1] + 1;
                    maxLength = Math.max(maxLength, dp[first][second]);
                }
            }
        }
        return maxLength;
    }
}
