package chapter05.binary;

/**
 * <p>寻找山峰</p>
 */
public class FindPeekElement {

    /**
     * <p>遍历: 时间复杂度 O(n), 寻找最大值, 不需要使用单调栈</p>
     */
    public static int findPeekElementMax(int[] nums) {
        int maxIndex = 0;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] > nums[maxIndex]) {
                maxIndex = index;
            }
        }
        return maxIndex;
    }

    /**
     * <p>二分查找: 时间复杂度 O(logn)</p>
     * <p>1. 最左侧和最右侧默认都是负无穷: </p>
     * <p>如果出现单调递增, 那么峰值一定在最右侧</p>
     * <p>如果出现单调递减, 那么峰值一定在最左侧</p>
     * <p>2. 如果中间值小于右侧值, 那么就证明可能是单调递增的, 向右侧移动指针</p>
     * <p>3. 如果中间值大于右侧值, 那么就证明可能是单调递减的, 向左侧移动指针</p>
     * <p>4. 虽然左侧和右侧都可能存在峰值, 但是只要找到其中一个就可以, 所以可以这样移动指针</p>
     */
    public static int findPeekElementBinarySearch(int[] nums) {
        int left = 0, right = nums.length - 1;
        // NOTE: 开区间, 否则无法终止
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                // NOTE: 如果中间值大于右侧值, 那么是不能证明中间值就不是峰值, 所以不能排除掉
                right = mid;
            }
        }
        return left;
    }

}
