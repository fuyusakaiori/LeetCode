package binary

// 寻找峰值
type FindPeekElement struct {}

// 二分查找
func (binary *FindPeekElement) FindPeekElement(nums []int) int {
	left, right := 0, len(nums) - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		if nums[mid] < nums[mid + 1] {
			left = mid + 1
		} else {
			right = mid
		}
	}
	return left
}