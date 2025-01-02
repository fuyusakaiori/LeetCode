package pointer

import (
	"leetcode-go/util"
	"math"
	"sort"
)

// 两数之和
type TwoNumberSumI struct {}

// 双指针 + 哈希表
func (array *TwoNumberSumI) TwoSum(nums []int, target int) []int {
	location := make(map[int]int)
	for index := 0; index < len(nums); index++ {
		if previous, ok := location[target - nums[index]]; ok {
			return []int{previous, index}
		}
		location[nums[index]] = index
	}
	return nil
}

// 两数之和 II
type TwoNumberSumII struct {}

// 双指针
func (array *TwoNumberSumII) TwoSum(nums []int, target int) []int {
	first, last := 0, len(nums) - 1
	for first <= last {
		if nums[first] + nums[last] < target {
			first++
		} else if nums[first] + nums[last] > target {
			last--
		} else {
			return []int{first + 1, last + 1}
		}
	}
	return nil
}

// 三数之和
type ThreeNumberSum struct {}

// 三指针: 固定第一个指针, 剩下两个指针转换为两数之和
func (array *ThreeNumberSum) TreeSum(nums []int) [][]int {
	tuples := make([][]int, 0)
	sort.Ints(nums)
	for first := 0; first < len(nums); first++ {
		// NOTE: 去重重复的三元组
		if first > 0 && nums[first - 1] == nums[first] {
			continue
		}
		third := len(nums) - 1
		target := -nums[first]
		for second := first + 1; second < len(nums); second++ {
			if second > first + 1 && nums[second - 1] == nums[second] {
				continue
			}
			for second < third && nums[second] + nums[third] > target {
				third--
			}
			if second == third {
				break
			}
			if nums[second] + nums[third] == target {
				tuples = append(tuples, []int{nums[first], nums[second], nums[third]})
			}
		}
	}
	return tuples
}

// 四数之和
type FourNumberSum struct {}

// 四指针: 类似三数之和
func (array *FourNumberSum) FourSum(nums []int, target int) [][]int {
	tuples := make([][]int, 0)
	sort.Ints(nums)
	for first := 0; first < len(nums); first++ {
		if first > 0 && nums[first - 1] == nums[first] {
			continue
		}
		for second := first + 1; second < len(nums); second++ {
			if second > first + 1 && nums[second - 1] == nums[second] {
				continue
			}
			fourth := len(nums) - 1
			newTarget := target - nums[first] - nums[second]
			for third := second + 1; third < len(nums); third++ {
				if third > second + 1 && nums[third - 1] == nums[third] {
					continue
				}
				for third < fourth && nums[third] + nums[fourth] > newTarget {
					fourth--
				}
				if third == fourth {
					break
				}
				if nums[third] + nums[fourth] == newTarget {
					tuples = append(tuples, []int{nums[first], nums[second], nums[third], nums[fourth]})
				}
			}
		}
	}
	return tuples
}

// 最接近的三数之和
type ThreeNumberSumCloset struct {}

// 三指针
func (array *ThreeNumberSumCloset) ThreeSumCloset(nums []int, target int) int {
	minSum := math.MaxInt
	sort.Ints(nums)
	for first := 0; first < len(nums); first++ {
		if first > 0 && nums[first - 1] == nums[first] {
			continue
		}
		second, third := first + 1, len(nums) - 1
		for second < third {
			sum := nums[first] + nums[second] + nums[third]
			if sum == target {
				return target
			}
			if util.Abs(sum - target) < util.Abs(minSum - target) {
				minSum = sum
			}
			if sum > target {
				index := third
				// NOTE: 去除重复值, 其实也可以不在这里去重
				for second < index && nums[index] == nums[third] {
					index--
				}
				third = index
			} else {
				index := second
				for index < third && nums[index] == nums[second] {
					index++
				}
				second = index
			}
		}
	}
	return minSum
}

// 有效的三角形个数
type TriangleNumber struct {}

// 三指针: 类似三数之和, 采用二分查找优化
func (array *TriangleNumber) TriangleNumberBinary(nums []int) int {
	count := 0
	sort.Ints(nums)
	for first := 0; first < len(nums); first++ {
		for second := first + 1; second < len(nums); second++ {
			index := second
			left, right := second + 1, len(nums) - 1
			for left <= right {
				mid := left + ((right - left) >> 1)
				if nums[first] + nums[second] <= nums[mid] {
					right = mid - 1
				} else {
					index = mid
					left = mid + 1
				}
			}
			count += index - second
		}
	}
	return count
}

// 三指针: 类似三数之和, 从右向左, 优先选择两条最大的边
func (array *TriangleNumber) TriangleNumberMax(nums []int) int {
	count := 0
	sort.Ints(nums)
	for first := len(nums) - 1; first >= 0; first-- {
		third := 0
		for second := first - 1; second >= 0; second-- {
			for third < second && nums[third] + nums[second] < nums[first] {
				third++
			}
			if third == second {
				break
			}
			if nums[third] + nums[second] > nums[first] {
				count += second - third
			}
		}
	}
	return count
}

// 三指针: 类似三数之和, 从左向右, 优先选择两条最小的边
func (array *TriangleNumber) TriangleNumberMin(nums []int) int {
	count := 0;
	sort.Ints(nums)
	for first := 0; first < len(nums); first++ {
		second := first + 1
		third := second + 1
		for second < len(nums) {
			for third < len(nums) && nums[first] + nums[second] > nums[third] {
				third++
			}
			count += max(third - second - 1, 0)
		}
	}
	return count
}