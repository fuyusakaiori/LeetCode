package chapter05.stack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p>下一个更大的元素</p>
 * <p>下一个更大的元素 II</p>
 */
public class NextGreaterElement {

    /**
     * <p>下一个更大的元素</p>
     */
    public static class NextGreaterElementI {

        /**
         * <p>单调栈</p>
         */
        public static int[] nextGreaterElement(int[] nums1 ,int[] nums2) {
            int[] answers = new int[nums1.length];
            Map<Integer, Integer> map = new HashMap<>();
            LinkedList<Integer> stack = new LinkedList<>();
            for (int index = 0; index < nums2.length; index++) {
                while (!stack.isEmpty() && nums2[index] > nums2[stack.peek()]) {
                    map.put(nums2[stack.pop()], nums2[index]);
                }
                stack.push(index);
            }
            for (int index = 0; index < nums1.length; index++) {
                answers[index] = map.getOrDefault(nums1[index], -1);
            }
            return answers;
        }

    }

    /**
     * <p>下一个更大的元素 II</p>
     */
    public static class NextGreaterElementII {

        /**
         * <p>单调栈: 双倍长度取模</p>
         * <p>1. 循环数组确实需要取模, 不过直接对索引取模就会导致循环最后难以终止</p>
         * <p>2. 所以可以考虑使用两倍的长度, 因为这里最多只会走两圈, 所以用两倍的长度来做终止条件是可以的</p>
         */
        public static int[] nextGreaterElements(int[] nums) {
            int[] answers = new int[nums.length];
            LinkedList<Integer> stack = new LinkedList<>();
            Arrays.fill(answers, - 1);
            // NOTE: 两倍长度会导致重复入栈, 不过没有关系并不会影响结果
            for (int index = 0; index < nums.length * 2 - 1; index++) {
                while (!stack.isEmpty() && nums[index % nums.length] > nums[stack.peek()]) {
                    answers[stack.pop()] = nums[index % nums.length];
                }
                stack.push(index % nums.length);
            }
            return answers;
        }

    }


}
