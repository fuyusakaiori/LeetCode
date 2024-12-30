package chapter05.pointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>合并区间</p>
 * <p>插入区间</p>
 * <p>汇总区间</p>
 * <p>删除区间</p>
 */
public class IntervalArray {

    /**
     * 合并区间
     */
    public static class MergeInterval {

        /**
         * 双指针
         */
        public static int[][] merge(int[][] intervals) {
            // NOTE: 排序
            Arrays.sort(intervals, (first, second) -> first[0] - second[0]);
            int current = -1;
            int[][] newIntervals = new int[intervals.length][2];
            // NOTE: 双指针分别遍历不同的区间数组
            for (int index = 0; index < intervals.length; index++) {
                if (current == -1 || newIntervals[current][1] < intervals[index][0]) {
                    newIntervals[++current] = intervals[index];
                } else {
                    newIntervals[current][1] = Math.max(newIntervals[current][1], intervals[index][1]);
                }
            }
            return Arrays.copyOf(newIntervals, current + 1);
        }

    }

    /**
     * 插入区间
     */
    public static class InsertInterval {

        /**
         * 双指针: 拆分为三次循环
         */
        public static int[][] insert(int[][] intervals, int[] interval) {
            int index = 0, current = 0;
            int[][] newIntervals = new int[intervals.length + 1][2];
            // NOTE: 将左边无法合并的区间放入新的集合
            for (; index < intervals.length && intervals[index][1] < interval[0]; index++) {
                newIntervals[current++] = intervals[index];
            }
            // NOTE: 将中间可以合并的区间进行合并; 此时需要和后面的区间做比较
            for(; index < intervals.length && interval[1] >= intervals[index][0]; index++) {
                interval[0] = Math.min(intervals[index][0], interval[0]);
                interval[1] = Math.max(intervals[index][1], interval[1]);
            }
            newIntervals[current++] = interval;
            // NOTE: 将右侧无法合并的区间放入新的集合
            for (; index < intervals.length; index++) {
                newIntervals[current++] = intervals[index];
            }
            return Arrays.copyOf(newIntervals, current);
        }

    }

    /**
     * 汇总区间
     */
    public static class SummaryInterval {

        /**
         * 双指针: 利用数字之差等于索引之差的特点
         */
        public static List<String> summaryRangesSelf(int[] numbers) {
            if (numbers.length == 0) {
                return new ArrayList<>();
            }
            int previous = 0;
            StringBuilder interval = new StringBuilder();
            List<String> intervals = new ArrayList<>();
            for (int index = 0; index <= numbers.length; index++) {
                // NOTE: 避免最后一个元素无法放入集合
                if (index == numbers.length || numbers[index] - numbers[previous] != index - previous) {
                    interval.append(numbers[previous]);
                    // NOTE: 如果只有一个元素, 那么就不需要指向后面
                    if (index - 1 != previous) {
                        interval.append("->").append(numbers[index - 1]);
                    }
                    intervals.add(interval.toString());
                    interval = new StringBuilder();
                    previous = index;
                }
            }
            return intervals;
        }

        /**
         * 双指针: 官方实现
         */
        public static List<String> summaryRanges(int[] numbers) {
            int index = 0;
            List<String> intervals = new ArrayList<>();
            while (index < numbers.length) {
                int left = index++;
                while (index < numbers.length && numbers[index] == numbers[index - 1] + 1) {
                    index++;
                }
                int right = index - 1;
                StringBuilder interval = new StringBuilder();
                interval.append(numbers[left]);
                if (right > left) {
                    interval.append("->").append(numbers[right]);
                }
                intervals.add(interval.toString());
            }
            return intervals;
         }

    }

    /**
     * 删除区间
     */
    public static class RemoveInterval {

        /**
         * 遍历: 思路类似合并区间
         */
        public static int removeCoveredIntervals(int[][] intervals) {
            int count = 0;
            int[] interval = new int[2];
            Arrays.sort(intervals, (first, second) -> first[0] - second[0]);
            for (int index = 0; index < intervals.length; index++) {
                // NOTE: 区间的左边界是一定有序的, 只需要看区间的右边界大小
                if (index == 0 || interval[1] < intervals[index][1]) {
                    // NOTE: 如果两个区间存在不相交的位置, 那么就可以直接更新边界区间
                    // NOTE: 因为如果后面的区间能被之前的区间覆盖, 那么是一定可以被边界区间覆盖
                    count++;
                    interval = intervals[index];
                } else {
                    interval[1] = Math.max(interval[1], intervals[index][1]);
                }
            }
            return count;
        }

    }



}
