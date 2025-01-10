package chapter09.dynamic.path;

/**
 * <p>矩阵中的最长递增路径</p>
 */
public class PathLongestIncreasing {

    /**
     * <p>记忆化搜索</p>
     */
    public static int longestIncreasingPath(int[][] matrix) {
        int maxLength = 0;
        Integer[][] dp = new Integer[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                maxLength = Math.max(longestIncreasingPath(row, col, matrix, dp), maxLength);
            }
        }
        return maxLength;
    }

    public static int longestIncreasingPath(int row, int col, int[][] matrix, Integer[][] dp) {
        if (row < 0 || col < 0 || row >= matrix.length || col > matrix[0].length) {
            return 0;
        }
        if (dp[row][col] != null) {
            return dp[row][col];
        }
        int maxLength = 0;
        if (row > 0 && matrix[row - 1][col] > matrix[row][col]) {
            maxLength = Math.max(maxLength, longestIncreasingPath(row - 1, col, matrix, dp));
        }
        if (row < matrix.length - 1 && matrix[row + 1][col] > matrix[row][col]) {
            maxLength = Math.max(maxLength, longestIncreasingPath(row + 1, col, matrix, dp));
        }
        if (col > 0 && matrix[row][col - 1] > matrix[row][col]) {
            maxLength = Math.max(maxLength, longestIncreasingPath(row, col - 1, matrix, dp));
        }
        if (col < matrix[0].length - 1 && matrix[row][col + 1] > matrix[row][col]) {
            maxLength = Math.max(maxLength, longestIncreasingPath(row, col + 1, matrix, dp));
        }
        dp[row][col] = maxLength + 1;
        return dp[row][col];
    }

}
