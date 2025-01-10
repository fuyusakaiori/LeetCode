package chapter09.dynamic.stock;

/**
 * <p>买卖股票最佳时机 II</p>
 * <p>买卖股票最佳时机 III</p>
 * <p>买卖股票最佳时机 IV</p>
 */
public class BuyMultipleStock {

    /**
     * <p>买卖股票最佳时机 II => 买卖股票最佳时机 IV</p>
     */
    public static class BuyStockII {

        /**
         * <p>记忆化搜索</p>
         * <p>1. 是否可以在当天买入并卖出不会对结果造成任何实际影响, 当天买入卖出利润实际就是 0</p>
         * <p>2. 记忆化搜索不要去尝试当天买入当天卖出的写法, 会导致动态规划的改写受到影响</p>
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
                dp[index][hasStock] = Math.max(
                        maxProfitMemory(index + 1, 1, prices, dp),
                        maxProfitMemory(index + 1, 0, prices, dp) + prices[index]);
                return dp[index][hasStock];
            }
            dp[index][hasStock] = Math.max(
                    maxProfitMemory(index + 1, 0, prices, dp),
                    maxProfitMemory(index + 1, 1, prices, dp) - prices[index]);
            return dp[index][hasStock];
        }

        /**
         * <p>动态规划: 正向</p>
         */
        public static int maxProfitForward(int[] prices) {
            int[][] dp = new int[prices.length][2];
            dp[0][0] = 0;
            dp[0][1] = -prices[0];
            for (int index = 1; index < prices.length; index++) {
                dp[index][0] = Math.max(dp[index - 1][0], dp[index - 1][1] + prices[index]);
                dp[index][1] = Math.max(dp[index - 1][1], dp[index - 1][0] - prices[index]);
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
                dp[index][1] = Math.max(dp[index + 1][1], dp[index + 1][0] + prices[index]);
            }
            return dp[0][0];
        }

    }

    /**
     * <p>买卖股票最佳时机 III => 买卖股票最佳时机 IV</p>
     */
    public static class BuyStockIII {

        /**
         * <p>记忆化搜索</p>
         */
        public static int maxProfitMemory(int[] prices) {
            return BuyStockIV.maxProfitMemory(2, prices);
        }

        /**
         * <p>动态规划: 正向</p>
         */
        public static int maxProfitForward(int[] prices) {
            return BuyStockIV.maxProfitForward(2, prices);
        }

        /**
         * <p>动态规划: 逆向</p>
         */
        public static int maxProfitBackward(int[] prices) {
            return BuyStockIV.maxProfitBackward(2, prices);
        }

    }

    /**
     * <p>买卖股票最佳时机 IV</p>
     */
    public static class BuyStockIV {

        /**
         * <p>记忆化搜索</p>
         */
        public static int maxProfitMemory(int count, int[] prices) {
            Integer[][][] dp = new Integer[count + 1][prices.length][2];
            return maxProfitMemory(0, 0, count, prices, dp);
        }

        public static int maxProfitMemory(int index, int hasStock, int count, int[] prices, Integer[][][] dp) {
            if (index == prices.length || count == 0) {
                return 0;
            }
            if (dp[count][index][hasStock] != null) {
                return dp[count][index][hasStock];
            }
            if (hasStock > 0) {
                dp[count][index][hasStock] = Math.max(
                        maxProfitMemory(index + 1, 1, count, prices, dp),
                        maxProfitMemory(index + 1, 0, count - 1, prices, dp) + prices[index]
                );
                return dp[count][index][hasStock];
            }
            dp[count][index][hasStock] = Math.max(
                    maxProfitMemory(index + 1, 0, count, prices, dp),
                    // NOTE: 不要在交易还没完成时就减少可交易数量, 会导致可交易数量为 0 提前退出递归
                    maxProfitMemory(index + 1, 1, count, prices, dp) - prices[index]
            );
            return dp[count][index][hasStock];
        }

        /**
         * <p>动态规划: 正向 [推荐]</p>
         */
        public static int maxProfitForward(int count, int[] prices) {
            int[][][] dp = new int[count + 1][prices.length][2];
            for (int index = 0; index <= count; index++) {
                dp[index][0][0] = 0;
                dp[index][0][1] = -prices[0];
            }
            for (int index = 0; index < prices.length; index++) {
                dp[0][index][0] = 0;
                dp[0][index][1] = -prices[index];
            }
            for (int current = 1; current <= count; current++) {
                for (int index = 1; index < prices.length; index++) {
                    dp[current][index][0] = Math.max(dp[current][index - 1][0], dp[current][index - 1][1] + prices[index]);
                    // TODO: 为什么可以不依赖 dp[current][index - 1][0]
                    dp[current][index][1] = Math.max(dp[current][index - 1][1], dp[current - 1][index - 1][0] - prices[index]);
                }
            }
            return dp[count][prices.length - 1][0];
        }

        /**
         * <p>动态规划: 逆向 [搁置]</p>
         */
        public static int maxProfitBackward(int count, int[] prices) {
            if(count == 0) return 0;
            int[][][] dp = new int[prices.length + 1][2][count + 1];
            for(int index = prices.length - 1;index >= 0;index--){
                for(int limit = 0;limit <= count;limit++){
                    // 注: 这里的 0 代表的是没有进行任何交易
                    dp[index][0][limit] = Math.max(dp[index + 1][0][limit],
                            dp[index + 1][1][limit] - prices[index]);
                    dp[index][1][limit] = Math.max(dp[index + 1][1][limit],
                            limit - 1 > 0 ? dp[index + 1][0][limit - 1] + prices[index] : prices[index]);
                }
            }
            return dp[0][0][count];
        }

    }

}
