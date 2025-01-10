package chapter09.dynamic.basic;


/**
 * <p>1. 爬楼梯基础问题</p>
 * <p>2. 使用最小花费爬楼梯</p>
 */
public class ClimbStair {

    /**
     * <p>爬楼梯</p>
     */
    public static class ClimbStairCombination {

        /**
         * <p>记忆化递归</p>
         */
        public static int climbStairsMemory(int number) {
            int[] dp = new int[number + 1];
            return climbStairsMemory(number, dp);
        }

        public static int climbStairsMemory(int number, int[] dp) {
            if (number == 0) {
                return 1;
            }
            if (number < 0) {
                return 0;
            }
            if (dp[number] != 0) {
                return dp[number];
            }
            return dp[number] = climbStairsMemory(number - 1, dp)
                    + climbStairsMemory(number - 2, dp);
        }

        /**
         * <p>动态规划: 正向</p>
         */
        public static int climbStairForward(int number) {
            // NOTE: dp[i] 表示 [0:i] 范围内恰好跳到第 i 个台阶的方法数
            int[] dp = new int[number + 1];
            for (int index = 0; index <= number; index++) {
                if (index == 0 || index == 1) {
                    dp[index] = 1;
                    continue;
                }
                // NOTE: dp[i] 依赖 dp[i - 1] 和 dp[i - 2], 也就是当前的台阶可以通过前面相距 1 个台阶或者 2 个台阶的位置到达
                dp[index] = dp[index - 1] + dp[index - 2];
            }
            return dp[number];
        }

        /**
         * <p>动态规划: 逆向 [推荐]</p>
         */
        public static int climbStairBackward(int number) {
            // NOTE: dp[i] 表示 [i:] 范围内从第 i 台阶恰好跳到楼顶的方法数
            int[] dp = new int[number + 1];
            for (int index = number; index >= 0; index--) {
                if (index == number || index == number - 1) {
                    dp[index] = 1;
                    continue;
                }
                // NOTE: dp[i] 依赖 dp[i + 1] 和 dp[i + 2], 也就是当前的台阶通过后面相距 1 个或者 2 个台阶的位置到达楼顶
                dp[index] = dp[index + 1] + dp[index + 2];
            }
            return dp[0];
        }

    }

    /**
     * <p>使用最小花费爬楼梯</p>
     */
    public static class ClimbStairMinCost {

        /**
         * <p>记忆化搜索</p>
         */
        public static int minCostClimbingStairsMemory(int[] cost) {
            // NOTE: 爬楼梯的花费可能为 0, 所以最好使用对象数组判断是否填充过动态动态规划
            Integer[] dp = new Integer[cost.length];
            // NOTE: 可以分别从 0 和 1 的位置开始爬楼梯, 所以需要比较最小值
            return Math.min(
                    minCostClimbingStairsMemory(0, cost, dp),
                    minCostClimbingStairsMemory(1, cost, dp));
        }


        public static int minCostClimbingStairsMemory(int index, int[] cost, Integer[] dp) {
            if (index >= cost.length) {
                return 0;
            }
            if (dp[index] != null) {
                return dp[index];
            }
            dp[index] = cost[index] + Math.min(
                    minCostClimbingStairsMemory(index + 1, cost, dp),
                    minCostClimbingStairsMemory(index + 2, cost, dp));
            return dp[index];
        }

        /**
         * <p>动态规划: 正向</p>
         */
        public static int minCostClimbingStairsForward(int[] cost) {
            // NOTE: dp[i] 表示范围 [0:i] 内以 i 作为楼顶的最小花费
            int[] dp = new int[cost.length + 1];
            // NOTE: 因为需要跳跃到楼顶, 所以边界条件时小于等于
            for (int index = 0; index <= cost.length; index++) {
                // NOTE: 如果以 i 作为楼顶, 那么为 0 的时候本身就在楼顶, 边界条件自然为 0; 那么为 1 的时候可以选择直接从楼顶开始爬, 所以也是 0
                if (index == 0 || index == 1) {
                    dp[index] = 0;
                    continue;
                }
                // NOTE: dp[i] 依赖 dp[i - 1] 和 dp[i - 2], 也就是当前的台阶可以通过前面相距 1 个或者 2 个台阶的位置到达, 选择其中最小的花费
                dp[index] = Math.min(dp[index - 1] + cost[index - 1], dp[index - 2] + cost[index - 2]);

            }
            return dp[cost.length];
        }

        /**
         * <p>动态规划: 逆向 [推荐]</p>
         */
        public static int minCostClimbingStairsBackward(int[] cost) {
            // NOTE: dp[i] 表示范围 [i:] 内从 i 开始跳跃到到楼顶的最小花费
            int[] dp = new int[cost.length + 1];
            for (int index = cost.length; index >= 0; index--) {
                if (index == cost.length) {
                    dp[index] = 0;
                } else if (index == cost.length - 1) {
                    dp[index] = cost[cost.length - 1];
                } else {
                    // NOTE: dp[i] 依赖 dp[i + 1] 和 dp[i + 2], 也就是当前的台阶通过后面相距 1 个或者 2 个台阶的位置到达楼顶, 最小的花费
                    dp[index] = cost[index] + Math.min(dp[index + 1], dp[index + 2]);
                }
            }
            // NOTE: 因为可以从 0 或者 1 开始爬楼梯, 所以需要取最小值
            return Math.min(dp[0], dp[1]);
        }
    }
}
