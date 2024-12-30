package chapter05.binary;

/**
 * <h2>搜索旋转排序数组系列问题</h2>
 * <h3>1. 搜索旋转排序数组 </h3>
 * <h3>2. 搜索旋转排序数组 II</h3>
 * <h3>3. 寻找旋转排序数组中最小值</h3>
 * <h3>4. 寻找旋转排序数组中最小值 II</h3>
 */
public class SearchRotateSortArray {

    /**
     * <p>搜索旋转排序数组</p>
     */
    public static class SearchRotateSortArrayI {

        /**
         * <p>二分查找: 因为旋转后的数组是部分有序的, 所以优先在部分有序的数组中查询, 然后再考虑无序的数组</p>
         */
        public static int search(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (nums[mid] == target) {
                    return mid;
                }
                // NOTE: 如果左侧数组有序, 那么优先在左侧数组判断和目标值的关系
                if (nums[left] <= nums[mid]) {
                    // NOTE: 判断目标值是否在有序数组的范围内, 这里的左边界是不能少的
                    if (nums[left] <= target && target <= nums[mid]) {
                        // NOTE: 如果就在有序数组内, 那么按照二分查找的正常思路判断即可
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                } else {
                    if (nums[mid] <= target && target <= nums[right]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
            return -1;
        }

    }

    /**
     * <p>搜索旋转排序数组 II</p>
     */
    public static class SearchRotateSortArrayII {

        /**
         * <p>二分查找: 和搜索旋转排序数组完全一致, 重复的元素其实可以不用管</p>
         * <p>注意: 如果所有元素都相等, 那么就会退化为线性遍历, 时间复杂度为 O(n)</p>
         */
        public static int search(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (nums[mid] == target) {
                    return mid;
                }
                // NOTE: 如果左右两侧边界都相同, 那么可以将两侧的边界去掉, 不过不去掉貌似也没问题
                if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                    left++;
                    right--;
                    // NOTE: 如果左侧数组有序, 那么优先在左侧数组判断和目标值的关系
                } else if (nums[left] <= nums[mid]) {
                    // NOTE: 判断目标值是否在有序数组的范围内, 这里的左边界是不能少的
                    if (nums[left] <= target && target <= nums[mid]) {
                        // NOTE: 如果就在有序数组内, 那么按照二分查找的正常思路判断即可
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                } else {
                    if (nums[mid] <= target && target <= nums[right]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
            return -1;
        }

    }

    /**
     * <p>搜索旋转排序数组中最小值</p>
     */
    public static class SearchRotateSortArrayMinI {

        /**
         * <p>二分查找</p>
         * <p>1. 选择最后一个元素作为基准</p>
         * <p>如果比基准值大, 那么就肯定在最小值的左侧, 那么应该右移</p>
         * <p>如果比基准值小, 那么就肯定在最小值右侧, 那么应该左移</p>
         * <p>2. 选择第一个元素作为基准是有问题的</p>
         * <p>如果比基准值小, 那么肯定是在最小值右侧, 那么应该左移</p>
         * <p>如果比基准值大, 那么是无法保证一定在最小值左侧的, 也可能在右侧, 比如数组完全升序的情况</p>
         * <p>不推荐使用第二种方式, 会有额外的判断条件, 第一种方式更简单</p>
         */
        public static int findMin(int[] nums) {
            int min = Integer.MAX_VALUE;
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                min = Math.min(min, nums[mid]);
                if (nums[mid] < nums[nums.length - 1]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return min;
        }

        public static int findMinSelf(int[] numbers){
            int min = Integer.MAX_VALUE;
            int left = 0, right = numbers.length - 1;
            while (left <= right){
                int mid = (left + right) >> 1;
                // 注: 这里必须保证中间值比右边界大, 才能确保左侧有序, 右侧无序, 否则可能出现左右两侧都有序的情况
                // 注: 这里最好不要去加右侧的等号, 这是因为如果左侧有序, 右侧相等, 那么这个时候应该去左侧找而不应该去右侧找, 加了等号就会右侧找
                if (numbers[left] <= numbers[mid] && numbers[mid] > numbers[right]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
                min = Math.min(min, numbers[mid]);
            }

            return min;
        }

    }

    /**
     * <p>搜索旋转排序数组中最小值 II</p>
     */
    public static class SearchRotateSortArrayMinII {

        /**
         * <p>二分查找: 和搜索旋转排序数组中最小值完全一致, 重复的元素其实也是可以不用管的</p>
         */
        public static int findMin(int[] nums) {
            int min = Integer.MAX_VALUE;
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                min = Math.min(min, nums[mid]);
                if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                    left++;
                    right--;
                } else if (nums[mid] < nums[nums.length - 1]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return min;
        }

        public static int findMinSelf(int[] numbers){
            int min = Integer.MAX_VALUE;
            int left = 0, right = numbers.length - 1;
            while (left <= right){
                int mid = (left + right) >> 1;
                if(numbers[left] == numbers[mid] && numbers[mid] == numbers[right]){
                    left++;
                    right--;
                }else if (numbers[left] <= numbers[mid] && numbers[mid] > numbers[right]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
                min = Math.min(min, numbers[mid]);
            }
            return min;
        }

    }


}
