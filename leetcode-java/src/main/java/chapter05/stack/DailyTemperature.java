package chapter05.stack;

import java.util.LinkedList;

/**
 * <p>每日温度</p>
 */
public class DailyTemperature {

    /**
     * <p>单调栈: 建议索引入栈而不是元素入栈</p>
     */
    public static int[] dailyTemperatures(int[] temperatures) {
        int[] answers = new int[temperatures.length];
        LinkedList<Integer> stack = new LinkedList<>();
        for (int index = 0; index < temperatures.length; index++) {
            while (!stack.isEmpty() && temperatures[index] > temperatures[stack.peek()]) {
                answers[stack.peek()] = index - stack.pop();
            }
            stack.push(index);
        }
        return answers;
    }

}
