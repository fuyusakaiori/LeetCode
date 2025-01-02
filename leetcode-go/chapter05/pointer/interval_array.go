package pointer

import (
	"sort"
	"strconv"
)

// 合并区间
type MergeInterval struct {}

// 双指针
func (array *MergeInterval) Merge(intervals [][]int) [][]int {
	current := 0
	newIntervals := make([][]int, 0)
	sort.Slice(intervals, func(first, second int) bool {
		return intervals[first][0] < intervals[second][0]
	})
	for index := 0; index < len(intervals); index++ {
		if index == 0 || newIntervals[current][1] < intervals[index][0] {
			current++
			newIntervals = append(newIntervals, intervals[index])
		} else {
			newIntervals[current][1] = max(newIntervals[current][1], intervals[index][1])
		}
	}
	return newIntervals
}

// 插入区间
type InsertInterval struct {}

// 双指针
func (array *InsertInterval) Insert(intervals [][]int, interval []int) [][]int {
	index := 0
	newIntervals := make([][]int, 0)
	for ; index < len(intervals) && intervals[index][1] < interval[0]; index++ {
		newIntervals = append(newIntervals, intervals[index])
	}
	// NOTE: 注意判断条件
	for ; index < len(intervals) && intervals[index][0] <= interval[1]; index++ {
		interval[0] = min(interval[0], intervals[index][0])
		interval[1] = max(interval[1], intervals[index][1])
	}
	newIntervals = append(newIntervals, interval)
	for ; index < len(intervals); index++ {
		newIntervals = append(newIntervals, intervals[index])
	}
	return newIntervals
}

// 汇总区间
type SummaryInterval struct {}

// 双指针: 利用索引之差
func (array *SummaryInterval) SummaryRangesSelf(nums []int) []string {
	if len(nums) == 0 {
		return nil
	}
	start := 0
	intervals := make([]string, 0)
	for index := 0; index <= len(nums); index++ {
		if index == len(nums) || nums[index] - nums[start] != index - start {
			if index - 1 == start {
				intervals = append(intervals, strconv.Itoa(nums[start]))
			} else {
				intervals = append(intervals, strconv.Itoa(nums[start]) + "->" + strconv.Itoa(nums[index - 1]))
			}
			start = index
		}
	}
	return intervals
}

// 双指针: 官方实现
func (array *SummaryInterval) SummaryRanges(nums []int) []string {
	index := 0
	intervals := make([]string, 0)
	for index < len(nums) {
		left := index
		index++
		for index < len(nums) && nums[index - 1] + 1 == nums[index] {
			index++
		}
		right := index - 1
		if right > left {
			intervals = append(intervals, strconv.Itoa(nums[left]) + "->" + strconv.Itoa(nums[right]))
		} else {
			intervals = append(intervals, strconv.Itoa(nums[left]))
		}
	}
	return intervals
}

// 删除区间
type RemoveInterval struct {}

func (array *RemoveInterval) RemoveCoveredIntervals(intervals [][]int) int {
	count := 0
	interval := make([]int, 0)
	sort.Slice(intervals, func(first, second int) bool {
		return intervals[first][0] < intervals[second][0]
	})
	for index := 0; index < len(intervals); index++ {
		// NOTE: 不能省略判断条件
		if index == 0 || (interval[0] < intervals[index][0] && interval[1] < intervals[index][1]) {
			count++
			interval = intervals[index]
		} else {
			interval[1] = max(interval[1], intervals[index][1])
		}
	}
	return count
}