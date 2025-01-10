package chapter09.dynamic.path;

/**
 * <h3>不同路径</h3>
 */
public class PathUnique {

    /**
     * <p>不同路径</p>
     */
    public static class UniquePathI {

        /**
         * <p>记忆化搜索</p>
         */
        public static int uniquePathsMemory(int row, int col) {
            int[][] dp = new int[row][col];
            return uniquePathsMemory(row, col, dp);
        }

        public static int uniquePathsMemory(int row, int col, int[][] dp) {
            if (row == 0 && col == 0) {
                return 1;
            }
            if (row < 0 || col < 0) {
                return 0;
            }
            if (dp[row][col] != -1) {
                return dp[row][col];
            }
            dp[row][col] = uniquePathsMemory(row - 1, col, dp) + uniquePathsMemory(row, col - 1, dp);
            return dp[row][col];
        }

        /**
         * <p>动态规划: 正向</p>
         */
        public static int uniquePathForward(int row, int col) {
            // NOTE: dp[i][j] 表示从 (0,0) 到 (i,j) 的路径数
            int[][] dp = new int[row][col];
            for (int index = 0; index < row; index++) {
                dp[index][0] = 1;
            }
            for (int index = 0; index < col; index++) {
                dp[0][index] = 1;
            }
            for (int rowIndex = 1; rowIndex < row; rowIndex++) {
                for (int colIndex = 1; colIndex < col; colIndex++) {
                    dp[rowIndex][colIndex] = dp[rowIndex - 1][colIndex] + dp[rowIndex][colIndex - 1];
                }
            }
            return dp[row - 1][col - 1];
        }

        /**
         * <p>动态规划: 逆向 [推荐]</p>
         */
        public static int uniquePathBackward(int row, int col) {
            // NOTE: dp[i][j] 表示从 (i,j) 到 (row - 1, col - 1) 的路径数
            int[][] dp = new int[row + 1][col + 1];
            for (int rowIndex = row - 1; rowIndex >= 0; rowIndex--) {
                for (int colIndex = col - 1; colIndex >= 0; colIndex--) {
                    if (rowIndex == row - 1 && colIndex == col - 1) {
                        dp[rowIndex][colIndex] = 1;
                        continue;
                    }
                    dp[rowIndex][colIndex] = dp[rowIndex + 1][colIndex] + dp[rowIndex][colIndex + 1];
                }
            }
            return dp[0][0];
        }

    }

    /**
     * <p>不同路径 II</p>
     */
    public static class UniquePathII {

        /**
         * <p>记忆化搜索</p>
         */
        public static int uniquePathsWithObstaclesMemory(int[][] obstacleGrid) {
            Integer[][] dp = new Integer[obstacleGrid.length][obstacleGrid[0].length];
            return uniquePathsWithObstaclesMemory(0, 0, obstacleGrid, dp);
        }

        public static int uniquePathsWithObstaclesMemory(int row, int col, int[][] obstacleGrid, Integer[][] dp) {
            if (row >= obstacleGrid.length || col > obstacleGrid[0].length || obstacleGrid[row][col] == 1) {
                return 0;
            }
            // NOTE: 因为终点可能已经被障碍物阻挡, 所以需要先判断有没有障碍物
            if (row == obstacleGrid.length - 1 && col == obstacleGrid.length - 1) {
                return 1;
            }
            if (dp[row][col] != null) {
                return dp[row][col];
            }
            dp[row][col] = uniquePathsWithObstaclesMemory(row + 1, col, obstacleGrid, dp)
                    + uniquePathsWithObstaclesMemory(row, col + 1, obstacleGrid, dp);
            return dp[row][col];
        }

        /**
         * <p>动态规划: 正向</p>
         */
        public static int uniquePathsWithObstaclesForward(int[][] obstacleGrid) {
            // NOTE: dp[i][j] 表示从 (0,0) 到 (i,j) 的路径数
            int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
            for (int index = 0; index < obstacleGrid.length && obstacleGrid[index][0] == 0; index++) {
                // NOTE: 如果路径中存在障碍物, 那么后面的就都不用判断了
                dp[index][0] = obstacleGrid[index][0] == 0 ? 1: 0;
            }
            for (int index = 0; index < obstacleGrid[0].length && obstacleGrid[0][index] == 0; index++) {
                dp[0][index] = obstacleGrid[0][index] == 0 ? 1: 0;
            }
            for (int row = 1; row < obstacleGrid.length; row++) {
                for (int col = 1; col < obstacleGrid[0].length; col++) {
                    if (obstacleGrid[row][col] == 0) {
                        dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
                    }
                }
            }
            return dp[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
        }

        /**
         * <p>动态规划: 逆向 [推荐]</p>
         */
        public static int uniquePathsWithObstaclesBackward(int[][] obstacleGrid) {
            // NOTE: dp[i][j] 表示从 (i,j) 到 (row - 1, col - 1) 的路径数
            int[][] dp = new int[obstacleGrid.length + 1][obstacleGrid[0].length + 1];
            for (int row = obstacleGrid.length - 1; row >= 0; row--) {
                for (int col = obstacleGrid[0].length - 1; col >=0; col--) {
                    if (obstacleGrid[row][col] == 1) {
                        dp[row][col] = 0;
                        continue;
                    }
                    if (row == obstacleGrid.length - 1 && col == obstacleGrid[0].length - 1) {
                        dp[row][col] = 1;
                        continue;
                    }
                    dp[row][col] = dp[row + 1][col] + dp[row][col + 1];
                }
            }
            return dp[0][0];
        }


    }

}
