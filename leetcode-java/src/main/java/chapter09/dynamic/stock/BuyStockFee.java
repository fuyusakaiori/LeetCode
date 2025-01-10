package chapter09.dynamic.stock;

/**
 * <h3>买卖股票的最佳时机含手续费</h3>
 */
public class BuyStockFee {

    /**
     * <p>记忆化搜索</p>
     */
    public static int maxProfitMemory(int[] prices, int fee) {
        Integer[][] dp = new Integer[prices.length][2];
        return maxProfitMemory(0, 0, fee, prices, dp);
    }

    public static int maxProfitMemory(int index, int hasStock, int fee, int[] prices, Integer[][] dp) {
        if (index == prices.length) {
            return 0;
        }
        if (dp[index][hasStock] != null) {
            return dp[index][hasStock];
        }
        if (hasStock > 0) {
            dp[index][hasStock] = Math.max(
                    maxProfitMemory(index + 1, 1, fee, prices, dp),
                    // NOTE: 最好在交易完成时结算手续费, 动态规划的边界条件会更好写
                    maxProfitMemory(index + 1, 0, fee, prices, dp) + prices[index] - fee);
            return dp[index][hasStock];
        }
        dp[index][hasStock] = Math.max(
                maxProfitMemory(index + 1, 0, fee, prices, dp),
                maxProfitMemory(index + 1, 1, fee, prices, dp) - prices[index]);
        return dp[index][hasStock];
    }

    /**
     * <p>动态规划: 正向 [推荐]</p>
     */
    public static int maxProfitForward(int[] prices, int fee) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int index = 1; index < prices.length; index++) {
            dp[index][0] = Math.max(dp[index - 1][0], dp[index - 1][1] + prices[index] - fee);
            // NOTE: 如果要在买入的时候计算手续费, 那么边界条件也得加上手续费
            dp[index][1] = Math.max(dp[index - 1][1], dp[index - 1][0] - prices[index]);
        }
        return dp[prices.length - 1][0];
    }

    /**
     * <p>动态规划: 逆向</p>
     */
    public static int maxProfitBackward(int[] prices, int fee) {
        int[][] dp = new int[prices.length][2];
        dp[prices.length - 1][0] = 0;
        dp[prices.length - 1][1] = prices[prices.length - 1];
        for (int index = prices.length - 2; index >= 0; index--) {
            dp[index][0] = Math.max(dp[index + 1][0], dp[index + 1][1] - prices[index] - fee);
            dp[index][1] = Math.max(dp[index + 1][1], dp[index + 1][0] + prices[index]);
        }
        return dp[0][0];
    }
}
