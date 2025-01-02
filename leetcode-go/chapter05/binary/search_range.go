package binary

// 在排序数组中查找元素的第一个和最后一个位置
type SearchRange struct {}

// 二分查找
func (binary *SearchRange) SearchRange(nums []int, target int) []int {
	first := binary.BinarySearch(nums, target, true)
	if first == -1 {
		return []int{-1, -1}
	}
	second := binary.BinarySearch(nums, target, false) - 1
	if first > second {
		return []int{-1, -1}
	}
	return []int{first, second}
}

func (binary *SearchRange) BinarySearch(nums []int, target int, flag bool) int {
	index := len(nums)
	left, right := 0, len(nums) - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		if nums[mid] > target || (flag && nums[mid] >= target) {
			index = mid
			right = mid - 1
		} else {
			left = mid + 1
		}
	}
	return index
}