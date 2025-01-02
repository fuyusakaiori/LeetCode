package prefix

// 除自身以外数组的乘积
type ProductExceptSelf struct {}

// 前缀和: 空间复杂度 O(n)
func (prefix *ProductExceptSelf) ProductExceptSelfSimple(nums []int) []int {
	production := make([]int, len(nums))
	prefixSum := make([]int, len(nums) + 1)
	suffixSum := make([]int, len(nums) + 1)
	for index := 0; index < len(nums) + 1; index++ {
		prefixSum[index] = 1
		suffixSum[index] = 1
	}
	for index := 0; index < len(nums); index++ {
		prefixSum[index + 1] = prefixSum[index] * nums[index]
		suffixSum[index + 1] = suffixSum[index] * nums[len(nums) - index - 1]
	}
	for index := 0; index < len(nums); index++ {
		production[index] = prefixSum[index] * suffixSum[len(nums) - index - 1]
	}
	return production
}

// 前缀和: 空间复杂度 O(1)
func (prefix *ProductExceptSelf) ProductExceptSelf(nums []int) []int {
	production := make([]int, len(nums))
	for index := range production {
		production[index] = 1
	}
	left := 1
	for index := 0; index < len(nums); index++ {
		production[index] *= left
		left *= nums[index]
	}
	right := 1
	for index := len(nums) - 1; index >= 0; index-- {
		production[index] *= right
		right *= nums[index]
	}
	return production
}