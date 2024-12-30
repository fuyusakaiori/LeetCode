package chapter05.matrix;

/**
 * <p>重塑矩阵</p>
 */
public class ReshapeMatrix {

    /**
     * 数学: 计算坐标, 直接将原始矩阵转换为重塑矩阵, 通过原始矩阵的坐标计算重塑矩阵的坐标
     * <p>重塑矩阵横坐标: (row * colLength + col) / newColLength</p>
     * <p>重塑矩阵纵坐标: (row * colLength + col) % newColLength</p>
     */
    public static int[][] matrixReshapeLocationDirect(int[][] matrix, int row, int col) {
        // NOTE: 无法重塑的矩阵直接返回
        if (matrix.length * matrix[0].length != row * col) {
            return matrix;
        }
        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        int[][] reshape = new int[row][col];
        // NOTE: 遍历二维矩阵
        for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
            for (int colIndex = 0; colIndex < colLength; colIndex++) {
                // NOTE: 计算坐标
                int rowLoc = (rowIndex * colLength + colIndex) / col;
                int colLoc = (rowIndex * colLength + colIndex) % col;
                reshape[rowLoc][colLoc] = matrix[rowLoc][colLoc];
            }
        }
        return reshape;
    }

    /**
     * 数学: 计算坐标, 相当于将一维矩阵分别转换为两个不同的二维矩阵
     * <p>任何矩阵的横坐标: index / colLength</p>
     * <p>任何矩阵的纵坐标: index % colLength</p>
     */
    public static int[][] matrixReshapeLocationBridge(int[][] matrix, int row, int col) {
        if (matrix.length * matrix[0].length != row * col) {
            return matrix;
        }
        int colLength = matrix[0].length;
        int[][] reshape = new int[row][col];
        for (int index = 0; index < row * col; index++) {
            reshape[index / col][index % col] = matrix[index / colLength][index % colLength];
        }
        return reshape;
    }

}
