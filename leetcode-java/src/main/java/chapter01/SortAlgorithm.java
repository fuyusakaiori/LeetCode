package chapter01;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

@Slf4j
public class SortAlgorithm {
    /**
     * 冒泡排序
     */
    public static class BubbleSort {

        public static void sort(int[] nums) {
            // debug: 排序前的结果
            log.info("bubble sort: before sort {}", Arrays.toString(nums));
            for (int first = 0; first < nums.length; first++) {
                for (int second = 0; second < nums.length - 1 - first; second++) {
                    if (nums[second] > nums[second + 1]) {
                        swap(nums, second, second + 1);
                    }
                }
            }
            // debug: 排序后的结果
            log.info("bubble sort: after sort {}", Arrays.toString(nums));
        }
    }

    /**
     * 选择排序: 向后看
     */
    public static class SelectSort {

        public static void sort(int[] nums) {
            // debug: 排序前的结果
            log.info("select sort: before sort {}", Arrays.toString(nums));
            for (int first = 0; first < nums.length; first++) {
                int min = first;
                for (int second = first + 1; second < nums.length; second++) {
                    if (nums[min] > nums[second]) {
                        min = second;
                    }
                }
                swap(nums, first, min);
            }
            // debug: 排序后的结果
            log.info("select sort: after sort {}", Arrays.toString(nums));
        }
    }

    /**
     * 插入排序: 向前看
     */
    public static class InsertSort {

        public static void sort(int[] nums) {
            log.info("insert sort: before sort {}", Arrays.toString(nums));
            for (int first = 0; first < nums.length; first++) {
                for (int second = first; second > 0; second--) {
                    if (nums[second] < nums[second - 1]) {
                        swap(nums, second - 1, second);
                    }
                }
            }
            log.info("insert sort: before sort {}", Arrays.toString(nums));
        }

    }

    /**
     * 归并排序
     */
    public static class MergeSort {

        public static void sort(int[] nums) {
            log.info("merge sort: before sort {}", Arrays.toString(nums));
            fork(nums, 0, nums.length - 1);
            log.info("merge sort: after sort {}", Arrays.toString(nums));
        }

        private static void fork(int[] nums, int left, int right) {
            if (left >= right) {
                return;
            }
            int mid = left + ((right - left) >> 1);
            fork(nums, left, mid);
            fork(nums, mid + 1, right);
            merge(nums, left, mid, right);
        }

        private static void merge(int[] nums, int left, int mid, int right) {
            int[] helper = new int[right - left + 1];
            int index = 0, leftIndex = left, rightIndex = mid + 1;
            while (leftIndex <= mid && rightIndex <= right) {
                helper[index++] = nums[leftIndex] < nums[rightIndex] ? nums[leftIndex++]: nums[rightIndex++];
            }
            while (leftIndex <= mid) {
                helper[index++] = nums[leftIndex++];
            }
            while (rightIndex <= right) {
                helper[index++] = nums[rightIndex++];
            }
            System.arraycopy(helper, 0, nums, left, helper.length);
        }
    }

    /**
     * 快速排序
     */
    public static class QuickSort {

        public static void sort(int[] nums) {
            log.info("quick sort: before sort {}", Arrays.toString(nums));
            fork(nums, 0, nums.length - 1);
            log.info("quick sort: after sort {}", Arrays.toString(nums));
        }

        public static void fork(int[] nums, int left, int right) {
            if (left >= right) {
                return;
            }
            swap(nums, left + new Random().nextInt(right - left), right);
            int[] partition = partition(nums, left, right, nums[right]);
            fork(nums, left, partition[0]);
            fork(nums, partition[1], right);
        }

        public static int[] partition(int[] nums, int left, int right, int target) {
            int index = left, leftIndex = left, rightIndex = right;
            while (index < rightIndex + 1) {
                if (nums[index] < target) {
                    swap(nums, index++, leftIndex++);
                } else if (nums[index] > target) {
                    swap(nums, index, rightIndex--);
                } else {
                    index++;
                }
            }
            return new int[]{leftIndex - 1, rightIndex + 1};
        }

    }

    /**
     * 堆排序
     */
    public static class HeapSort {

        public static void sort(int[] nums) {
            log.info("heap sort: before sort {}", Arrays.toString(nums));
            int heapSize = 0;
            while (heapSize < nums.length) {
                heapInsert(nums, heapSize++);
            }
            swap(nums, 0, --heapSize);
            while (heapSize > 0) {
                heapify(nums, 0, heapSize);
                swap(nums, 0, --heapSize);
            }
            log.info("heap sort: after sort {}", Arrays.toString(nums));
        }

        public static void heapInsert(int[] nums, int index) {
            int parentIndex = (index - 1) / 2;
            while (nums[index] > nums[parentIndex]) {
                swap(nums, index, parentIndex);
                index = parentIndex;
                parentIndex = (index - 1) / 2;
            }
        }

        public static void heapify(int[] nums, int parentIndex, int heapSize) {
            int leftIndex = (parentIndex << 1) + 1;
            while (leftIndex < heapSize) {
                int largestIndex = leftIndex + 1 < heapSize &&
                        nums[leftIndex] < nums[leftIndex + 1] ? leftIndex + 1 : leftIndex;
                largestIndex = nums[parentIndex] < nums[largestIndex] ? largestIndex: parentIndex;
                if (largestIndex == parentIndex) {
                    break;
                }
                swap(nums, parentIndex, largestIndex);
                parentIndex = largestIndex;
                leftIndex = (parentIndex << 1) + 1;
            }
        }
    }

    /**
     * 桶排序
     */
    public static class BucketSort {

        public static void sort(int[] nums) {
            // debug: 排序前的结果
            log.info("bucket sort: before sort {}", Arrays.toString(nums));
            radixSort(nums, 0, nums.length - 1, 10);
            // debug: 排序前的结果
            log.info("bucket sort: after sort {}", Arrays.toString(nums));
        }

        private static void radixSort(int[] numbers, int left, int right, int bits){
            // 1. 准备词频数组, 辅助数组
            final int radix = 10;
            int[] bucket = new int[right - left + 1];
            // 2. 统计词频: 相当于入桶的操作
            // 有几位就会执行几次入桶出桶的操作
            for (int k = 1; k <= bits; k++) {
                // 注意: 每次词频数组都需要清空再添加, 否则就会越界
                int[] count = new int[radix];
                for (int i = left; i <= right; i++) {
                    int digit = getDigit(numbers[i], k);
                    count[digit]++;
                }
                // 3. 计算前缀和
                for (int i = 1; i < count.length; i++) {
                    count[i] = count[i] + count[i - 1];
                }
                // 4. 从右向左遍历: 相当于出桶
                for (int i = right;i >= left;i--){
                    int digit = getDigit(numbers[i], k);
                    bucket[count[digit] - 1] = numbers[i];
                    count[digit]--;
                }
                // 5. 更新原数组
                for (int i = 0, j = left; i <= right; i++, j++) {
                    numbers[i] = bucket[j];
                }
            }
        }

        private static int maxBits(int[] numbers){
            // 1. 先遍历寻找到的最大值
            int max = 0;
            for (int number : numbers) {
                max = Math.max(number, max);
            }
            // 2. 判断最大值的位数
            int res = 0;
            while (max != 0){
                max /= 10;
                res++;
            }
            return res;
        }

        private static int getDigit(int number, int digit){
            return (number / (int) Math.pow(10, digit - 1)) % 10;
        }
    }

    private static void swap(int[] nums, int first, int second){
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

    public static void main(String[] args) {
        int[] nums = new int[10];
        // 1. 随机生成数字
        for (int index = 0; index < nums.length; index++) {
            nums[index] = new Random().nextInt(20);
        }
        // 2. 调用排序算法
        SortAlgorithm.BubbleSort.sort(nums.clone());
        SortAlgorithm.SelectSort.sort(nums.clone());
        SortAlgorithm.InsertSort.sort(nums.clone());
        SortAlgorithm.MergeSort.sort(nums.clone());
        SortAlgorithm.QuickSort.sort(nums.clone());
        SortAlgorithm.HeapSort.sort(nums.clone());
        SortAlgorithm.BucketSort.sort(nums.clone());
    }

}
