package pointer

// 合并两个有序数组
type MergeArray struct {}

// 双指针: 空间复杂度 O(n), 借助额外数组
func (array *MergeArray) MergeSimple(firstNums, secondNums []int, firstLength, secondLength int)  {
	first, second := 0, 0
	nums := make([]int, 0, firstLength + secondLength)
	for {
		if first == firstLength {
			nums = append(nums, secondNums[second:]...)
			break
		}
		if second == secondLength {
			nums = append(nums, firstNums[first:]...)
			break
		}
 		if firstNums[first] < secondNums[second] {
			nums = append(nums, firstNums[first])
			first++
		} else {
			nums = append(nums, secondNums[second])
			second++
		}
	}
	copy(firstNums, nums)
}

// 双指针: 空间复杂度 O(1), 不借助额外数组
func (array *MergeArray) Merge(firstNums, secondNums []int, firstLength, secondLength int)  {
	current := firstLength + secondLength - 1
	first, second := firstLength, secondLength
	for first >= 0 && second >= 0 {
		if firstNums[first] > secondNums[second] {
			firstNums[current] = firstNums[first]
			first--
			current--
		} else {
			firstNums[current] = secondNums[second]
			second--
			current--
		}
	}
	if second >= 0 {
		for ; second >= 0; second-- {
			firstNums[current] = secondNums[second]
			current--;
		}
	}
}