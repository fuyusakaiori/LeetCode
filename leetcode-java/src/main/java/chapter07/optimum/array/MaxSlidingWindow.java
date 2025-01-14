package chapter07.optimum.array;

import java.util.*;

/**
 * <p>滑动窗口最大值</p>
 *
 */
public class MaxSlidingWindow {

    /**
     * <p>滑动窗口: 使用优先权队列作为滑动窗口</p>
     * <p>1. 如果直接使用单个变量维护最大值的索引, 那么在滑动窗口的左指针移动后, 就没有办法重新计算最大值</p>
     * <p>2. 因为有可能滑动窗口的最大值就是左指针指向的元素, 所以只要左指针发生变化, 那么新的最大值就不得而知了</p>
     * <p>3. 因为需要快速计算出滑动窗口中的最大值, 所以需要使用一种可以快速获取最值的数据结构</p>
     * <p>3.1. 优先权队列 [堆]</p>
     * <p>3.2. 单调为队列 [单调递减维护最大值]</p>
     */
    public static int[] maxSlidingWindowPriority(int[] nums, int count) {
        // 1. 滑动窗口最大值数组
        int[] maxValues = new int[nums.length - count + 1];
        // 2. 滑动窗口: 记录索引目的是为了判断最值是否已经不在滑动窗口中
        PriorityQueue<int[]> windows = new PriorityQueue<>((first, second) -> second[1] - first[1]);
        // 3. 初始化滑动窗口的内容: 窗口大小固定
        for (int index = 0; index < count; index++) {
            windows.offer(new int[]{index, nums[index]});
        }
        // NOTE: 因为第一个滑动窗口的最大值已经记录了, 所以后续滑动窗口的最大值需要从 1 开始
        maxValues[0] = windows.peek()[1];
        // 4. 滑动窗口的左右指针
        for (int left = 0, right = count; right < nums.length; right++) {
            left++;
            // 5. 将新的元素放入滑动窗口中
            windows.offer(new int[]{right, nums[right]});
            // 6. 判断滑动窗口最大值是否已经不在滑动窗口: 需要将之前所有滑动窗口的最大值都移除
            while (!windows.isEmpty() && windows.peek()[0] < left) {
                windows.poll();
            }
            // 7. 记录滑动窗口最大值并移动滑动窗口左指针; 固定滑动窗口没有移动条件
            maxValues[left] = windows.peek()[1];
        }
        return maxValues;
    }

    /**
     * <p>滑动窗口: 单调队列</p>
     */
    public static int[] maxSlidingWindowDecrease(int[] nums, int count) {
        int[] maxValues = new int[nums.length - count + 1];
        // 1. 滑动窗口: 不直接维护元素而是维护索引
        Deque<Integer> windows = new LinkedList<>();
        // 2. 初始化滑动窗口的内容
        for (int index = 0; index < count; index++) {
            while (!windows.isEmpty() && nums[windows.peekLast()] < nums[index]) {
                windows.pollLast();
            }
            windows.offerLast(index);
        }
        // NOTE: 单调递减 [i] < [j]
        maxValues[0] = nums[windows.peekFirst()];
        // 3. 滑动窗口左右指针
        for (int left = 0, right = count; right < nums.length; right++) {
            left++;
            // 4. 判断滑动窗口现在是否单调: 同样需要使用循环将之前滑动窗口的最大值移除
            while (!windows.isEmpty() && nums[windows.peekLast()] < nums[right]) {
                // NOTE: 检查队尾的元素是否为最大的元素
                windows.pollLast();
            }
            // 5. 将新的元素加入单调队列的队尾
            windows.offerLast(right);
            // 6. 判断单调队列的队首的元素是否已经不在滑动窗口中
            while (!windows.isEmpty() && windows.peekFirst() < left) {
                windows.pollFirst();
            }
            maxValues[left] = nums[windows.peekFirst()];
        }
        return maxValues;
    }
}
