package chapter05.binary;

/**
 * <p>寻找重复数</p>
 */
public class FindDuplicate {

    /**
     * <p>二分查找: 时间复杂度 O(nlogn)</p>
     * <p>1. 二分查找只能对有序或者部分有序的数组使用, 这里的数组是完全无序的, 那么就需要想办法构造有序数组</p>
     * <p>2. 最直接的想法就是排序: 如果不需要返回索引, 那么在不考虑时间复杂度的情况下确实可以排序</p>
     * <p>3. 其次还可以考虑利用元素的数量有序: 建议直接看官方题解</p>
     */
    public static int findDuplicateBinarySearch(int[] nums) {
        int duplicate = 0;
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            // NOTE: 因为元素大小和索引大小存在关联, 所以会对索引进行二分, 将索引当成元素
            int mid = left + ((right - left) >> 1);
            // NOTE: 统计小于等于自己的元素数量, 如果小于自己的元素都没有重复, 那么元素的数量一定等于自己的大小
            int count = getLessEqualCount(nums, mid);
            if (count > mid) {
                // NOTE: 每个中点都有可能是重复的数字
                duplicate = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return duplicate;
    }

    public static int getLessEqualCount(int[] num, int target) {
        int count = 0;
        for (int index = 0; index < num.length; index++) {
            if (num[index] <= target) {
                count++;
            }
        }
        return count;
    }

    /**
     * <p>快慢指针: 类似环形链表, 时间复杂度 O(n)</p>
     */
    public static int findDuplicateCircle(int[] nums) {
        int slow = 0, fast = 0;
        do {
            // NOTE: 因为不会越界, 所以可以不用判断
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        fast = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

}
