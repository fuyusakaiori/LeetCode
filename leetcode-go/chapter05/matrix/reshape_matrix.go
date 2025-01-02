package matrix

// 重塑矩阵
type ReshapeMatrix struct {}

// 数学: 坐标计算, 二维矩阵直接转换
func (array *ReshapeMatrix) MatrixReshapeDirect(matrix [][]int, row, col int) [][]int {
	if len(matrix) * len(matrix[0]) != row * col {
		return matrix
	}
	reshape := make([][]int, row)
	rowLength, colLength := len(matrix), len(matrix[0])
	for index := range reshape {
		reshape[index] = make([]int, col)
	}
	for rowIndex := 0; rowIndex < rowLength; rowIndex++ {
		for colIndex := 0; colIndex < colLength; colIndex++ {
			// NOTE: 注意参数不用使用错误
			newRowIndex := (rowIndex * colLength + colIndex) / col
			newColIndex := (rowIndex * colLength + colIndex) % col
			reshape[newRowIndex][newColIndex] = matrix[rowIndex][colIndex]
		}
	}
	return reshape
}

// 数学: 坐标计算, 二维矩阵使用一维矩阵作为桥接
func (array *ReshapeMatrix) MatrixReshapeBridge(matrix [][]int, row, col int) [][]int  {
	if len(matrix) * len(matrix[0]) != row * col {
		return matrix
	}
	length := row * col
	colLength := len(matrix[0])
	reshape := make([][]int, row)
	// NOTE: 需要初始化矩阵
	for index := range reshape {
		reshape[index] = make([]int, col)
	}
	for index := 0; index < length; index++ {
		reshape[index / col][index % col] = matrix[index / colLength][index % colLength]
	}
	return reshape
}
