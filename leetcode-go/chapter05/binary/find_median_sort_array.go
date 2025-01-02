package binary

// 寻找两个正序数组的中位数
type FindMedianSortArray struct {}

func (binary *FindMedianSortArray) FindMedianSortedArray(nums1, nums2 []int) float64 {
	first, second := 0, 0
	left, right := float64(0), float64(0)
	length := len(nums1) + len(nums2)
	for index := 0; index < length / 2 + 1; index++ {
		left = right
		if first < len(nums1) && (second >= len(nums2) || nums1[first] < nums2[second]) {
			right = float64(nums1[first])
			first++
		} else {
			right = float64(nums2[second])
			second++
		}
	}
	if length % 2 == 0 {
		return (left + right) / 2
	}
	// NOTE: 返回的是右侧的值, 这个才是中间值
	return right
}