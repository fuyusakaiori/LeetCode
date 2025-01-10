package chapter09.dynamic.subsequence.string;

/**
 * <p>最长公共子序列</p>
 */
public class LongestCommonSubsequence {

    /**
     * <p>记忆化搜索</p>
     */
    public static int longestCommonSubsequenceMemory(String text1, String text2) {
        Integer[][] dp = new Integer[text1.length()][text2.length()];
        return longestCommonSubsequenceMemory(0, text1, 0, text2, dp);
    }

    public static int longestCommonSubsequenceMemory(int first, String text1, int second, String text2, Integer[][] dp) {
        if (first == text1.length() || second == text2.length()) {
            return 0;
        }
        if (dp[first][second] != null) {
            return dp[first][second];
        }
        if (text1.charAt(first) == text2.charAt(second)) {
            dp[first][second] = longestCommonSubsequenceMemory(first + 1, text1, second + 1, text2, dp) + 1;
        } else {
            dp[first][second] = Math.max(
                    longestCommonSubsequenceMemory(first + 1, text1, second, text2, dp),
                    longestCommonSubsequenceMemory(first, text1, second + 1, text2, dp)
            );
        }
        return dp[first][second];
    }

    /**
     * <p>动态规划: 正序</p>
     * <p>1. 动态规划正序写法可以完全参照动态规划逆序的写法完成</p>
     * <p>2. 动态规划正序也可以使用提前初始化边界条件实现</p>
     */
    public static int longestCommonSubsequenceForward(String text1, String text2) {
        int[][] dp = new int[text1.length()][text2.length()];
        for (int index = 0; index < text2.length(); index++) {
            if (text1.charAt(0) == text2.charAt(index)) {
                dp[0][index] = 1;
            } else {
                dp[0][index] = index > 0 ? dp[0][index - 1] : 0;
            }
        }
        for (int index = 0; index < text1.length(); index++) {
            if (text1.charAt(index) == text2.charAt(0)) {
                dp[index][0] = 1;
            } else {
                dp[index][0] = index > 0 ? dp[index - 1][0] : 0;
            }
        }
        for (int first = 1; first < text1.length(); first++) {
            for (int second = 1; second < text2.length(); second++) {
                if (text1.charAt(first) == text2.charAt(second)) {
                    dp[first][second] = dp[first - 1][second - 1] + 1;
                } else {
                    dp[first][second] = Math.max(dp[first - 1][second], dp[first][second - 1]);
                }
            }
        }
        return dp[text1.length() - 1][text2.length() - 1];
    }

    /**
     * <p>动态规划: 逆序</p>
     */
    public static int longestCommonSubsequenceBackward(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int first = text1.length() - 1; first >= 0; first--) {
            for (int second = text2.length() - 1; second >= 0; second--) {
                if (text1.charAt(first) == text2.charAt(second)) {
                    dp[first][second] = dp[first + 1][second + 1] + 1;
                } else {
                    dp[first][second] = Math.max(dp[first + 1][second], dp[first][second + 1]);
                }
            }
        }
        return dp[0][0];
    }
}
