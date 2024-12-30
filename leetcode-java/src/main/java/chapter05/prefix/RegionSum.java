package chapter05.prefix;

/**
 * <p>1. 区域和检索-矩阵不可变</p>
 * <p>2. 二维区域和检索-矩阵不可变</p>
 */
public class RegionSum {

    /**
     * <p>区域和检索-矩阵不可变</p>
     */
    public static class NumArray {

        private static int[] prefixSum;

        /**
         * <p>前缀和: 前缀和必须在构造函数中初始化, 否则每次调用都初始化前缀和就没有意义了, 那还不如直接遍历数组</p>
         */
        public NumArray(int[] nums) {
            prefixSum = new int[nums.length + 1];
            for (int index = 0; index < nums.length; index++) {
                prefixSum[index + 1] = prefixSum[index] + nums[index];
            }
        }

        public static int sumRange(int left, int right) {
            return prefixSum[right + 1] - prefixSum[left];
        }

    }

    /**
     * <p>二维区域和检索-矩阵不可变</p>
     */
    public static class NumMatrix1D {

        public static int[][] prefixSum;

        /**
         * <p>一维前缀和: 可以计算每一行的前缀和或者计算每一列的前缀和</p>
         */
        public NumMatrix1D(int[][] matrix) {
            prefixSum = new int[matrix.length][matrix[0].length + 1];
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[0].length; col++) {
                    prefixSum[row][col + 1] = prefixSum[row][col] + matrix[row][col];
                }
            }
        }

        public static int sumRegion(int row1, int col1, int row2, int col2) {
            int sum = 0;
            for (int row = row1; row <= row2; row++) {
                sum += prefixSum[row][col2 + 1] - prefixSum[row][col1];
            }
            return sum;
        }
    }

    /**
     * <p>二维区域和检索-矩阵不可变</p>
     */
    public static class NumMatrix2D {

        public static int[][] prefixSum;

        /**
         * <p>一维前缀和: 可以计算每一行的前缀和或者计算每一列的前缀和</p>
         */
        public NumMatrix2D(int[][] matrix) {
            prefixSum = new int[matrix.length + 1][matrix[0].length + 1];
            for (int row = 1;row <= matrix.length; row++) {
                for (int col = 1; col <= matrix[0].length; col++) {
                    prefixSum[row][col] = prefixSum[row - 1][col] + prefixSum[row][col - 1] - prefixSum[row - 1][col - 1] + matrix[row - 1][col - 1];
                }
            }
        }

        public static int sumRegion(int row1, int col1, int row2, int col2) {
            row1++; col1++; row2++; col2++;
            return prefixSum[row2][col2] + prefixSum[row1 - 1][col1 - 1] - prefixSum[row1 - 1][col2] - prefixSum[row2][col1 - 1];
        }
    }

}
