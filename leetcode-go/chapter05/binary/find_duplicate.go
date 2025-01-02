package binary

type FindDuplicate struct {}

// 二分查找
func (binary *FindDuplicate) FindDuplicate(nums []int) int {
	duplicate := 0
	left, right := 0, len(nums) - 1
	for left <= right {
		mid := left + ((right - left) >> 1)
		count := binary.GetLessEqualCount(nums, mid)
		if count > mid {
			// NOTE: 只有大于和等于两种情况, 只能在大于的时候记录结果, 等于的时候肯定不是重复的
			duplicate = mid
			right = mid - 1
		} else {
			left = mid + 1
		}
	}
	return duplicate
}

func (binary *FindDuplicate) GetLessEqualCount(nums []int, target int) int {
	count := 0
	for _, num := range nums {
		if num <= target {
			count++
		}
	}
	return count
}

// 快慢指针
func (binary *FindDuplicate) FindDuplicateFastSlow(nums []int) int {
	slow, fast := 0, 0
	for ok := true; ok; ok = slow != fast {
		slow = nums[slow]
		fast = nums[nums[fast]]
	}
	fast = 0
	for slow != fast {
		slow = nums[slow]
		fast = nums[fast]
	}
	return slow
}