package hash

import "leetcode-go/util"

type FindDuplicateNumber struct {}

// 原地哈希
func (hash *FindDuplicateNumber) FindDuplicates(nums []int) []int {
	disappear := make([]int, 0)
	for index := 0; index < len(nums); index++ {
		current := util.Abs(nums[index]) - 1
		if nums[current] > 0 {
			nums[current] = -nums[current]
		} else {
			disappear = append(disappear, current + 1)
		}
	}
	return disappear
}

// 置换
func (hash *FindDuplicateNumber) FindDuplicatesSwap(nums []int) []int {
	disappear := make([]int, 0)
	for index := 0; index < len(nums); index++ {
		for nums[index] != nums[nums[index] - 1] {
			nums[index], nums[nums[index] - 1] = nums[nums[index] - 1], nums[index]
		}
 	}
	for index := 0; index < len(nums); index++ {
		if nums[index] != index + 1 {
			disappear = append(disappear, nums[index])
		}
	}
	return disappear
}