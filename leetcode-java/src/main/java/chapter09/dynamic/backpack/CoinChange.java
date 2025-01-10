package chapter09.dynamic.backpack;

/**
 * <p>零钱兑换</p>
 */
public class CoinChange {

    /**
     * <p>零钱兑换: 可以重复选择</p>
     */
    public static class CoinChangeI {

        /**
         * <p>记忆化搜索: 需要回头重新选择之前的元素, 建议使用循环, 不使用循环动态规划表维数会变高</p>
         */
        public static int coinChangeMemory(int[] coins, int amount) {
            Integer[] dp = new Integer[amount + 1];
            return coinChangeMemory(amount, coins, dp);
        }

        public static int coinChangeMemory(int amount, int[] coins, Integer[] dp) {
            if (amount == 0) {
                return 1;
            }
            if (amount < 0) {
                return -1;
            }
            if (dp[amount] != null) {
                return dp[amount];
            }
            int minCount = -1;
            for (int index = 0; index < coins.length; index++) {
                int count = coinChangeMemory(amount - coins[index], coins, dp);
                if (count != -1) {
                    minCount = minCount == -1 ? count + 1 : Math.min(minCount, count + 1);
                }
            }
            return minCount;
        }

        /**
         * <p>动态规划: 正序 [推荐]</p>
         * <p>记忆化搜索采用的是金额减少到 0 完成的, 所以正序是符合递归的逻辑的</p>
         */
        public static int coinChangeForward(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            for (int money = 1; money <= amount; money++) {
                int minCount = -1;
                for (int index = 0; index < coins.length; index++) {
                    if (coins[index] <= money) {
                        int count = dp[money - coins[index]];
                        if (count != -1) {
                            minCount = minCount == -1 ? count + 1 : Math.min(minCount, count + 1);
                        }
                    }
                }
                dp[money] = minCount;
            }
            return dp[amount];
        }

        /**
         * <p>动态规划: 逆序</p>
         * <p>记忆化搜索采用的是金额减少到 0 完成的, 所以逆序反而是不符合递归逻辑的</p>
         */
        public static int coinChangeBackward(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            // NOTE: 持有金额就是目标金额时不需要处理, 不能设置成 -1
            for (int money = amount - 1; money >= 0; money--) {
                int minCount = -1;
                for (int index = 0; index < coins.length; index++) {
                    if (coins[index] + money <= amount) {
                        // NOTE: 会溢出
                        int count = dp[coins[index] + money];
                        if (count != -1) {
                            minCount = minCount == -1 ? count + 1 : Math.min(count + 1, minCount);
                        }
                    }
                }
                dp[money] = minCount;
            }
            return dp[0];
        }

    }

    /**
     * <p>零钱兑换 II: 可以重复选择</p>
     */
    public static class CoinChangeII {

        /**
         * <p>记忆化搜索: 不能回头重新选择之前的元素, 会导致组合重复, 可以使用循环也可以不使用</p>
         */
        public static int changeMemoryInnerLoop(int amount, int[] coins) {
            Integer[][] dp = new Integer[coins.length][amount + 1];
            return changeMemoryInnerLoop(0, amount, coins, dp);
        }

        public static int changeMemoryInnerLoop(int start, int amount, int[] coins, Integer[][] dp) {
            if (amount == 0) {
                return 1;
            }
            if (amount < 0 || start == coins.length) {
                return 0;
            }
            if (dp[start][amount] != null) {
                return dp[start][amount];
            }
            int count = 0;
            for (int index = start; index < coins.length; index++) {
                count += Math.min(count, changeMemoryInnerLoop(index, amount - coins[index], coins, dp));
            }
            dp[start][amount] = count;
            return dp[start][amount];
        }

        public static int changeMemory(int amount, int[] coins) {
            Integer[][] dp = new Integer[coins.length][amount + 1];
            return changeMemory(0, amount, coins, dp);
        }

        public static int changeMemory(int index, int amount, int[] coins, Integer[][] dp) {
            if (amount == 0) {
                return 1;
            }
            if (index == coins.length || amount < 0) {
                return 0;
            }
            if (dp[index][amount] != null) {
                return dp[index][amount];
            }
            dp[index][amount] = changeMemory(index + 1, amount, coins, dp)
                    + changeMemory(index, amount - coins[index], coins, dp);
            return dp[index][amount];
        }

        /**
         * <p>动态规划: 正序 [推荐]</p>
         */
        public static int changeForward(int amount, int[] coins) {
            int[][] dp = new int[amount + 1][coins.length];
            for (int index = 0; index < coins.length; index++) {
                dp[0][index] = 1;
            }
            for (int money = 1; money <= amount; money++) {
                for (int index = 0; index < coins.length; index++) {
                    if (index > 0) {
                        dp[money][index] += dp[money][index - 1];
                    }
                    if (coins[index] <= money) {
                        dp[money][index] += dp[money - coins[index]][index]    ;
                    }
                }
            }
            return dp[amount][coins.length - 1];
        }

        /**
         * <p>动态规划: 逆序</p>
         */
        public static int changeBackward(int amount, int[] coins) {
            int[][] dp = new int[amount + 1][coins.length];
            for (int money = amount - 1; money >= 0; money--) {
                for (int index = coins.length - 1; index >= 0; index--) {
                    // NOTE: 会溢出
                    if (money + coins[index] < amount) {
                        dp[money][index] += dp[money + coins[index]][index];
                    }
                    if (index < coins.length - 1) {
                        dp[money][index] += dp[money][index + 1];
                    }
                }
            }
            return dp[0][0];
        }

    }

}
