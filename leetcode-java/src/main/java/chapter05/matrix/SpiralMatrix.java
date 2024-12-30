package chapter05.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>螺旋矩阵</p>
 * <p>1. 螺旋矩阵</p>
 * <p>2. 螺旋矩阵 II</p>
 */
public class SpiralMatrix {

    /**
     * 螺旋矩阵
     */
    public static class SpiralMatrixI {

        /**
         * 模拟: 单个元素模拟
         */
        public static List<Integer> spiralOrderDirection(int[][] matrix) {
            int rowLength = matrix.length;
            int colLength = matrix[0].length;
            int total = rowLength * colLength;
            // NOTE: 遍历的指针
            int row = 0, col = 0;
            // NOTE: 控制移动的方向
            int directIndex = 0;
            int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            boolean[][] visited = new boolean[rowLength][colLength];
            List<Integer> elements = new ArrayList<>();
            // NOTE: 遍历所有的元素
            for (int index = 0; index < total; index++) {
                elements.add(matrix[row][col]);
                visited[row][col] = true;
                // NOTE: 根据方向计算新的坐标
                int nextRow = row + directions[directIndex][0];
                int nextCol = col + directions[directIndex][1];
                // NOTE: 判断是否越界
                if (nextRow < 0 || nextRow >= rowLength || nextCol < 0 || nextCol >= colLength || visited[nextRow][nextCol]) {
                    // NOTE: 切换方向
                    directIndex = (directIndex + 1) % 4;
                }
                // NOTE: 不能直接使用之前的坐标, 可能需要重新计算
                row = row + directions[directIndex][0];
                col = col + directions[directIndex][1];
            }
            return elements;
        }

        /**
         * 模拟: 按层模拟
         */
        public static List<Integer> spiralOrderLevel(int[][] matrix) {
            int total = matrix.length * matrix[0].length;
            int top = 0, down = matrix.length - 1;
            int left = 0, right = matrix[0].length - 1;
            List<Integer> elements = new ArrayList<>();
            // NOTE: 所有元素都遍历完成时终止
            while (elements.size() < total) {
                // NOTE: 从左向右遍历
                for (int index = left; index <= right && elements.size() < total; index++) {
                    elements.add(matrix[top][index]);
                }
                top++;
                // NOTE: 从上向下遍历
                for (int index = top; index <= down && elements.size() < total; index++) {
                    elements.add(matrix[index][right]);
                }
                right--;
                // NOTE: 从右向左遍历
                for (int index = right; index >= left && elements.size() < total; index++) {
                    elements.add(matrix[down][index]);
                }
                down--;
                // NOTE: 从下向上遍历
                for (int index = down; index >= top && elements.size() < total; index++) {
                    elements.add(matrix[index][left]);
                }
                left++;
            }
            return elements;
        }

    }

    /**
     * 螺旋矩阵 II
     */
    public static class SpiralMatrixII {

        /**
         * 模拟: 单个元素模拟
         */
        public static int[][] generateMatrixDirection(int num) {
            int value = 1;
            int total = num * num;
            int directIndex = 0;
            int rowIndex = 0, colIndex = 0;
            boolean[][] visited = new boolean[num][num];
            int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int[][] matrix = new int[num][num];
            for (int index = 0; index < total; index++) {
                matrix[rowIndex][colIndex] = value++;
                visited[rowIndex][colIndex] = true;
                int nextRowIndex = rowIndex + directions[directIndex][0];
                int nextColIndex = colIndex + directions[directIndex][1];
                if (nextRowIndex < 0 || nextColIndex < 0 || nextRowIndex >= num || nextColIndex >= num || visited[nextRowIndex][nextColIndex]) {
                    directIndex = (directIndex + 1) % 4;
                }
                rowIndex = rowIndex + directions[directIndex][0];
                colIndex = colIndex + directions[directIndex][1];
            }
            return matrix;
        }

        public static int[][] generateMatrixLevel(int num) {
            int value = 1;
            int top = 0, down = num - 1;
            int left = 0, right = num - 1;
            int[][] matrix = new int[num][num];
            while (value <= num * num) {
                for (int index = left; index <= right; index++) {
                    matrix[top][index] = value++;
                }
                top++;
                for (int index = top; index <= down; index++) {
                    matrix[index][right] = value++;
                }
                right--;
                for (int index = right; index >= left; index--) {
                    matrix[down][index] = value++;
                }
                down--;
                for (int index = down; index >= top; index--) {
                    matrix[index][left] = value++;
                }
                left++;
            }

            return matrix;
        }

    }

}
