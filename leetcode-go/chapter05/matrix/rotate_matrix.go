package matrix

// 旋转矩阵
type RotateMatrix struct {}

// 数学: 计算坐标
func (array *RotateMatrix) Rotate(matrix [][]int)  {
	colLimit := len(matrix) - 1
	// NOTE: 翻转次数减半
	for row := 0; row < len(matrix) / 2; row++ {
		for col := 0; col < (len(matrix) + 1) / 2; col++ {
			temp := matrix[row][col]
			matrix[row][col] = matrix[colLimit- col][row]
			matrix[colLimit- col][row] = matrix[colLimit- row][colLimit- col]
			matrix[colLimit- row][colLimit- col] = matrix[col][colLimit- row]
			matrix[col][colLimit- row] = temp
		}
	}
}

// 数学: 转置矩阵
func (array *RotateMatrix) RotateTranspose(matrix [][]int)  {
	rowLength, colLength := len(matrix), len(matrix)
	// NOTE: 转置矩阵
	for row := 0; row < rowLength; row++ {
		// NOTE: 不是从零开始遍历
		for col := row; col < colLength; col++ {
			matrix[row][col], matrix[col][row] = matrix[col][row], matrix[row][col]
		}
	}
	// NOTE: 左右翻转矩阵
	for col := 0; col < colLength / 2; col++ {
		for row := 0; row < rowLength; row++ {
			matrix[row][col], matrix[row][colLength - 1 - col] = matrix[row][colLength - 1 - col], matrix[row][col]
		}
	}
}