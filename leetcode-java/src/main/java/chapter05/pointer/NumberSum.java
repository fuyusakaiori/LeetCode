package chapter05.pointer;

import java.util.*;

/**
 * <p>数字之和系列问题</p>
 * <p>1. 两数之和</p>
 * <p>2. 两数之和 II</p>
 * <p>3. 三数之和</p>
 * <p>4. 四数之和</p>
 * <p>5. 最接近的三数之和</p>
 * <p>6. 有效三角形个数</p>
 */
public class NumberSum {

    /**
     * 两数之和
     * <p>1. 暴力解</p>
     * <p>2. 哈希表</p>
     * <p>注: 无序数组不能转换为有序数组处理, 因为排序之后索引就发生变化了</p>
     */
    public static class TwoSumI {

        public static int[] twoSum(int[] numbers, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int index = 0; index < numbers.length; index++) {
                if (map.containsKey(target - numbers[index])) {
                    return new int[]{index, map.get(target - numbers[index])};
                }
                map.put(numbers[index], index);
            }
            return new int[]{0, 0};
        }

    }

    /**
     * 两数之和 II
     */
    public static class TwoSumII {

        public static int[] twoSum(int[] numbers, int target) {
            int first = 0, last = numbers.length - 1;
            while (first <= last) {
                if (numbers[first] + numbers[last] > target) {
                    last--;
                } else if (numbers[first] + numbers[last] < target) {
                    first++;
                } else {
                    return new int[]{first + 1, last + 1};
                }
            }
            return new int[]{0, 0};
        }

    }


    /**
     * 三数之和
     * <p>排序 + 三指针</p>
     * <p>因为不需要获取满足条件的 index, 所以可以直接使用排序减少遍历次数</p>
     * <p>因为三数之和可以拆分成两数之和, 所以做法和两数之和差距并不大, 重点在于如何去重</p>
     */
    public static class ThreeSum {

        public static List<List<Integer>> threeSum(int[] numbers) {
            List<List<Integer>> tuples = new ArrayList<>();
            // NOTE: 排序
            Arrays.sort(numbers);
            // NOTE: 三指针遍历
            for (int first = 0; first < numbers.length; first++) {
                // NOTE: 因为是有序的, 所以如果上个数字和当前数字相同, 那么就可以直接跳过
                if (first > 0 && numbers[first] == numbers[first - 1]) {
                    continue;
                }
                // NOTE: 计算两数之和
                int target = -numbers[first];
                // NOTE: 同样可以写成两数之和 II 那种形式, 不过没有这种方式简洁
                int third = numbers.length - 1;
                for (int second = 0; second < numbers.length; second++) {
                    // NOTE: 因为是有序的, 所以如果上个数字和当前数字相同, 那么就可以直接跳过
                    if (second > first + 1 && numbers[second] == numbers[second - 1]) {
                        continue;
                    }
                    // NOTE: 仅移动第三个指针
                    while (second < third && numbers[second] + numbers[third] > target) {
                        third--;
                    }
                    if (second == third) {
                        break;
                    }
                    if (numbers[second] + numbers[third] == target) {
                        tuples.add(Arrays.asList(numbers[first], numbers[second], numbers[third]));
                    }
                }
            }
            return tuples;
        }

    }

    /**
     * 四数之和
     * <p>排序 + 四指针</p>
     */
    public static class FourSum {

        public List<List<Integer>> fourSum(int[] numbers, int target) {
            List<List<Integer>> tuples = new ArrayList<>();
            Arrays.sort(numbers);
            for (int first = 0; first < numbers.length; first++) {
                if (first > 0 && numbers[first] == numbers[first - 1]) {
                    continue;
                }
                for (int second = first + 1; second < numbers.length; second++) {
                    if (second > first + 1 && numbers[second] == numbers[second - 1]) {
                        continue;
                    }
                    int fourth = numbers.length - 1;
                    long newTarget = (long) target - (long) numbers[first] - (long) numbers[second];
                    for (int third = second + 1; third < numbers.length; third++) {
                        if (third > second + 1 && numbers[third] == numbers[third - 1]) {
                            continue;
                        }
                        while (third < fourth && numbers[third] + numbers[fourth] > newTarget) {
                            fourth--;
                        }
                        if (third == fourth) {
                            break;
                        }
                        if (numbers[third] + numbers[fourth] == newTarget) {
                            tuples.add(Arrays.asList(numbers[first], numbers[second], numbers[third], numbers[fourth]));
                        }
                    }
                }
            }
            return tuples;
        }

    }


    /**
     * 最接近的三数之和: 实在不记得可以采用暴力解法
     */
    public static class ThreeSumCloset {

        /**
         * 类三数之和: 自己采用的解法, 类似于模拟, 在更新最小差值会较为麻烦
         */
        public static int threeSumClosetSelf(int[] numbers, int target) {
            int minMinus = Integer.MAX_VALUE;
            int minSum = Integer.MAX_VALUE;
            Arrays.sort(numbers);
            for (int first = 0; first < numbers.length; first++) {
                // NOTE: 同样可以在重复的情况下跳过, 没有再次遍历
                if (first > 0 && numbers[first] == numbers[first - 1]) {
                    continue;
                }
                int third = numbers.length - 1;
                // NOTE: 必须重新定义一个变量, 否则 target 会越来越小
                int newTarget = target - numbers[first];
                for (int second = first + 1; second < numbers.length; second++) {
                    if (second > first + 1 && numbers[second] == numbers[second - 1]) {
                        continue;
                    }
                    // NOTE: 继续遍历直到 target 的附近, 然后选取左右两侧的数字进行判断
                    while (second < third && numbers[second] + numbers[third] > newTarget) {
                        third--;
                    }
                    // NOTE: 计算左侧的差值
                    int leftMinus = second != third ? numbers[second] + numbers[third] - newTarget : Integer.MAX_VALUE;
                    int rightMinus = third + 1 < numbers.length ? numbers[second] + numbers[third + 1] - newTarget : Integer.MAX_VALUE;
                    // NOTE: 比较最小的差值
                    int minus = Math.abs(leftMinus) < Math.abs(rightMinus) ? leftMinus: rightMinus;
                    if (minMinus > Math.abs(minus)) {
                        minMinus = Math.abs(minus);
                        minSum = target + minus;
                    }
                }
            }
            return minSum;
        }

        /**
         * 类两数之和 II: 官方提供的解法, 感觉类似于贪心, 相对更好理解
         */
        public static int threeSumCloset(int[] numbers, int target) {
            int minSum = Integer.MAX_VALUE;
            Arrays.sort(numbers);
            for (int first = 0; first < numbers.length; first++) {
                if (first > 0 && numbers[first] == numbers[first - 1]) {
                    continue;
                }
                // NOTE: 采用类似两数之和 II 的解法
                int second = first + 1, third = numbers.length - 1;
                // NOTE: 开始遍历
                while (second < third) {
                    // NOTE: 计算三数之和
                    int sum = numbers[first] + numbers[second] + numbers[third];
                    if (sum == target) {
                        return target;
                    }
                    // NOTE: 更新最接近的和
                    if (Math.abs(sum - target) < Math.abs(minSum - target)) {
                        minSum = sum;
                    }
                    // NOTE: 如果当前的和大于目标值, 那么移动尾指针, 从而减小和的大小, 以达到减少差值的目的
                    if (sum > target) {
                        int index = third;
                        while (second < index && numbers[index] == numbers[third]) {
                            index--;
                        }
                        third = index;
                    } else {
                        // NOTE: 如果当前和小于目标值, 那么移动头指针, 从而增加和的大小, 以达到减少差值的目的
                        int index = second;
                        while (index < third && numbers[index] == numbers[second]) {
                            index++;
                        }
                        second = index;
                    }
                }
            }
            return minSum;
        }

    }

    /**
     * 有效三角形的个数:
     * <p>从左到右遍历: 双指针先固定最小的两条边, 然后移动指针选择最小的边 比较两边之和是否大于第三边</p>
     * <p>如果小于, 那么之前的边必然是满足条件的, 不需要继续遍历; 如果大于, 那么就继续移动指针</p>
     * <p>从右到左遍历: 双指针先固定最大的两条边, 然后移动指针选择最小的边, 比较两边之和是否大于第三边</p>
     * <p>如果大于, 那么之后的边必然是满足条件的, 不需要继续遍历; 如果小于, 那么就继续移动指针</p>
     */
    public static class TriangleNumber {

        /**
         * 三指针, 暴力解法
         */
        public static int triangleNumberSimple(int[] nums) {
            int count = 0;
            Arrays.sort(nums);
            for (int first = 0; first < nums.length; first++) {
                // NOTE: 结果是满足条件的三角形, 是需要重复计算的, 不能去重
                for (int second = first + 1; second < nums.length; second++) {
                    for (int third = second + 1; third < nums.length; third++) {
                        // NOTE: 最小的两边之和大于第三边
                        if (nums[first] + nums[second] > nums[third]) {
                            count++;
                        }
                    }
                }
            }
            return count;
        }

        /**
         * 三指针, 二分查找优化
         */
        public static int triangleNumberBinarySearch(int[] nums) {
            int count = 0;
            Arrays.sort(nums);
            for (int first = 0; first < nums.length; first++) {
                for (int second = first + 1; second < nums.length; second++) {
                    // NOTE: 记录最大满足两边之和大于第三边的 index
                    int index = second;
                    // NOTE: 第三个指针每次都需要从尾部开始遍历, 不能接着上次的位置继续遍历
                    int left = second + 1, right = nums.length - 1;
                    // NOTE: 执行二分查找
                    while (left <= right) {
                        int mid = left + ((right - left) >> 1);
                        if (nums[first] + nums[second] <= nums[mid]) {
                            right = mid - 1;
                        } else {
                            index = mid;
                            left = mid + 1;
                        }
                    }
                    count += index - second;
                }
            }
            return count;
        }

        /**
         * 三指针: 最优解, 类三数之和
         */
        public static int triangleNumber(int[] nums) {
            int count = 0;
            Arrays.sort(nums);
            for (int first = 0; first < nums.length; first++) {
                for (int second = first + 1, third = second + 1; second < nums.length; second++) {
                    // NOTE: 不停向后移动, 直到不满足两边之和大于第三边
                    while (third < nums.length && nums[first] + nums[second] > nums[third]) {
                        third++;
                    }
                    // NOTE: 避免没有满足条件的三角形时出现负数
                    count += Math.max(third - second - 1, 0);
                }
            }
            return count;
        }

    }

}
