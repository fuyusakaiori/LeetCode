package matrix

// 螺旋矩阵
type SpiralMatrixI struct {}

// 模拟: 单个元素模拟
func (array *SpiralMatrixI) SpiralOrderSingle(matrix [][]int) []int {
	row, col := 0, 0
	rowLength, colLength := len(matrix), len(matrix[0])
	visited := make([][]bool, rowLength)
	directIndex := 0
	directions := [][]int{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}
	nums := make([]int, 0)
	for index := range visited {
		visited[index] = make([]bool, colLength)
	}
	for len(nums) < rowLength * colLength {
		visited[row][col] = true
		nums = append(nums, matrix[row][col])
		newRow, newCol := row + directions[directIndex][0], col + directions[directIndex][1]
		if newRow < 0 || newCol < 0 || newRow >= rowLength || newCol >= colLength || visited[newRow][newCol] {
			directIndex = (directIndex + 1) % 4
		}
		row, col = row + directions[directIndex][0], col + directions[directIndex][1]
	}
	return nums
}

// 模拟: 按层模拟
func (array *SpiralMatrixI) SpiralOrderLevel(matrix [][]int) []int {
	top, down := 0, len(matrix) - 1
	left, right := 0, len(matrix[0]) - 1
	rowLength, colLength := len(matrix), len(matrix[0])
	nums := make([]int, 0)
	for len(nums) < rowLength * colLength {
		for index := left; index <= right && len(nums) < rowLength * colLength; index++ {
			nums = append(nums, matrix[top][index])
		}
		top++
		for index := top; index <= down && len(nums) < rowLength * colLength; index++ {
			nums = append(nums, matrix[index][right])
		}
		right--
		for index := right; index >= left && len(nums) < rowLength * colLength; index-- {
			nums = append(nums, matrix[down][index])
		}
		down--
		for index := down; index >= top && len(nums) < rowLength * colLength; index-- {
			nums = append(nums, matrix[index][left])
		}
		left++
	}
	return nums
}

// 螺旋矩阵 II
type SpiralMatrixII struct {}

// 模拟: 单个元素模拟
func (array *SpiralMatrixII) GenerateMatrixSingle(number int) [][]int {
	row, col := 0, 0
	visited := make([][]bool, number)
	directIndex := 0
	directions := [][]int{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}
	matrix := make([][]int, number)
	for index := range visited {
		visited[index] = make([]bool, number)
	}
	for index := range matrix {
		matrix[index] = make([]int, number)
	}
	for value := 1; value <= number * number; value++ {
		visited[row][col] = true
		matrix[row][col] = value
		newRow, newCol := row + directions[directIndex][0], col + directions[directIndex][1]
		if newRow < 0 || newCol < 0 || newRow >= number || newCol >= number || visited[newRow][newCol] {
			directIndex = (directIndex + 1) % 4
		}
		row, col = row + directions[directIndex][0], col + directions[directIndex][1]
	}
	return matrix
}

// 模拟: 按层模拟
func (array *SpiralMatrixII) GenerateMatrixLevel(number int) [][]int {
	value := 1
	top, down := 0, number - 1
	left, right := 0, number - 1
	matrix := make([][]int, number)
	for index := range matrix {
		matrix[index] = make([]int, number)
	}
	for value <= number * number {
		for index := left; index <= right; index++ {
			matrix[top][index] = value
			value++
		}
		top++
		for index := top; index <= down; index++ {
			matrix[index][right] = value
			value++
		}
		right--
		for index := right; index >= left; index-- {
			matrix[down][index] = value
			value++
		}
		down--
		for index := down; index >= top; index-- {
			matrix[index][left]= value
			value++
		}
		left++
	}
	return matrix
}