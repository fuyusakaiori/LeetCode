package chapter05.stack;

import java.util.LinkedList;

/**
 * <p>1. 最大矩形</p>
 * <p>2. 柱状图中的最大矩形</p>
 */
public class MaxRectangle {

    /**
     * <p>最大矩形</p>
     */
    public static class MaximalRectangle {

    }

    /**
     * <p>柱状图的最大矩形</p>
     */
    public static class LargestRectangleHistogram {

        /**
         * <p>单调栈</p>
         * <p>1. 如果能想到使用单调栈这个方法, 那么难度就会降低, 但是依然存在问题</p>
         * <p>2. 在单调栈的基础上还需要使用哨兵元素, 从而确保结果正确, 这个是非常不容易想到的</p>
         */
        public static int largestRectangleArea(int[] heights){
            int max = 0;
            LinkedList<Integer> stack = new LinkedList<>();
            // 1. 哨兵结点: 此前弹出的元素都会栈顶元素大, 所以栈顶元素出栈的时候不仅可以右侧的, 还可以使用左侧的, 没有哨兵就会漏算左侧的
            stack.push(0);
            // 2. 还可以在数组末尾添加哨兵, 从而不用额外添加循环, 但是需要修改数组
            int[] newHeights = new int[heights.length + 2];
            System.arraycopy(heights, 0, newHeights, 1, heights.length);
            // 3. 单调栈
            for (int index = 1;index < newHeights.length;index++) {
                while (newHeights[stack.peek()] > newHeights[index]) {
                    // 注: 这里不要使用 index - stack.pop(), 会导致右侧面积计算不到
                    int height = newHeights[stack.pop()];
                    int width = index - stack.peek() - 1;
                    max = Math.max(max, height * width);
                }
                stack.push(index);
            }
            return max;
        }
        
    }

}
