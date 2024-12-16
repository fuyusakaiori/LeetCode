package main

import (
	"fmt"
	"math/rand"
	"time"
)

type SortAlgorithm interface {
	Sort(nums []int)
}

type BubbleSort struct{}

type InsertSort struct{}

type SelectSort struct{}

type MergeSort struct{}

type QuickSort struct{}

type HeapSort struct{}

func (sort *BubbleSort) Sort(nums []int) {
	fmt.Printf("bubble sort: before sort %v\n", nums)
	for first := 0; first < len(nums); first++ {
		for second := 0; second < len(nums)-1-first; second++ {
			if nums[second] > nums[second+1] {
				swap(nums, second, second+1)
			}
		}
	}
	fmt.Printf("bubble sort: after sort %v\n", nums)
}

func (sort *InsertSort) Sort(nums []int) {
	fmt.Printf("insert sort: before sort %v\n", nums)
	for first := 0; first < len(nums); first++ {
		for second := first; second > 0; second-- {
			if nums[second-1] > nums[second] {
				swap(nums, second-1, second)
			}
		}
	}
	fmt.Printf("insert Sort: after sort %v\n", nums)
}

func (sort *SelectSort) Sort(nums []int) {
	fmt.Printf("select sort: before sort %v\n", nums)
	for first := 0; first < len(nums); first++ {
		minIndex := first
		for second := first; second < len(nums); second++ {
			if nums[minIndex] > nums[second] {
				minIndex = second
			}
		}
		swap(nums, first, minIndex)
	}
	fmt.Printf("select sort: after sort %v\n", nums)
}

func (sort *MergeSort) Sort(nums []int) {
	fmt.Printf("merge sort: before sort %v\n", nums)
	sort.fork(nums, 0, len(nums) - 1)
	fmt.Printf("merge sort: after sort %v\n", nums)
}

func (sort *MergeSort) fork(nums []int, left, right int) {
	if left >= right {
		return
	}
	mid := left + ((right - left) >> 1)
	sort.fork(nums, left, mid)
	sort.fork(nums, mid + 1, right)
	sort.merge(nums, left, mid, right)
}

func (sort *MergeSort) merge(nums []int, left, mid, right int) {
	index, leftIndex, rightIndex := 0, left, mid + 1
	helper := make([]int, right - left + 1)
	for leftIndex <= mid && rightIndex <= right {
		if nums[leftIndex] < nums[rightIndex] {
			helper[index] = nums[leftIndex]
			leftIndex++
		} else {
			helper[index] = nums[rightIndex]
			rightIndex++
		}
		index++
	}
	for leftIndex <= mid {
		helper[index] = nums[leftIndex]
		index++
		leftIndex++
	}
	for rightIndex <= right {
		helper[index] = nums[rightIndex]
		index++
		rightIndex++
	}
	for location := 0; location < len(helper); location++ {
		nums[left] = helper[location]
		left++
	}
}

func (sort *QuickSort) Sort(nums []int) {
	fmt.Printf("quick sort: before sort %v\n", nums)
	sort.fork(nums, 0, len(nums) - 1)
	fmt.Printf("quick sort: after sort %v\n", nums)
}

func (sort *QuickSort) fork(nums []int, left, right int)  {
	if left >= right {
		return
	}
	swap(nums, left + rand.Intn(right - left), right)
	leftBound, rightBound := sort.partition(nums, left, right, nums[right])
	sort.fork(nums, left, leftBound)
	sort.fork(nums, rightBound, right)
}

func (sort *QuickSort) partition(nums []int, left, right, target int) (int, int) {
	index, leftIndex, rightIndex := left, left, right
	for index < rightIndex + 1 {
		if nums[index] < target {
			swap(nums, index, leftIndex)
			index++
			leftIndex++
		} else if nums[index] > target {
			swap(nums, index, rightIndex)
			rightIndex--
		} else {
			index++
		}
	}
	return leftIndex - 1, rightIndex + 1
}

func (sort *HeapSort) Sort(nums []int)  {
	fmt.Printf("heap sort: before sort %v\n", nums)
	heapSize := 0
	for heapSize < len(nums) {
		sort.heapInsert(nums, heapSize)
		heapSize++
	}
	heapSize--
	swap(nums, 0, heapSize)
	for heapSize > 0 {
		sort.heapify(nums, 0, heapSize)
		heapSize--
		swap(nums, 0, heapSize)
	}
	fmt.Printf("heap sort: after sort %v\n", nums)
}

func (sort *HeapSort) heapInsert(nums []int, index int)  {
	parentIndex := (index - 1) / 2
	for nums[index] > nums[parentIndex] {
		swap(nums, index, parentIndex)
		index = parentIndex
		parentIndex = (index - 1) / 2
	}
}

func (sort *HeapSort) heapify(nums []int, parentIndex, heapSize int)  {
	leftIndex := (parentIndex << 1) + 1
	for leftIndex < heapSize {
		largestIndex := leftIndex
		if leftIndex + 1 < heapSize && nums[leftIndex] < nums[leftIndex + 1] {
			largestIndex = leftIndex + 1
		}
		if largestIndex == parentIndex {
			break
		}
		swap(nums, parentIndex, largestIndex)
		parentIndex = largestIndex
		leftIndex = (parentIndex << 1) + 1
	}
}

func swap(nums []int, first int, second int) {
	temp := nums[first]
	nums[first] = nums[second]
	nums[second] = temp
}

func generate() []int {
	nums := make([]int, 10)
	generator := rand.New(rand.NewSource(time.Now().UnixNano()))
	// 0. 随机生成数组
	for index := 0; index < len(nums); index++ {
		nums[index] = generator.Intn(10)
	}
	return nums
}

func main() {
	bubbleSort := &BubbleSort{}
	bubbleSort.Sort(generate())
	insertSort := &InsertSort{}
	insertSort.Sort(generate())
	selectSort := &SelectSort{}
	selectSort.Sort(generate())
	mergeSort := &MergeSort{}
	mergeSort.Sort(generate())
	quickSort := &QuickSort{}
	quickSort.Sort(generate())
	heapSort := &HeapSort{}
	heapSort.Sort(generate())
}
