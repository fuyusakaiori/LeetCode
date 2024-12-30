package chapter05.pointer;

import java.util.*;

/**
 * <p>1. 两个数组交集</p>
 * <p>2. 两个数组交集 II</h3>
 */
public class IntersectArray {

    /**
     * <p>两个数组的交集</p>
     * <p>1. 哈希表</p>
     * <p>2. 双指针</p>
     */
    public static class ArrayIntersectI {

        /**
         * 哈希表
         * <p>只需要将第一个数组放入哈希表, 然后遍历第二个数组, 只要在哈希表中存在就放入交集, 然后移除元素</p>
         */
        public static int[] intersectionHash(int[] firstNum, int[] secondNum) {
            int current = 0;
            int[] intersection = new int[firstNum.length + secondNum.length];
            Set<Integer> set = new HashSet<>();
            for (int index = 0; index < firstNum.length; index++) {
                set.add(firstNum[index]);
            }
            for (int index = 0; index < secondNum.length; index++) {
                if (set.contains(secondNum[index])) {
                    intersection[current++] = secondNum[index];
                    set.remove(secondNum[index]);
                }
            }
            return Arrays.copyOf(intersection, current);
        }

        /**
         * 双指针
         * <p>如果内存有限, 那么只能采用双指针的方式, 采用外部排序 + 双指针</p>
         */
        public static int[] intersection(int[] firstNum, int[] secondNum) {
            // 1. 排序
            Arrays.sort(firstNum);
            Arrays.sort(secondNum);
            // 2. 双指针遍历
            int index = 0;
            int first = 0, second = 0;
            int[] intersection = new int[firstNum.length + secondNum.length];
            while (first < firstNum.length && second < secondNum.length) {
                if (firstNum[first] < secondNum[second]) {
                    first++;
                } else if (firstNum[first] > secondNum[second]) {
                    second++;
                } else {
                    if (first == 0 || firstNum[first] != firstNum[first - 1]) {
                        intersection[index++] = firstNum[first];
                    }
                    first++;
                    second++;
                }
            }
            return Arrays.copyOf(intersection, index);
        }

    }

    /**
     * <p>两个数组的交集 II</p>
     */
    public static class ArrayIntersectII {

        /**
         * 哈希表
         * <p>只需要将第一个数组放入哈希表, 然后遍历第二个数组, 只要在哈希表中存在就放入交集, 然后移除元素</p>
         */
        public static int[] intersectionHash(int[] firstNum, int[] secondNum) {
            int current = 0;
            int[] intersection = new int[firstNum.length + secondNum.length];
            Map<Integer, Integer> map = new HashMap<>();
            for (int index = 0; index < firstNum.length; index++) {
                map.put(firstNum[index], map.getOrDefault(firstNum[index] ,0 ) + 1);
            }
            for (int index = 0; index < secondNum.length; index++) {
                int count = map.getOrDefault(secondNum[index], 0);
                if (count > 0) {
                    intersection[current++] = secondNum[index];
                    count--;
                    map.put(secondNum[index], count);
                }
            }
            return Arrays.copyOf(intersection, current);
        }

        /**
         * 双指针
         * <p>如果内存有限, 那么只能采用双指针的方式, 采用外部排序 + 双指针</p>
         */
        public static int[] intersection(int[] firstNum, int[] secondNum) {
            // 1. 排序
            Arrays.sort(firstNum);
            Arrays.sort(secondNum);
            // 2. 双指针
            int index = 0;
            int first = 0, second = 0;
            int[] intersection = new int[firstNum.length + secondNum.length];
            while (first < firstNum.length && second < secondNum.length) {
                if (firstNum[first] < secondNum[second]) {
                    first++;
                } else if (firstNum[first] > secondNum[second]) {
                    second++;
                } else {
                    intersection[index++] = firstNum[first];
                    first++;
                    second++;
                }
            }
            return Arrays.copyOf(intersection, index);
        }

    }

}
