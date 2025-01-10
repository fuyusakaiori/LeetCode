package chapter09.dynamic.subsequence.string;

/**
 * <p>最长回文子序列</p>
 */
public class LongestPalindromeSubsequence {

    /**
     * <p>记忆化搜索</p>
     */
    public static int longestPalindromeSubseqMemory(String string) {
        Integer[][] dp = new Integer[string.length()][string.length()];
        return longestPalindromeSubseqMemory(0, string.length() - 1, string, dp);
    }

    public static int longestPalindromeSubseqMemory(int first, int last, String string, Integer[][] dp) {
        if (first > last) {
            return 0;
        }
        if (first == last) {
            return 1;
        }
        if (dp[first][last] != null) {
            return dp[first][last];
        }
        if (string.charAt(first) == string.charAt(last)) {
            dp[first][last] = longestPalindromeSubseqMemory(first + 1, last - 1, string, dp) + 2;
        } else {
            dp[first][last] = Math.max(
                    longestPalindromeSubseqMemory(first + 1, last, string, dp),
                    longestPalindromeSubseqMemory(first, last - 1, string, dp)
            );
        }
        return dp[first][last];
    }

    /**
     * <p>动态规划: 正序 [不好写]</p>
     */
    public static int longestPalindromeSubseqForward(String string) {
        return 0;
    }

    /**
     * <p>动态规划: 逆序 [推荐]</p>
     */
    public static int longestPalindromeSubseqBackward(String string) {
        int[][] dp = new int[string.length()][string.length()];
        for (int first = string.length() - 1; first >= 0; first--) {
            for (int last = first; last < string.length(); last++) {
                if (first == last) {
                    dp[first][last] = 1;
                    continue;
                }
                if (string.charAt(first) == string.charAt(last)) {
                    dp[first][last] = dp[first + 1][last - 1] + 2;
                } else {
                    dp[first][last] = Math.max(dp[first + 1][last], dp[first][last - 1]);
                }
            }
        }
        return dp[0][string.length() - 1];
    }

    /**
     * <p>反转字符串 => 最长公共子序列</p>
     */
    public static int longestPalindromeSubseqReverse(String string) {
        return LongestCommonSubsequence.longestCommonSubsequenceBackward(string, new StringBuilder(string).reverse().toString());
    }

}
