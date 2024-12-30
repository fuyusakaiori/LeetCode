package chapter10.array.dynamic.path;

/**
 * <h3>不同路径</h3>
 */
public class UniquePath {

    /**
     * <h3>思路: 不同路径</h3>
     * <h3>1. 动态规划</h3>
     * <h3>1.1 从上向下填更加符合动态规划的思路</h3>
     * <h3>1.2 从下向上是从递归的思路考虑的</h3>
     * <h3>2. 排列组合</h3>
     */
    private static int uniquePaths1(int rowLength, int colLength){
        int[][] dp = new int[rowLength + 1][colLength + 1];
        dp[rowLength - 1][colLength - 1] = 1;
        // 注: 没有必要提前初始化第一行或者第一列, 都是可以直接在双重循环中填充好的
        for(int row = rowLength - 1;row >= 0;row--){
            for(int col = colLength - 1;col >= 0;col--){
                dp[row][col] += dp[row + 1][col] + dp[row][col + 1];
            }
        }
        return dp[0][0];
    }

    private static int uniquePaths2(int rowLength, int colLength){
        int[][] dp = new int[rowLength][colLength];
        // 1. 初始化第一行
        for (int index = 0;index < rowLength;index++){
            dp[index][0] = 1;
        }
        // 2. 初始化第一列
        for (int index = 0;index < colLength;index++){
            dp[0][index] = 1;
        }
        // 3. 填充剩余各自
        for (int row = 1;row < rowLength;row++){
            for (int col = 1;col < colLength;col++){
                dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
            }
        }
        return dp[rowLength - 1][colLength - 1];
    }

    private static int uniquePaths3(int rowLength, int colLength){
        return dfs(rowLength, colLength, 0, 0, new int[rowLength][colLength]);
    }

    private static int dfs(int rowLength, int colLength, int row, int col, int[][] dp){
        if (row >= rowLength || col > colLength)
            return 0;
        if (row == rowLength - 1 && col == colLength - 1)
            return 1;
        if (dp[row][col] != 0)
            return dp[row][col];
        return dfs(rowLength, colLength, row + 1, col, dp)
                       + dfs(rowLength, colLength, row, col + 1, dp);
    }

    /**
     * <h3>思路: 不同路径 II</h3>
     */
    private static int uniquePathsWithObstacles1(int[][] obstacleGrid) {
        int rowLength = obstacleGrid.length, colLength = obstacleGrid[0].length;
        // 注: 如果终点直接被障碍阻挡, 那么直接返回, 因为肯定不存在到达终点的路径
        if(obstacleGrid[rowLength - 1][colLength - 1] == 1)
            return 0;
        int[][] dp = new int[rowLength + 1][colLength + 1];
        dp[rowLength - 1][colLength - 1] = 1;
        for(int row = rowLength - 1;row >= 0;row--){
            for(int col = colLength - 1;col >= 0;col--){
                if(obstacleGrid[row][col] != 1){
                    dp[row][col] += dp[row + 1][col] + dp[row][col + 1];
                }
            }
        }
        return dp[0][0];
    }


    private static int uniquePathsWithObstacles2(int[][] obstacleGrid){
        int rowLength = obstacleGrid.length, colLength = obstacleGrid[0].length;
        int[][] dp = new int[rowLength][colLength];
        // 1. 初始化第一行
        for (int index = 0;index < rowLength && obstacleGrid[index][0] == 0;index++){
            dp[index][0] = 1;
        }
        // 2, 初始化第一列
        for (int index = 0;index < colLength && obstacleGrid[0][index] == 0;index++){
            dp[0][index] = 1;
        }
        // 3. 填充剩余格子
        for (int row = 1;row < rowLength;row++){
            for (int col = 1;col < colLength;col++){
                if (obstacleGrid[row][col] != 1){
                    dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
                }
            }
        }
        return dp[rowLength - 1][colLength - 1];
    }


}
