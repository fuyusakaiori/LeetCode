package binary

// 搜索二维矩阵
type SearchMatrixI struct {}

// 二分查找
func (binary *SearchMatrixI) SearchMatrix(matrix [][]int, target int) bool {
	rowLength, colLength := len(matrix), len(matrix[0])
	total := rowLength * colLength
	left, right := 0, total - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		if matrix[mid / colLength][mid % colLength] < target {
			left = mid + 1
		} else if matrix[mid / colLength][mid % colLength] > target {
			right = mid - 1
		} else {
			return true
		}
	}
	return false
}

// Z 字形查找
func (binary *SearchMatrixI) SearchMatrixZ(matrix [][]int, target int) bool {
	row, col := 0, len(matrix[0]) - 1
	for row < len(matrix) && col >= 0 {
		if matrix[row][col] > target {
			col--
		} else if matrix[row][col] < target {
			row++
		} else {
			return true
		}
	}
	return false
}


// 搜索二维矩阵 II
type SearchMatrixII struct {}

// 二分查找
func (binary *SearchMatrixII) SearchMatrix(matrix [][]int, target int) bool {
	for row := 0; row < len(matrix); row++ {
		for col := 0; col < len(matrix[0]); col++ {
			if matrix[row][col] > target {
				return false
			}
			if matrix[row][col] == target || binary.BinarySearch(matrix[row], target) {
				return true
			}
		}
	}
	return false
}

func (binary *SearchMatrixII) BinarySearch(nums []int, target int) bool {
	left, right := 0, len(nums) - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		if nums[mid] < target {
			left = mid + 1
		} else if nums[mid] > target {
			right = mid - 1
		} else {
			return true
		}
	}
	return false
}

// Z 字形查找
func (binary *SearchMatrixII) SearchMatrixZ(matrix [][]int, target int) bool {
	row, col := 0, len(matrix[0]) - 1
	for row < len(matrix) && col >= 0 {
		if matrix[row][col] > target {
			col--
		} else if matrix[row][col] < target {
			row++
		} else {
			return true
		}
	}
	return false
}