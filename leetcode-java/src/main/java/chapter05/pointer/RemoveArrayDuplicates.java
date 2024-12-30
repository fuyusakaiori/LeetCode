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
            int count = 0;
            int current = 0;
            for (int index = 0; index < nums.length; index++) {
                if (index == 0 || nums[index] != nums[index - 1]) {
                    count++;
                    nums[current++] = nums[index];
                }
            }
            return count;
        }

    }

    /**
     * <p>删除有序数组中的重复项 II</p>
     */
    public static class RemoveDuplicatesII {

        /**
         * <p>双指针: 自行实现</p>
         */
        public static int removeDuplicatesSelf(int[] nums) {
            int count = 0;
            int current = 0;
            int frequency = 1;
            for (int index = 0; index < nums.length; index++) {
                if (index == 0 || frequency < 2 || nums[index] != nums[index - 1]) {
                    if (index > 0 && nums[index] != nums[index - 1]) {
                        frequency = 0;
                    }
                    count++;
                    nums[current++] = nums[index];
                }
                frequency++;
            }
            return count;
        }

        /**
         * <p>思路: 删除数组中的重复元素 II</p>
         * <p>注: 这个解法其实有点不好想, 也不是那么好理解, 建议多看几次</p>
         */
        private static int removeDuplicates(int[] nums, int k){
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
        public int removeElement(int[] nums, int value) {
            int current = 0;
            for (int index = 0; index < nums.length; index++) {
                if (nums[index] != value) {
                    nums[current++] = nums[index];
                }
            }
            return current;
        }

    }

    /**
     * <p>1. 第二种解法可以采用快排分区的思想</p>
     * <p>2. 将大于目标次数的放在左边, 将小于目标次数的放在右边</p>
     */
    private static int removeDuplicates(int k, int[] nums){
        int count = 1;
        int leftIndex = 0, currentIndex = 1;
        int previous = nums[0];
        while (currentIndex < nums.length){
            int current = nums[currentIndex];
            if (current != previous){
                nums[++leftIndex] = nums[currentIndex++];
                previous = current;
                count = 1;
            }else if (count < 2){
                nums[++leftIndex] = nums[currentIndex++];
                count++;
            }else{
                currentIndex++;
            }
        }

        return leftIndex + 1;
    }
}
