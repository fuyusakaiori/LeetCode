package chapter09.dynamic.path;

/**
 * <p>出界的路径数</p>
 */
public class PathOutOfBounds {

    private final static int MOD = 1000000007;

    /**
     * <p>记忆化搜索</p>
     */
    public static int findPathsMemory(int row, int col, int maxMove, int startRow, int startCol) {
        Integer[][][] dp = new Integer[row][col][maxMove + 1];
        return findPathsMemory(startRow, startCol, maxMove, row, col, dp);
    }

    public static int findPathsMemory(int startRow, int startCol, int maxMove, int row, int col, Integer[][][] dp) {
        if (startRow < 0 || startCol < 0 || startRow >= row || startCol >= col) {
            // NOTE: 出界的时候必须保证最大的移动步数不能为负
            return maxMove >= 0 ? 1 : 0;
        }
        if (maxMove == 0) {
            return 0;
        }
        if (dp[startRow][startCol][maxMove] != null) {
            return dp[startRow][startCol][maxMove];
        }
        int sum = 0;
        sum += findPathsMemory(startRow + 1, startCol, maxMove - 1, row, col, dp) % MOD;
        sum += findPathsMemory(startRow - 1, startCol, maxMove - 1, row, col, dp) % MOD;
        sum += findPathsMemory(startRow, startCol + 1, maxMove - 1, row, col, dp) % MOD;
        sum += findPathsMemory(startRow, startCol - 1, maxMove - 1, row, col, dp) % MOD;
        dp[startRow][startCol][maxMove] = sum;
        return dp[startRow][startCol][maxMove];
    }

    /**
     * <p>动态规划: 不好写</p>
     */
    public static int findPath(int row, int col, int maxMove, int startRow, int startCol) {
        return 0;
    }

}
