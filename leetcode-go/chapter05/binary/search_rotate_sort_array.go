package binary

import "math"

// 搜索旋转排序数组
type SearchRotateSortArrayI struct {}

// 二分查找
func (binary *SearchRotateSortArrayI) Search(nums []int, target int) int {
	left, right := 0, len(nums) - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		if nums[mid] == target {
			return mid
		}
		if nums[left] <= nums[mid] {
			if nums[left] <= target && target <= nums[mid] {
				right = mid - 1
			} else {
				left = mid + 1
			}
		} else {
			if nums[mid] <= target && target <= nums[right] {
				left = mid + 1
			} else {
				right = mid - 1
			}
		}
	}
	return -1
}

// 搜索旋转排序数组 II
type SearchRotateSortArrayII struct {}

// 二分查找
func (binary *SearchRotateSortArrayII) Search(nums []int, target int) bool {
	left, right := 0, len(nums) - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		if nums[mid] == target {
			return true
		}
		if nums[left] == nums[mid] && nums[mid] == nums[right] {
			left++
			right--
		} else if nums[left] <= nums[mid] {
			if nums[left] <= target && target <= nums[mid] {
				right = mid - 1
			} else {
				left = mid + 1
			}
 		} else {
			 if nums[mid] <= target && target <= nums[right] {
				 left = mid + 1
			 } else {
				 right = mid - 1
			 }
		}
	}
	return false
}

// 寻找旋转排序数组中的最小值
type SearchRotateSortArrayMinI struct {}

// 二分查找
func (binary *SearchRotateSortArrayMinI) FindMin(nums []int) int {
	answer := math.MaxInt
	left, right := 0, len(nums) - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		answer = min(answer, nums[mid])
		if nums[mid] < nums[right] {
			right = mid - 1
		} else {
			left = mid + 1
		}
	}
	return answer
}

// 寻找旋转排序数组中的最小值
type SearchRotateSortArrayMinII struct {}

func (binary *SearchRotateSortArrayMinII) FindMin(nums []int) int {
	answer := math.MaxInt
	left, right := 0, len(nums) - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		answer = min(answer, nums[mid])
		if nums[mid] < nums[right] {
			right = mid - 1
		} else if nums[mid] > nums[right] {
			left = mid + 1
		} else {
			right--
		}
	}
	return answer
}