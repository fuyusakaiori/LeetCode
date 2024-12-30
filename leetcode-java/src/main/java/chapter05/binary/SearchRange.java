package chapter05.binary;

/**
 * <p>在排序数组中查找元素的第一个值和最后一个值</p>
 */
public class SearchRange {

    /**
     * <p>二分查找: 找到第一个大于等于目标值的元素, 和第一个大于目标值的元素</p>
     * <p>1. 找到相等的元素后, 将指针向左移动, 那么就可以找到第一个大于等于目标值的元素</p>
     * <p>2. 找到相等的元素后, 将指针向右移动, 那么就可以找到第一个大于目标值的元素</p>
     */
    public static int[] searchRange(int[] nums, int target) {
        // NOTE: 找第一个大于等于目标值的元素
        int first = binarySearch(nums, target, true);
        int second = binarySearch(nums, target, false);
        return first <= second ? new int[]{first, second}: new int[]{-1, -1};
    }

    public static int binarySearch(int[] nums, int target, boolean flag) {
        int index = nums.length;
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            // NOTE: 如果大于等于, 那么指针向左移动找最小值
            // NOTE: 如果严格大于, 那么指针向右移动找最大值
            if (nums[mid] > target || (flag && nums[mid] >= target)) {
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

}
