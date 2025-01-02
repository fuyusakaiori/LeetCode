package pointer

import "sort"

// 两个数组的交集
type ArrayIntersectI struct {}

// 哈希表
func (array *ArrayIntersectI) intersectionHash(nums1, nums2 []int) []int {
	intersection := make([]int, 0)
	set := make(map[int]struct{})
	for index := 0; index < len(nums1); index++ {
		set[nums1[index]] = struct{}{}
	}
	for index := 0; index < len(nums2); index++ {
		if _, ok := set[nums2[index]]; ok {
			intersection = append(intersection, nums2[index])
			delete(set, nums2[index])
		}
	}
	return intersection
}

// 双指针
func (array *ArrayIntersectI) intersection(nums1, nums2 []int) []int {
	sort.Ints(nums1)
	sort.Ints(nums2)
	first, second := 0, 0
	intersection := make([]int, 0)
	for first < len(nums1) && second < len(nums2) {
		if nums1[first] < nums2[second] {
			first++
		} else if nums1[first] > nums2[second] {
			second++
		} else {
			if first == 0 || nums1[first] != nums1[first - 1] {
				intersection = append(intersection, nums1[first])
			}
			first++
			second++
		}
	}
	return intersection
}

// 两个数组的交集 II
type ArrayIntersectII struct {}

// 哈希表
func (array *ArrayIntersectII) intersectionHash(nums1, nums2 []int) []int {
	intersection := make([]int, 0)
	count := make(map[int]int)
	for index := 0; index < len(nums1); index++ {
		count[nums1[index]]++
	}
	for index := 0; index < len(nums2); index++ {
		if count[nums2[index]] > 0 {
			intersection = append(intersection, nums2[index])
			count[nums2[index]]--
		}
	}
	return intersection
}

// 双指针
func (array *ArrayIntersectII) intersection(nums1, nums2 []int) []int {
	sort.Ints(nums1)
	sort.Ints(nums2)
	first, second := 0, 0
	intersection := make([]int, 0)
	for first < len(nums1) && second < len(nums2) {
		if nums1[first] < nums2[second] {
			first++
		} else if nums1[first] > nums2[second] {
			second++
		} else {
			intersection = append(intersection, nums1[first])
			first++
			second++
		}
	}
	return intersection
}