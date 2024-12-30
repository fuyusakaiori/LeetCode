package chapter05.matrix;

import java.util.Arrays;

/**
 * <p>矩阵置零</p>
 * <p>类似 “移动零” </h3>
 */
public class ZeroMatrix {

    /**
     * 模拟: 遍历标记有 0 的坐标, 原地标记
     */
    public static void setZero(int[][] matrix) {
        boolean rowFlag = false, colFlag = false;
        // NOTE: 提前记录第一行是否存在 0, 会使用第一行作为标记数组
        for (int index = 0; index < matrix[0].length; index++) {
            if (matrix[0][index] == 0) {
                rowFlag = true;
                break;
            }
        }
        // NOTE: 提前记录第一列是否存在 0, 会使用第一行作为标记数组
        for (int index = 0; index < matrix.length; index++) {
            if (matrix[index][0] == 0) {
                colFlag = true;
                break;
            }
        }
        // NOTE: 遍历矩阵, 记录所有存在 0 的行和列
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == 0) {
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }
        // NOTE: 对应的行和列填充为 0
        for (int row = 1; row < matrix.length; row++) {
            for (int col = 1; col < matrix[0].length; col++) {
                if (matrix[row][0] == 0 || matrix[0][col] == 0) {
                    matrix[row][col] = 0;
                }
            }
        }
        if (rowFlag) {
            for (int index = 0; index < matrix[0].length; index++) {
                matrix[0][index] = 0;
            }
        }
        if (colFlag) {
            for (int index = 0; index < matrix.length; index++) {
                matrix[index][0] = 0;
            }
        }

    }

    /**
     * 模拟: 遍历标记有 0 的坐标, 额外数组标记
     */
    public static void setZeroFlag(int[][] matrix) {
        boolean[] rowFlags = new boolean[matrix.length];
        boolean[] colFlags = new boolean[matrix[0].length];
        // NOTE: 遍历标记
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == 0) {
                    rowFlags[row] = true;
                    colFlags[col] = true;
                }
            }
        }
        // NOTE: 置为 0
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] != 0 && (rowFlags[row] || colFlags[col])) {
                    matrix[row][col] = 0;
                }
            }
        }
    }

}
