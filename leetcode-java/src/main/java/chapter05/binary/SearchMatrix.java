package chapter05.binary;

/**
 * <h2>搜索二维矩阵问题</h2>
 * <p>1. 搜索二维矩阵</p>
 * <p>2. 搜索二维矩阵 II</p>
 */
public class SearchMatrix {

    /**
     * <p>搜索二维矩阵</p>
     */
    public static class SearchMatrixI {

        /**
         * <p>单次二分查找: 类似重塑矩阵, 可以将二维矩阵看成一维矩阵, 然后进行二分搜索</p>
         */
        public static boolean searchMatrixSingleBinarySearch(int[][] matrix, int target) {
            int rowLength = matrix.length, colLength = matrix[0].length;
            int total = rowLength * colLength;
            int left = 0, right = total - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                // NOTE: 一维矩阵坐标映射到二维矩阵
                int row = mid / colLength, col = mid % colLength;
                if (matrix[row][col] < target) {
                    right = mid - 1;
                } else if (matrix[row][col] > target) {
                    left = mid + 1;
                } else {
                    return true;
                }
            }
            return false;
        }

        /**
         * <p>两次二分查找: 先对第一列二分查找, 然后对行进行二分查找</p>
         * <p>本质: 查找第一列中最后一个比目标值小的数</p>
         * <p>1. 找到第一列中最后一个比目标值小的数, 前面的所有行中的数肯定都比目标值小, 所以就不再考虑</p>
         * <p>2. 后面的行肯定比目标值大, 所以目标值最有可能在这行中, 所以只需要再对这行进行二分</p>
         * <p>问题 1: 为什么第二题不可以这么做</p>
         * <p>1. 因为行与行之间没有明确的这种大小关系, 即使你找到第一列最后一个比目标值小的数</p>
         * <p>2. 前面的行中还有可能存在更大的数, 不能断定目标值就在这行里, 完全可能在前面的行中</p>
         * <p>问题 2: 为什么不先对行进行二分?</p>
         * <p>1.道理和刚才一样, 列与列间没有明确的大小关系, 前一列很可能存在比目标大的数, 更加接近目标值</p>
         */
        public static boolean searchMatrixDoubleBinarySearch(int[][] matrix, int target) {
            // NOTE: 对第一列进行二分查找
            int index = binarySearchFirstCol(matrix, target);
            // NOTE: 如果没有找到比目标值小的元素, 那么因为剩下的元素都比第一列的元素大, 所以不可能找到目标值
            if (index == -1) {
                return false;
            }
            // NOTE: 如果在第一列找到比目标值小的元素, 那么就在对应的行里继续二分
            return binarySearchRow(matrix, target, index);
        }

        private static int binarySearchFirstCol(int[][] matrix, int target) {
            int index = -1;
            int left = 0, right = matrix.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (matrix[mid][0] < target) {
                    // NOTE: 记录每次的中点, 每次的中点都可以是刚好小于目标值的元素, 顺便可以解决没有比目标值小的元素这个问题
                    index = mid;
                    left = mid + 1;
                } else if (matrix[mid][0] > target) {
                    right = mid - 1;
                } else {
                    return mid;
                }
            }
            return index;
        }
        
        private static boolean binarySearchRow(int[][] matrix, int target, int row) {
            int left = 0, right = matrix[0].length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (matrix[row][mid] < target) {
                    left = mid + 1;
                } else if (matrix[row][mid] > target) {
                    right = mid - 1;
                } else {
                    return true;
                }
            }
            return false;
        }

        /**
         * <p>Z 字形查找</p>
         */
        public static boolean searchMatrixZShapedSearch(int[][] matrix, int target) {
            int row = 0, col = matrix[0].length - 1;
            while (row < matrix.length && col >= 0) {
                if (matrix[row][col] > target) {
                    col--;
                } else if (matrix[row][col] < target) {
                    row++;
                } else {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * <p>搜索二维矩阵 II</p>
     */
    public static class SearchMatrixII {

        /**
         * <p>每行二分查找</p>
         */
        public static boolean searchMatrixEveryBinarySearch(int[][] matrix, int target) {
            for (int row = 0; row < matrix.length; row++) {
                // NOTE: 如果行的首元素比目标值大, 那么就可以不用继续遍历了, 后面的元素一定都比目标值大
                if (matrix[row][0] > target) {
                    return false;
                }
                // NOTE: 对行进行二分
                if (matrix[row][0] == target || binarySearch(matrix[row], target)) {
                    return true;
                }
            }
            return false;
        }

        private static boolean binarySearch(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid - 1;
                } else {
                    return true;
                }
            }
            return false;
        }

        /**
         * <p>Z 字形查找: 从右上角开始; 如果比目标值大, 左移; 如果比目标值小, 下移</p>
         */
        public static boolean searchMatrixZShapedSearch(int[][] matrix, int target) {
            int row = 0, col = matrix[0].length - 1;
            while (row < matrix.length && col >= 0) {
                if (matrix[row][col] > target) {
                    col--;
                } else if (matrix[row][col] < target) {
                    row++;
                } else {
                    return true;
                }
            }
            return false;
        }

    }


}
