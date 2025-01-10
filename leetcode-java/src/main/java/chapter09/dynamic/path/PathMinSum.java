package chapter09.dynamic.path;

import java.util.Arrays;
import java.util.List;

/**
 * <p>1. 最小路径和</p>
 * <p>2. 三角形最小路径和</p>
 * <p>3. 下降路径最小和</p>
 */
public class PathMinSum {

    /**
     * <p>最小路径和</p>
     */
    public static class MinPathSum {

        /**
         * <p>记忆化搜索</p>
         */
        public static int minPathSumMemory(int[][] grid) {
            Integer[][] dp = new Integer[grid.length][grid[0].length];
            return minPathSumMemory(0, 0, grid, dp);
        }

        /**
         * <p>记忆化搜索</p>
         */
        public static int minPathSumMemory(int row, int col, int[][] grid, Integer[][] dp) {
            if (row >= grid.length || col >= grid[0].length) {
                return Integer.MAX_VALUE;
            }
            if (row == grid.length - 1 && col == grid.length - 1) {
                return grid[grid.length - 1][grid[0].length - 1];
            }
            if (dp[row][col] != null) {
                return dp[row][col];
            }
            dp[row][col] = grid[row][col] + Math.min(
                    minPathSumMemory(row + 1, col, grid, dp),
                    minPathSumMemory(row, col + 1, grid, dp));
            return dp[row][col];
        }

        /**
         * <p>动态规划: 正向 [推荐]</p>
         */
        public static int minPathSumForward(int[][] grid) {
            // NOTE: dp[i][j] 表示从 (0, 0) 到 (i, j) 的最小路径和
            int[][] dp = new int[grid.length][grid[0].length];
            for (int index = 0; index < grid.length; index++) {
                dp[index][0] = index > 0 ? grid[index][0] + dp[index - 1][0] : grid[index][0];
            }
            for (int index = 0; index < grid[0].length; index++) {
                dp[0][index] = index > 0 ? grid[0][index] + dp[0][index - 1] : grid[0][index];
            }
            for (int row = 1; row < grid.length; row++) {
                for (int col = 1; col < grid[0].length; col++) {
                    dp[row][col] = grid[row][col] + Math.min(dp[row - 1][col], dp[row][col - 1]);
                }
            }
            return dp[grid.length - 1][grid[0].length - 1];
        }

        /**
         * <p>动态规划: 逆向</p>
         */
        public static int minPathSumBackward(int[][] grid) {
            // NOTE: dp[i][j] 表示从 (i, j) 到 (grid.length - 1, grid[0].length - 1) 的最小路径和
            int[][] dp = new int[grid.length + 1][grid[0].length + 1];
            for (int index = 0; index < grid.length; index++) {
                dp[index][grid[0].length] = Integer.MAX_VALUE;
            }
            for (int index = 0; index < grid[0].length; index++) {
                dp[grid.length][index] = Integer.MAX_VALUE;
            }
            for (int row = grid.length - 1; row >= 0; row--) {
                for (int col = grid[0].length - 1; col >= 0; col--) {
                    if (row == grid.length - 1 && col == grid[0].length - 1) {
                        dp[row][col] = grid[row][col];
                        continue;
                    }
                    dp[row][col] = grid[row][col] + Math.min(dp[row + 1][col], dp[row][col + 1]);
                }
            }
            return dp[0][0];
        }


    }

    /**
     * <p>三角形最小路径和</p>
     */
    public static class TriangleMinPathSum {

        /**
         * <p>记忆化搜索</p>
         */
        public static int minimumTotalMemory(List<List<Integer>> triangle) {
            Integer[][] dp = new Integer[triangle.size()][triangle.size()];
            return minimumTotalMemory(0, 0, triangle, dp);
        }

        public static int minimumTotalMemory(int level, int index, List<List<Integer>> triangle, Integer[][] dp) {
            if (level == triangle.size()) {
                return 0;
            }
            if (dp[level][index] != null) {
                return dp[level][index];
            }
            dp[level][index] = triangle.get(level).get(index) + Math.min(
                    minimumTotalMemory(level + 1, index, triangle, dp),
                    minimumTotalMemory(level + 1, index + 1, triangle, dp));
            return dp[level][index];
        }

        /**
         * <p>动态规划: 正向</p>
         */
        public static int minimumTotalForward(List<List<Integer>> triangle) {
            int levels = triangle.size();
            // NOTE: dp[i][j] 表示从 (0, 0) 到 (i, j) 的最小路径和
            int[][] dp = new int[levels][levels];
            for (int level = 0; level < levels; level++) {
                for (int index = 0; index < level + 1; index++) {
                    if (level == 0) {
                        dp[level][index] = triangle.get(level).get(index);
                        continue;
                    }
                    // NOTE: 每行的第一个元素, 只依赖上一行正对着的元素
                    if (index == 0) {
                        dp[level][index] = triangle.get(level).get(index) + dp[level - 1][index];
                        continue;
                    }
                    // NOTE: 每一行的最后一个元素, 只依赖上一行斜对着的元素
                    if (index == level) {
                        dp[level][index] = triangle.get(level).get(index) + dp[level - 1][index - 1];
                        continue;
                    }
                    dp[level][index] = triangle.get(level).get(index) + Math.min(dp[level - 1][index], dp[level - 1][index - 1]);
                }
            }
            return Arrays.stream(dp[triangle.size() - 1]).min().getAsInt();
        }

        /**
         * <p>动态规划: 逆向 [推荐]</p>
         */
        public static int minimumTotalBackward(List<List<Integer>> triangle) {
            int levels = triangle.size();
            // NOTE: dp[i][j] 表示从 (i, j) 到最后一层的最小路径和
            int[][] dp = new int[levels + 1][levels + 1];
            for (int level = levels - 1; level >= 0; level--) {
                for (int index = level; index >= 0; index--) {
                    dp[level][index] = triangle.get(level).get(index)
                            + Math.min(dp[level + 1][index], dp[level + 1][index + 1]);
                }
            }
            return dp[0][0];
        }

        /**
         * <p>动态规划: 状态压缩</p>
         */
        public static int minimumTotalCompact(List<List<Integer>> triangle) {
            int levels = triangle.size();
            // NOTE: dp[i][j] 表示从 (i, j) 到最后一层的最小路径和
            int[] dp = new int[levels + 1];
            for (int level = levels - 1; level >= 0; level--) {
                // NOTE: 这里只能从左向右填, 从右向左会导致上一层的最小路径和被覆盖
                for (int index = 0; index < level + 1; index++) {
                    dp[index] = triangle.get(level).get(index) + Math.min(dp[index], dp[index + 1]);
                }
            }
            return dp[0];
        }

    }

    /**
     * <p>下降路径最小和</p>
     */
    public static class FallingMinPathSum {

        /**
         * <p>记忆化搜索</p>
         */
        public static int minFallingPathSumMemory(int[][] matrix) {
            int minSum = Integer.MAX_VALUE;
            Integer[][] dp = new Integer[matrix.length][matrix[0].length];
            for (int index = 0; index < matrix[0].length; index++) {
                minSum = Math.min(minSum, minFallingPathSumMemory(0, index, matrix, dp));
            }
            return minSum;
        }

        public static int minFallingPathSumMemory(int level, int index, int[][] matrix, Integer[][] dp) {
            if (level == matrix.length || index < 0 || index >= matrix[0].length) {
                return Integer.MAX_VALUE;
            }
            if (level == matrix.length - 1) {
                return matrix[level][index];
            }
            if (dp[level][index] != null) {
                return dp[level][index];
            }
            int down = minFallingPathSumMemory(level + 1, index, matrix, dp);
            int left = minFallingPathSumMemory(level + 1, index - 1, matrix, dp);
            int right = minFallingPathSumMemory(level + 1, index + 1, matrix, dp);
            dp[level][index] = matrix[level][index] + Math.min(down, Math.min(left, right));
            return dp[level][index];
        }

        /**
         * <p>动态规划: 正向 [没有区别]</p>
         */
        public static int minFallingPathSumForward(int[][] matrix) {
            // NOTE: dp[i][j] 表示 (0, j) 到 dp[i][j] 的最小路径和
            int[][] dp = new int[matrix.length][matrix[0].length];
            for (int index = 0; index < matrix[0].length; index++) {
                dp[0][index] = matrix[0][index];
            }
            for (int level = 1; level < matrix.length; level++) {
                for (int index = 0; index < matrix[0].length; index++) {
                    int down = Integer.MAX_VALUE, left = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
                    if (index > 0) {
                        left = dp[level - 1][index - 1];
                    }
                    if (index < matrix[0].length - 1){
                        right = dp[level - 1][index + 1];
                    }
                    down = dp[level - 1][index];
                    dp[level][index] = matrix[level][index] + Math.min(down, Math.min(left, right));
                }
            }
            return Arrays.stream(dp[matrix.length - 1]).min().getAsInt();
        }

        /**
         * <p>动态规划: 逆向 [没有区别]</p>
         */
        public static int minFallingPathSumBackWard(int[][] matrix) {
            // NOTE: dp[i][j] 表示 (i, j) 到最后一层的最小路径和
            int[][] dp = new int[matrix.length][matrix[0].length];
            for (int index = 0; index < matrix[0].length; index++) {
                dp[matrix.length - 1][index] = matrix[matrix.length - 1][index];
            }
            for (int level = matrix.length - 2; level >= 0; level--) {
                for (int index = matrix[0].length - 1; index >= 0; index--) {
                    int down = Integer.MAX_VALUE, left = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
                    if (index > 0) {
                        left = dp[level + 1][index - 1];
                    }
                    if (index < matrix.length - 1) {
                        right = dp[level + 1][index + 1];
                    }
                    down = dp[level + 1][index];
                    dp[level][index] = matrix[level][index] + Math.min(down, Math.min(left, right));
                }
            }
            return Arrays.stream(dp[0]).min().getAsInt();
        }

    }

}
