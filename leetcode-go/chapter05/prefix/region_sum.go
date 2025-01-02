package prefix

// 区域和检索-数组不可变
type NumArray struct {
	prefixSum []int
}

// 前缀和
func ConstructorArray(nums []int) NumArray {
	array := NumArray{prefixSum: make([]int, len(nums) + 1)}
	for index := 0; index < len(nums); index++ {
		array.prefixSum[index + 1] += array.prefixSum[index] + nums[index]
	}
	return array
}

func (prefix *NumArray) SumRange(left int, right int) int {
	return prefix.prefixSum[right + 1] - prefix.prefixSum[left]
}

// 二维区域和检索-矩阵不可变
type NumMatrix struct {
	prefixSum [][]int
}

func ConstructorMatrix(nums [][]int) NumMatrix {
	matrix := NumMatrix{prefixSum: make([][]int, len(nums))}
	for index := range matrix.prefixSum {
		matrix.prefixSum[index] = make([]int, len(nums[0]) + 1)
	}
	for row := 0; row < len(nums); row++ {
		for col := 0; col < len(nums[0]); col++ {
			matrix.prefixSum[row][col + 1] = matrix.prefixSum[row][col] + nums[row][col]
		}
	}
	return matrix
}


func (prefix *NumMatrix) SumRegion(row1 int, col1 int, row2 int, col2 int) int {
	sum := 0
	for row := row1; row <= row2; row++ {
		sum += prefix.prefixSum[row][col2 + 1] - prefix.prefixSum[row][col1]
	}
	return sum
}