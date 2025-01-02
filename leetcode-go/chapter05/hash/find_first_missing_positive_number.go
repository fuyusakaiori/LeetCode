package hash

import "leetcode-go/util"

type FindFirstMissingPositiveNumber struct {}

// 原地哈希
func (hash *FindFirstMissingPositiveNumber) FirstMissingPositive(nums []int) int {
	for index := 0; index < len(nums); index++ {
		if nums[index] <= 0 {
			nums[index] = len(nums) + 1
		}
	}
	for index := 0; index < len(nums); index++ {
		current := util.Abs(nums[index]) - 1
		if current < len(nums) && nums[current] > 0 {
			nums[current] = -nums[current]
		}
	}
	for index := 0; index < len(nums); index++ {
		if nums[index] > 0 {
			return index + 1
		}
	}
	return len(nums) + 1
}

// 置换
func (hash *FindFirstMissingPositiveNumber) FirstMissingPositiveSwap(nums []int) int {
	for index := 0; index < len(nums); index++ {
		if nums[index] <= 0 {
			nums[index] = len(nums) + 1
		}
	}
	for index := 0; index < len(nums); index++ {
		for nums[index] < len(nums) && nums[index] != nums[nums[index] - 1] {
			nums[index], nums[nums[index] - 1] = nums[nums[index] - 1], nums[index]
		}
	}
	for index := 0; index < len(nums); index++ {
		if nums[index] != index + 1 {
			return index + 1
		}
	}
	return len(nums) + 1
}