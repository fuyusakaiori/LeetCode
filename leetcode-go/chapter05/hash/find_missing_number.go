package hash

type MissingNumber struct {}

// 原地哈希
func (hash *MissingNumber) MissingNumber(nums []int) int {
	length := len(nums) + 1
	for index := 0; index < len(nums); index++ {
		current := nums[index] % length
		if current < len(nums) {
			nums[current] = nums[current] + length
		}
	}
	for index := 0; index < len(nums); index++ {
		if nums[index] < length {
			return index
		}
	}
	return len(nums)
}

// 置换
func (hash *MissingNumber) MissingNumberSwap(nums []int) int {
	for index := 0; index < len(nums); index++ {
		// NOTE: 第二个条件是用来判断是否将对应的元素调整到当前的位置上
		for nums[index] < len(nums) && nums[index] != nums[nums[index]] {
			nums[index], nums[nums[index]] = nums[nums[index]], nums[index]
		}
	}
	for index := 0; index < len(nums); index++ {
		if nums[index] != index {
			return index
		}
	}
	return len(nums)
}