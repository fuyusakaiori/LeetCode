package chapter09.dynamic.substring.string;

import java.util.*;

/**
 * <p>1. 分割回文串</p>
 * <p>2. 分割回文串 II</p>
 */
public class PalindromeSplit {

    /**
     * <p>分割回文串: 数量</p>
     */
    public static class SplitPalindromeI {

        /**
         * <p>记忆化搜索 + 回溯 [回溯最好使用循环写]</p>
         */
        public static List<List<String>> partitionMemory(String string) {
            List<String> palindrome = new ArrayList<>();
            List<List<String>> palindromes = new ArrayList<>();
            Boolean[][] dp = new Boolean[string.length()][string.length()];
            for (int first = string.length() - 1; first >= 0; first--) {
                for (int last = string.length() - 1; last >= first; last--) {
                    dp[first][last] = isValidPalindrome(first, last, string, dp);
                }
            }
            splitPalindromeMemory(0, string, palindrome, palindromes, dp);
            return palindromes;
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

        public static void splitPalindromeMemory(int start, String string, List<String> palindrome, List<List<String>> palindromes, Boolean[][] dp) {
            if (start == string.length()) {
                palindromes.add(new ArrayList<>(palindrome));
                return;
            }
            for (int index = start; index < string.length(); index++) {
                if (dp[start][index]) {
                    palindrome.add(string.substring(start, index + 1));
                    splitPalindromeMemory(index + 1, string, palindrome, palindromes, dp);
                    palindrome.remove(palindrome.size() - 1);
                }
            }
        }

        /**
         * <p>动态规划: 正序 + 回溯 [不推荐]</p>
         */
        public static List<List<String>> partitionForward(String string) {
            return Collections.emptyList();
        }

        /**
         * <p>动态规划: 逆序 + 回溯 [推荐]</p>
         */
        public static List<List<String>> partitionBackward(String string) {
            List<String> palindrome = new ArrayList<>();
            List<List<String>> palindromes = new ArrayList<>();
            boolean[][] dp = new boolean[string.length()][string.length()];
            for (int first = string.length() - 1; first >= 0; first--) {
                for (int last = string.length() - 1; last >= first; last--) {
                    if (string.charAt(first) == string.charAt(last)) {
                        dp[first][last] = first + 1 < last - 1 ? dp[first + 1][last - 1] : true;
                    }
                }
            }
            splitPalindromeBackward(0, string, palindrome, palindromes, dp);
            return palindromes;
        }

        public static void splitPalindromeBackward(int start, String string, List<String> palindrome, List<List<String>> palindromes, boolean[][] dp) {
            if (start == string.length()) {
                palindromes.add(new ArrayList<>(palindrome));
                return;
            }
            for (int index = start; index < string.length(); index++) {
                if (dp[start][index]) {
                    palindrome.add(string.substring(start, index + 1));
                    splitPalindromeBackward(index + 1, string, palindrome, palindromes, dp);
                    palindrome.remove(palindrome.size() - 1);
                }
            }
        }


    }

    /**
     * <p>分割回文串 II: 最小分割次数</p>
     */
    public static class SplitPalindromeII {

        /**
         * <p>记忆化搜索</p>
         */
        public static int minCutMemory(String string) {
            Boolean[][] dp1 = new Boolean[string.length()][string.length()];
            for (int first = string.length() - 1; first >= 0; first--) {
                for (int last = string.length() - 1; last >= first; last--) {
                    dp1[first][last] = isValidPalindrome(first, last, string, dp1);
                }
            }
            Integer[] dp2 = new Integer[string.length()];
            return splitPalindrome(0, string, dp1, dp2) - 1;
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

        public static int splitPalindrome(int start, String string, Boolean[][] dp1, Integer[] dp2) {
            if (start == string.length()) {
                return 0;
            }
            if (dp2[start] != null) {
                return dp2[start];
            }
            int minCount = Integer.MAX_VALUE;
            for (int index = start; index < string.length(); index++) {
                if (dp1[start][index]) {
                    minCount = Math.min(splitPalindrome(index + 1, string, dp1, dp2) + 1, minCount);
                }
            }
            dp2[start] = minCount;
            return dp2[start];
        }

        /**
         * <p>动态规划: 正序 [不好写]</p>
         */
        public static int minCutForward(String string) {
            return 0;
        }

        /**
         * <p>动态规划: 逆序 [推荐]</p>
         */
        public static int minCutBackward(String string) {
            boolean[][] dp1 = new boolean[string.length()][string.length()];
            for (int first = string.length() - 1; first >= 0; first--) {
                for (int last = string.length() - 1; last >= first; last--) {
                    if (string.charAt(first) == string.charAt(last)) {
                        dp1[first][last] = first + 1 < last - 1 ? dp1[first + 1][last - 1] : true;
                    }
                }
            }
            int[] dp2 = new int[string.length() + 1];
            for (int start = string.length() - 1; start >= 0; start--) {
                int minCount = Integer.MAX_VALUE;
                for (int index = string.length() - 1; index >= start; index--) {
                    if (dp1[start][index]) {
                        minCount = Math.min(minCount, dp2[index + 1] + 1);
                    }
                }
                dp2[start] = minCount;
            }
            return dp2[0];
        }
    }

}
