package pointer

type MoveZero struct {}

// 双指针
func (array *MoveZero) MoveZeroes(nums []int)  {
	current := 0
	for index := 0; index < len(nums); index++ {
		if nums[index] != 0 {
			nums[current], nums[index] = nums[index], nums[current]
			current++
		}
	}
}