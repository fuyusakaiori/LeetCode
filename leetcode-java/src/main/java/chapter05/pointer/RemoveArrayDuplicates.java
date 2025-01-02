package chapter05.pointer;


/**
 * <p>删除数组中的重复元素</p>
 * <p>1. 删除数组中的重复元素</p>
 * <p>2. 删除数组中的重复元素 II</p>
 * <p>3. 移除元素</p>
 */
public class RemoveArrayDuplicates {

    /**
     * <p>删除有序数组中的重复项</p>
     */
    public static class RemoveDuplicatesI {

        /**
         * <p>双指针</p>
         */
        public static int removeDuplicates(int[] nums) {
            int current = 0;
            for (int index = 0; index < nums.length; index++) {
                if (index == 0 || nums[index] != nums[index - 1]) {
                    nums[current++] = nums[index];
                }
            }
            return current;
        }

    }

    /**
     * <p>删除有序数组中的重复项 II</p>
     */
    public static class RemoveDuplicatesII {

        /**
         * <p>双指针: 自行实现</p>
         */
        public static int removeDuplicates(int[] nums) {
            int current = 0;
            int frequency = 1;
            for (int index = 0; index < nums.length; index++) {
                if (index == 0 || frequency < 2 || nums[index] != nums[index - 1]) {
                    if (index > 0 && nums[index] != nums[index - 1]) {
                        frequency = 0;
                    }
                    nums[current++] = nums[index];
                }
                frequency++;
            }
            return current;
        }

        /**
         * <p>思路: 删除数组中的重复元素 II</p>
         */
        private static int removeDuplicatesGeneral(int[] nums, int k){
            int cur = 0;
            for (int index = 0;index < nums.length;index++){
                // 注: 因为每个元素仅允许出现 k 次, 所以只需要将比较大小为 k 的区间的左右边界是否相等
                if (cur < k || nums[cur - k] != nums[index])
                    // 注: 如果左右边界相等, 那么就证明这个区间已经有超过 k 个相同元素, 所以就不要放进数组中了
                    nums[cur++] = nums[index];
            }
            return cur;
        }

    }

    /**
     * <p>移除元素</p>
     */
    public static class RemoveElement {

        /**
         * <p>双指针</p>
         */
        public static int removeElement(int[] nums, int value) {
            int current = 0;
            for (int index = 0; index < nums.length; index++) {
                if (nums[index] != value) {
                    nums[current++] = nums[index];
                }
            }
            return current;
        }

        /**
         * <p>双指针: 优化</p>
         */
        public static int removeElementOptimize(int[] nums, int value) {
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                if (nums[left] == value) {
                    nums[left] = nums[right];
                    right--;
                } else {
                    left++;
                }
            }
            return left;
        }

    }
}
