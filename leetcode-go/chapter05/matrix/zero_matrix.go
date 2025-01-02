package matrix

// 矩阵置零
type ZeroMatrix struct {}

// 模拟: 空间复杂度为 O(n)
func (array *ZeroMatrix) SetZeroesArray(matrix [][]int)  {
	rowFlags := make([]bool, len(matrix))
	colFlags := make([]bool, len(matrix[0]))
	for row := 0; row < len(matrix); row++ {
		for col := 0; col < len(matrix[0]); col++ {
			if matrix[row][col] == 0 {
				rowFlags[row] = true
				colFlags[col] = true
			}
		}
	}
	for row := 0; row < len(matrix); row++ {
		for col := 0; col < len(matrix[0]); col++ {
			if rowFlags[row] || colFlags[col] {
				matrix[row][col] = 0
			}
		}
	}
}

// 模拟: 空间复杂度为 O(1)
func (array *ZeroMatrix) SetZeroes(matrix [][]int)  {
	rowFlag, colFlag := false, false
	for index := 0; index < len(matrix[0]); index++ {
		if matrix[0][index] == 0 {
			rowFlag = true
			break
		}
	}
	for index := 0; index < len(matrix); index++ {
		if matrix[index][0] == 0 {
			colFlag = true
			break
		}
	}
	for row := 0; row < len(matrix); row++ {
		for col := 0; col < len(matrix[0]); col++ {
			if matrix[row][col] == 0 {
				matrix[row][0] = 0
				matrix[0][col] = 0
			}
		}
	}
	for row := 1; row < len(matrix); row++ {
		for col := 1; col < len(matrix[0]); col++ {
			if matrix[row][0] == 0 || matrix[0][col] == 0 {
				matrix[row][col] = 0
			}
		}
	}
	if rowFlag {
		for index := 0; index < len(matrix[0]); index++ {
			matrix[0][index] = 0
		}
	}
	if colFlag {
		for index := 0; index < len(matrix); index++ {
			matrix[index][0] = 0
		}
	}
}