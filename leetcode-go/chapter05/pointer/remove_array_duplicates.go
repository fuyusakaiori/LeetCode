package pointer

// 删除有序数组中的重复项
type RemoveDuplicatesI struct {}

// 双指针
func (array *RemoveDuplicatesI) RemoveDuplicates(nums []int) int {
	current := 0
	for index := 0; index < len(nums); index++ {
		if index == 0 || nums[index] != nums[index - 1] {
			nums[current] = nums[index]
			current++
		}
	}
	return current
}

// 删除有序数组中的重复项 II
type RemoveDuplicatesII struct {}

// 双指针
func (array *RemoveDuplicatesII) RemoveDuplicates(nums []int) int {
	current := 0
	frequency := 0
	for index := 0; index < len(nums); index++ {
		if index == 0 || frequency < 2 || nums[index - 1] != nums[index] {
			if index > 0 && nums[index - 1] != nums[index] {
				frequency = 0
			}
			nums[current] = nums[index]
			current++
		}
		frequency++
	}
	return current
}

// 双指针: 通解
func (array *RemoveDuplicatesII) RemoveDuplicatesGeneral(nums []int, k int) int {
	current := 0
	for index := 0; index < len(nums); index++ {
		// NOTE: 前 k 个元素可以直接保留
		// NOTE: 对于后面的任意数字, 能够保留的前提是和前面的第 k 个元素比较, 不相同则保留
		if index < k || nums[current - k] != nums[index] {
			nums[current] = nums[index]
			current++
		}
	}
	return current
}

// 移除元素
type RemoveElement struct {}

// 双指针: 双指针合计至多遍历两次
func (array *RemoveElement) RemoveElement(nums []int ,value int) int {
	current := 0
	for index := 0; index < len(nums); index++ {
		if nums[index] != value {
			nums[current] = nums[index]
			current++
		}
	}
	return current
}

// 双指针: 优化, 双指针合计至多遍历一次
func (array *RemoveElement) RemoveElementOptimize(nums []int, value int) int {
	left, right := 0, len(nums) - 1
	for left <= right {
		if nums[left] == value {
			nums[left] = nums[right]
			right--
		} else {
			left++
		}
	}
	return left
}