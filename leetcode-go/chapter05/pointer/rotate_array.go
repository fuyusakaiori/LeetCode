package pointer

// 轮转数组
type RotateArray struct {}

// 额外数组
func (array *RotateArray) RotateSimple(nums []int, k int)  {
	offset := k % len(nums)
	rotate := make([]int, len(nums))
	for index := 0; index < len(nums); index++ {
		rotate[(index + offset) % len(nums)] = nums[index]
	}
	copy(nums, rotate)
}

// 环状数组
func (array *RotateArray) RotateCycle(nums []int, k int)  {
	count := 0
	offset := k % len(nums)
	for start := 0; count < len(nums); start++ {
		current := start
		previous := nums[current]
		for ok := true; ok; ok = start != current {
			next := (current + offset) % len(nums)
			nums[next], previous, current = previous, nums[next], next
			count++
		}
	}
}

// 翻转数组
func (array *RotateArray) RotateReverse(nums []int, k int)  {
	k = k % len(nums)
	// 第一次翻转: 完全翻转数组
	array.Reverse(nums, 0, len(nums) - 1)
	// 第二次翻转: 翻转后半数组
	array.Reverse(nums, k, len(nums) - 1)
	// 第三次翻转: 翻转前半数组
	array.Reverse(nums, 0, k - 1)
}

func (array *RotateArray) Reverse(nums []int, first, last int)  {
	for first < last {
		nums[first], nums[last] = nums[last], nums[first]
		first++
		last--
	}
}