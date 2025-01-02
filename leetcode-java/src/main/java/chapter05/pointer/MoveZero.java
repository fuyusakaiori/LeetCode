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
                swap(numbers, current++, index);
            }
        }
    }

    private static void swap(int[] numbers, int first, int second) {
        int temp = numbers[first];
        numbers[first] = numbers[second];
        numbers[second] = temp;
    }

}
