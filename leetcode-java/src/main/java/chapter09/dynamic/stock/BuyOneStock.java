package chapter09.dynamic.stock;

/**
 * <h3>买卖股票最佳时机</h3>
 */
public class BuyOneStock {

    /**
     * <p>记忆化搜索</p>
     */
    public static int maxProfitMemory(int[] prices) {
        Integer[][] dp = new Integer[prices.length][2];
        return maxProfitMemory(0, 0, prices, dp);
    }

    public static int maxProfitMemory(int index, int hasStock, int[] prices, Integer[][] dp) {
        if (index == prices.length) {
            return 0;
        }
        if (dp[index][hasStock] != null) {
            return dp[index][hasStock];
        }
        if (hasStock > 0) {
            dp[index][hasStock] = Math.max(prices[index], maxProfitMemory(index + 1, 1, prices, dp));
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
            // NOTE: 买入股票不能依赖之前没有股票的状态, 否则会相当于多次购买股票
            dp[index][1] = Math.max(dp[index - 1][1], -prices[index]);
        }
        // NOTE: 没有持有股票肯定比持有股票利润高
        return dp[prices.length - 1][0];
    }

    /**
     * <p>动态规划: 逆向</p>
     */
    public static int maxProfitBackward(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[prices.length - 1][0] = 0;
        // NOTE:
        dp[prices.length - 1][1] = prices[prices.length - 1];
        for (int index = prices.length - 2; index >= 0; index--) {
            dp[index][0] = Math.max(dp[index + 1][0], dp[index + 1][1] - prices[index]);
            // NOTE: 卖出股票时不能依赖没有股票的状态, 则会相当于多次购买股票
            dp[index][1] = Math.max(dp[index + 1][1], prices[index]);
        }
        // NOTE: 没有持有股票肯定比持有股票利润高
        return dp[0][0];
    }

}
