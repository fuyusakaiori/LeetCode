package chapter09.dynamic.backpack;

/**
 * <p>整数拆分</p>
 */
public class IntegerBreak {

    /**
     * <p>记忆化搜索</p>
     * <p>1. 因为整数必须要拆分, 所以不能直接将原本的整数作为乘积</p>
     * <p>2. 其次大多数的数字拆分后的乘积肯定是比原本的整数要大, 所以是需要在内循环时使用等号的</p>
     * <p>3. 但是前几个数 (2 和 3) 不拆分就是会比拆分后的乘积大, 所以前几个数又需要考虑不使用等号</p>
     * <p>4. 想要避免这种情况可以采用这种方式: </p>
     * <p>4.1. 如果将内循环范围减半, 那么就可以避免直接将原本的整数返回的情况</p>
     * <p>4.2. 但是这会导致后续的递归都是需要拆分的, 但是我们又需要不拆分的情况</p>
     * <p>4.3. 所以最终我们就需要调整下动态转移方程</p>
     */
    public static int integerBreakMemory(int number) {
        int[] dp = new int[number + 1];
        return integerBreakMemory(number, dp);
    }

    public static int integerBreakMemory(int number, int[] dp) {
        if (number == 0) {
            return 1;
        }
        if (dp[number] != 0) {
            return dp[number];
        }
        int maxProduction = 1;
        // NOTE: 范围减半的目的就是为了规避直接将原本的整数返回的情况
        for (int value = 1; value <= number / 2; value++) {
            maxProduction = Math.max(maxProduction,
                    // NOTE: 第一个值表示不再继续拆分, 直接使用剩下的值; 第二个值就表示继续拆分
                    Math.max(value * (number - value), integerBreakMemory(number - value, dp) * value));
        }
        dp[number] = maxProduction;
        return dp[number];
    }

    /**
     * <p>动态规划: 正序 [推荐]</p>
     */
    public static int integerBreakForward(int number) {
        int[] dp = new int[number + 1];
        for (int index = 1; index <= number; index++) {
            int maxProduction = 1;
            for (int value = 1; value <= index / 2; value++) {
                maxProduction = Math.max(maxProduction, Math.max(value * (index - value), dp[index - value] * value));
            }
            dp[index] = maxProduction;
        }
        return dp[number];
    }

    /**
     * <p>动态规划: 逆序</p>
     */
    public static int integerBreakBackward(int number) {
        return 0;
    }

}
