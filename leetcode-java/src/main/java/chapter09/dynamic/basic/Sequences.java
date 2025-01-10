package chapter09.dynamic.basic;

/**
 * <p>1. 斐波那契</p>
 * <p>2. 泰波那契</p>
 */
public class Sequences {

    /**
     * <p>斐波那契</p>
     */
    public static class Fibonacci {

        /**
         * <p>记忆化搜索</p>
         */
        public static int fibMemory(int number) {
            int[] dp = new int[number + 1];
            return fibMemory(number, dp);
        }

        public static int fibMemory(int number, int[] dp) {
            // NOTE: 终止条件
            if (number == 0 || number == 1) {
                return number;
            }
            if (dp[number] != 0) {
                return dp[number];
            }
            return dp[number] = fibMemory(number - 1, dp) + fibMemory(number - 2, dp);
        }

        /**
         * <p>动态规划: 正向 [推荐]</p>
         * <p>动态规划还可以采用状态压缩减少动态规划数组的维数, 建议直接参考官方题解</p>
         */
        public static int fibForward(int number) {
            // NOTE: dp[i] 表示 [0:i] 范围内位置 i 的斐波那契数
            int[] dp = new int[number + 1];
            // NOTE: 边界条件, 可以采用初始化的方式或者放入循环中判断
            dp[0] = 0;
            dp[1] = 1;
            // NOTE: 边界条件最好不要是动态规划数组的长度, 含义不够明确
            for (int index = 2; index <= number; index++) {
                // NOTE: 正向填写动态规划的转移方程和递归是相反的
                dp[index] = dp[index - 1] + dp[index - 2];
            }
            return dp[number];
        }

        /**
         * <p>动态规划: 逆向</p>
         * <p>动态规划还可以采用状态压缩减少动态规划数组的维数, 建议直接参考官方题解</p>
         */
        public static int fibBackward(int number) {
            // NOTE: dp[i] 表示 [i:] 范围内位置 number - i 的斐波那契数
            int[] dp = new int[number + 1];
            // NOTE: 斐波那契逆向动态规划不要在外面初始化边界条件, 会有越界问题
            for (int index = number; index >= 0; index--) {
                // NOTE: 边界条件, 可以采用初始化或者放入循环判断
                if (index == number || index == number - 1) {
                    dp[index] = number - index;
                    continue;
                }
                // NOTE: 逆向填写动态规划的动态转移方程和递归是相同的
                dp[index] = dp[index + 1] + dp[index + 2];
            }
            return dp[0];
        }


    }

    /**
     * <p>第 N 个泰波那契数</p>
     */
    public static class Tribonacci {

        /**
         * <p>记忆化搜索</p>
         */
        public static int tribonacciMemory(int number) {
            int[] dp = new int[number + 1];
            return tribonacciMemory(number, dp);
        }

        public static int tribonacciMemory(int number, int[] dp) {
            if (number == 0) {
                return 0;
            }
            if (number == 1 || number == 2) {
                return 1;
            }
            if (dp[number] != 0) {
                return dp[number];
            }
            return dp[number] = tribonacciMemory(number - 1, dp)
                    + tribonacciMemory(number - 2, dp)
                    + tribonacciMemory(number - 3, dp);
        }

        /**
         * <p>动态规划: 正向 [推荐]</p>
         */
        public static int tribonacciForward(int number) {
            // NOTE: dp[i] 表示 [0:i] 范围内位置 i 的泰波那契数
            int[] dp = new int[number + 1];
            for (int index = 0; index <= number; index++) {
                if (index == 0) {
                    dp[index] = 0;
                } else if (index == 1 || index == 2) {
                    dp[index] = 1;
                } else {
                    dp[index] = dp[index - 1] + dp[index - 2] + dp[index - 3];
                }
            }
            return dp[number];
        }

        /**
         * <p>动态规划: 逆向</p>
         */
        public static int tribonacciBackward(int number) {
            // NOTE: dp[i] 表示 [i:] 范围内位置 i 的泰波那契数
            int[] dp = new int[number + 1];
            for (int index = number; index >= 0; index--) {
                if (index == number) {
                    dp[index] = 0;
                } else if (index == number - 1 || index == number - 2) {
                    dp[index] = 1;
                } else {
                    dp[index] = dp[index + 1] + dp[index + 2] + dp[index + 3];
                }
            }
            return dp[0];
        }
    }
}
