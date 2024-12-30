package chapter05.matrix;

/**
 * <p>旋转矩阵</p>
 */
public class RotateMatrix {

    /**
     * 数学: 额外空间坐标转换
     */
    public static void rotateDirect(int[][] matrix) {
        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        int[][] rotate = new int[rowLength][colLength];
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                rotate[col][colLength - row - 1] = matrix[row][col];
            }
        }
        for (int index = 0; index < rowLength; index++) {
            System.arraycopy(rotate[index], 0, matrix[index], 0, colLength);
        }
    }

    /**
     * 数学: 计算坐标, 顺时针和逆时针的计算方式有所不同
     * <p>顺时针: (row, col) -> (colLength - col, row)</p>
     * <p>逆时针: (row, col) -> (col, colLength - row)</p>
     */
    public static void rotateLocation(int[][] matrix) {
        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        // NOTE: 旋转的次数需要减半, 否则会重复旋转
        for (int row = 0; row < rowLength / 2; row++) {
            for (int col = 0; col < (colLength + 1) / 2; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[colLength - col][row];
                matrix[colLength - col][row] = matrix[colLength - row][colLength - col];
                matrix[colLength - row][colLength - col] = matrix[col][colLength - row];
                matrix[col][colLength - row] = temp;
            }
        }
    }

    /**
     * 数学: 转置矩阵 + 左右翻转
     * <p>顺时针旋转 90: 转置矩阵 + 左右翻转</p>
     * <p>顺时针旋转 180: 上下翻转 + 左右翻转</p>
     * <p>顺时针旋转 270: 转置矩阵 + 上下翻转</p>
     * <p>逆时针旋转 90: <==> 顺时针旋转 270 </p>
     * <p>逆时针旋转 180: <==> 顺时针旋转 180 </p>
     * <p>逆时针旋转 270: <==> 顺时针旋转 90 </p>
     */
    public static void rotateTranspose(int[][] matrix) {
        // NOTE: 转置矩阵
        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        for (int row = 0; row < rowLength; row++) {
            // NOTE: 如果从 0 开始, 那么会重复转置
            for (int col = row; col < colLength; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
        // NOTE: 左右翻转; 翻转次数减半, 否则会重复翻转
        for (int col = 0; col < colLength / 2; col++) {
            for (int row = 0; row < rowLength; row++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[row][colLength - col - 1];
                matrix[row][colLength - col - 1] = temp;
            }
        }
    }

}
