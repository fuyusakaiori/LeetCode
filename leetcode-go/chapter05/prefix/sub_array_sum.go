package prefix

// 和为 K 的子数组
type SubArraySum struct {}

// 前缀和
func (prefix *SubArraySum) SubArraySum(nums []int, target int) int {
	count := 0
	prefixSum := 0
	prefixSums := map[int]int{0:1}
	for index := 0; index < len(nums); index++ {
		prefixSum += nums[index]
		if value, ok := prefixSums[prefixSum - target]; ok {
			count += value
		}
		prefixSums[prefixSum]++
	}
	return count
}