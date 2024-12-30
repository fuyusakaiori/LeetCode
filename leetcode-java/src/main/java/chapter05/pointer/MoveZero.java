package chapter05.pointer;

import java.util.Arrays;

/**
 * <p>移动零</p>
 */
public class MoveZero {

    /**
     * 类双指针
     */
    public static void moveZeroes(int[] numbers) {
        int current = 0;
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] != 0) {
                numbers[current++] = numbers[index];
            }
        }
        Arrays.fill(numbers, current, numbers.length, 0);
    }

}
