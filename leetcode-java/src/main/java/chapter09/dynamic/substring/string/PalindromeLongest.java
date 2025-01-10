package chapter09.dynamic.substring.string;

/**
 * <p>最长回文子串</p>
 */
public class PalindromeLongest {

    /**
     * <p>记忆化搜索</p>
     */
    public static String longestPalindromeSubseqMemory(String string) {
        int maxLength = 0;
        int start = 0, end = 0;
        Boolean[][] dp = new Boolean[string.length()][string.length()];
        for (int first = string.length() - 1; first >= 0; first--) {
            for (int last = string.length() - 1; last >= first; last--) {
                if (isValidPalindrome(first, last, string, dp) && maxLength < end - start + 1) {
                    start = first;
                    end = last;
                    maxLength = end - start + 1;
                }
            }
        }
        return string.substring(start, end + 1);
    }

    public static boolean isValidPalindrome(int first, int last, String string, Boolean[][] dp) {
        if (first <= last) {
            return true;
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
     * <p>1. 因为正序动态规划需要先判断 [i, i+1] 这类最小的字符串是否为回文, 所以需要先移动 first 指针</p>
     * <p>2. 因为需要先移动 first 指针就需要定义在内层循环, last 指针就要定义在外层循环</p>
     * <p>3. 因为 last 指针是需要以 first 指针为边界的, 所以这个先后定义就导致写起来很麻烦</p>
     */
    public static String longestPalindromeSubseqForward(String string) {
        return "";
    }

    /**
     * <p>动态规划: 逆序 [推荐]</p>
     */
    public static String longestPalindromeSubseqBackward(String string) {
        int maxLength = 0;
        int start = 0, end = 0;
        boolean[][] dp = new boolean[string.length()][string.length()];
        for (int first = string.length() - 1; first >= 0; first--) {
            for (int last = string.length() - 1; last >= first; last--) {
                if (string.charAt(first) == string.charAt(last)) {
                    dp[first][last] = first + 1 < last - 1 ? dp[first + 1][last - 1] : true;
                    start = first;
                    end = last;
                    maxLength = Math.max(maxLength, last - first + 1);
                }
            }
        }
        return string.substring(start, end + 1);
    }

    /**
     * <p>中心扩展算法: 奇数长度和偶数长度分开算</p>
     */
    public static String longestPalindromeCenterExpand(String string) {
        char[] chars = string.toCharArray();
        // 1. 最长回文子串的边界
        int start = 0, end = 0;
        // 2. 每个字符为中心结点向两边扩展
        for(int idx = 0;idx < chars.length;idx++){
            // 3. 奇数长度的最长回文
            int firstLength = getMaxLength(idx, idx, chars);
            // 4. 偶数长度的最长回文
            int secondLength = getMaxLength(idx, idx + 1, chars);
            // 5. 判断最长回文
            int maxLength = Math.max(firstLength, secondLength);
            if(maxLength > end - start + 1){
                start = idx - (maxLength - 1) / 2;
                end = idx + maxLength / 2;
            }
        }
        return string.substring(start, end + 1);
    }

    private static int getMaxLength(int left, int right, char[] chars){
        while(left >= 0 && right <= chars.length - 1){
            if(chars[left] != chars[right])
                break;
            left--;
            right++;
        }
        return right - left - 1;
    }

}
