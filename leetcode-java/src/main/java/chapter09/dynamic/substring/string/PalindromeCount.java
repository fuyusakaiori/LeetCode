package chapter09.dynamic.substring.string;

/**
 * <p>回文字符串</p>
 */
public class PalindromeCount {

    /**
     * <p>记忆化搜索</p>
     */
    public static int countSubstringsMemory(String string) {
        int count = 0;
        Boolean[][] dp = new Boolean[string.length()][string.length()];
        for (int first = string.length() - 1; first >= 0; first--) {
            for (int last = string.length() - 1; last >= first; last--) {
                if (isValidPalindrome(first, last, string, dp)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean isValidPalindrome(int first, int last, String string, Boolean[][] dp) {
        if (first >= last) {
            return true;
        }
        if (dp[first][last] != null) {
            return dp[first][last];
        }
        if (string.charAt(first) == string.charAt(last)) {
            dp[first][last] = isValidPalindrome(first + 1, last - 1, string, dp);
            return dp[first][last];
        }
        dp[first][last] = false;
        return dp[first][last];
    }

    /**
     * <p>动态规划: 正序 [不好写]</p>
     */
    public static int countSubstringsForward(String string) {
        return 0;
    }

    /**
     * <p>动态规划: 逆序 [推荐]</p>
     */
    public static int countSubstringsBackward(String string) {
        int count = 0;
        boolean[][] dp = new boolean[string.length()][string.length()];
        for (int first = string.length() - 1; first >= 0; first--) {
            for (int last = string.length() - 1; last >= first; last--) {
                if (string.charAt(first) == string.charAt(last)) {
                    // NOTE: 规避填充对角线的边界条件
                    dp[first][last] = first + 1 < last - 1 ? dp[first + 1][last - 1] : true;
                    count += dp[first][last] ? 1 : 0;
                }
            }
        }
        return count;
    }

    /**
     * <p>中心扩展算法</p>
     */
    public static int countSubstringsCenterExpand(String s) {
        int cnt = 0;
        char[] chars = s.toCharArray();
        // 1. 每个字符为中心结点向两侧遍历
        for(int idx = 0;idx < chars.length;idx++){
            // 2. 奇数长度的回文字符串
            int firstCnt = getPalindromeCnt(idx, idx, chars);
            // 3. 偶数长度的回文字符串
            int secondCnt = getPalindromeCnt(idx, idx + 1, chars);
            // 4. 叠加到总数量上
            cnt += firstCnt + secondCnt;
        }
        return cnt;
    }

    private static int getPalindromeCnt(int left, int right, char[] chars){
        int cnt = 0;
        while(left >= 0 && right <= chars.length - 1){
            if(chars[left--] != chars[right++])
                return cnt;
            cnt++;
        }
        return cnt;
    }
}
