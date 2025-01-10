package chapter09.dynamic.backpack;

/**
 * <p>完全平方数: 相当于可以重复选择</p>
 */
public class NumSquare {

    /**
     * <p>记忆化搜索</p>
     */
    public static int numSquaresMemory(int number) {
        int[] dp = new int[number + 1];
        return numSquaresMemory(number, dp);
    }

    public static int numSquaresMemory(int number, int[] dp) {
        if (number == 0) {
            return 0;
        }
        if (dp[number] != 0) {
            return dp[number];
        }
        int minCount = Integer.MAX_VALUE;
        // NOTE: 如果自身是完全平方数, 那么会计入数量, 所以这里可以取等于
        for (int value = 1; value * value <= number; value++) {
            minCount = Math.min(minCount, numSquaresMemory(number - value * value, dp) + 1);
        }
        dp[number] = minCount;
        return dp[number];
    }

    /**
     * <p>动态规划: 正序 [推荐]</p>
     */
    public static int numSquaresForward(int number) {
        int[] dp = new int[number + 1];
        for (int index = 1; index <= number; index++) {
            int minCount = Integer.MAX_VALUE;
            for (int value = 1; value * value <= index; value++) {
                minCount = Math.min(minCount, dp[index - value * value] + 1);
            }
            dp[index] = minCount;
        }
        return dp[number];
    }

    /**
     * <p>动态规划: 逆序</p>
     */
    public static int numSquaresBackward(int number) {
        int[] dp = new int[number + 1];
        for (int index = number - 1; index >= 0; index--) {
            int minCount = Integer.MAX_VALUE;
            for (int value = 1; value * value <= number - index; value++) {
                minCount = Math.min(minCount, dp[index + value * value] + 1);
            }
            dp[index] = minCount;
        }
        return dp[0];
    }

}
