package chapter09.dynamic.stock;

/**
 * <h3>买卖股票最佳时机含冷冻期</h3>
 */
public class BuyStockFrozen {

    /**
     * <p>记忆化搜索</p>
     */
    public static int maxProfitMemory(int[] prices) {
        Integer[][] dp = new Integer[prices.length][2];
        return maxProfitMemory(0, 0, prices, dp);
    }

    public static int maxProfitMemory(int index, int hasStock, int[] prices, Integer[][] dp) {
        // NOTE: 有冷冻期时可能超过数组的长度
        if (index >= prices.length) {
            return 0;
        }
        if (dp[index][hasStock] != null) {
            return dp[index][hasStock];
        }
        if (hasStock > 0) {
            dp[index][hasStock] = Math.max(
                    maxProfitMemory(index + 1, 1, prices, dp),
                    // NOTE: 卖出股票后不能选择相邻的股票
                    maxProfitMemory(index + 2, 0, prices, dp) + prices[index]);
            return dp[index][hasStock];
        }
        dp[index][hasStock] = Math.max(
                maxProfitMemory(index + 1, 0, prices, dp),
                maxProfitMemory(index + 1, 1, prices, dp) - prices[index]);
        return dp[index][hasStock];
    }

    /**
     * <p>动态规划: 正向 [推荐]</p>
     */
    public static int maxProfitForward(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int index = 1; index < prices.length; index++) {
            dp[index][0] = Math.max(dp[index - 1][0], dp[index - 1][1] + prices[index]);
            // NOTE: 因为 dp[index - 1][0] - prices[index] = dp[index][1], 所以没有必要依赖
            dp[index][1] = Math.max(dp[index - 1][1], index > 1 ? dp[index - 2][0] - prices[index] : -prices[index]);
        }
        return dp[prices.length - 1][0];
    }

    /**
     * <p>动态规划: 逆向</p>
     */
    public static int maxProfitBackward(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[prices.length - 1][0] = 0;
        dp[prices.length - 1][1] = prices[prices.length - 1];
        for (int index = prices.length - 2; index >= 0; index--) {
            dp[index][0] = Math.max(dp[index + 1][0], dp[index + 1][1] - prices[index]);
            dp[index][1] = Math.max(dp[index + 1][1], index < prices.length - 2 ? dp[index + 2][0] + prices[index] : prices[index]);
        }
        return dp[0][0];
    }

}
