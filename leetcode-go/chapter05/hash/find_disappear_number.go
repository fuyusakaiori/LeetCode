package hash

import "leetcode-go/util"

type FindDisappearNumber struct {}

// 原地哈希
func (hash *FindDisappearNumber) FindDisappearedNumbers(nums []int) []int {
	disappear := make([]int, 0)
	for index := 0; index < len(nums); index++ {
		current := util.Abs(nums[index]) - 1
		if nums[current] > 0 {
			nums[current] = -nums[current]
		}
	}
	for index := 0; index < len(nums); index++ {
		if nums[index] > 0 {
			disappear = append(disappear, index + 1)
		}
	}
	return disappear
}

// 置换
func (hash *FindDisappearNumber) FindDisappearedNumbersSwap(nums []int) []int  {
	disappear := make([]int, 0)
	for index := 0; index < len(nums); index++ {
		for nums[index] != nums[nums[index] - 1] {
			nums[index], nums[nums[index] - 1] = nums[nums[index] - 1], nums[index]
		}
	}
	for index := 0; index < len(nums); index++ {
		if nums[index] != index + 1 {
			disappear = append(disappear, index + 1)
		}
	}
	return disappear
}

