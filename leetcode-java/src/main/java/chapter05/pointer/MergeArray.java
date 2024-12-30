package chapter05.pointer;

/**
 * <p>合并两个有序数组<p>
 */
public class MergeArray {

    /**
     * 双指针: 额外空间
     */
    public static void mergeSimple(int[] firstNum, int firstLength, int[] secondNum, int secondLength) {
        int index = 0;
        int first = 0, second = 0;
        int[] numbers = new int[firstLength + secondLength];
        while (first < firstLength || second < secondLength) {
            int firstValue = first < firstLength ? firstNum[first] : Integer.MAX_VALUE;
            int secondValue = second < secondLength ? secondNum[second] : Integer.MAX_VALUE;
            if (firstValue < secondValue) {
                numbers[index++] = firstValue;
                first++;
            } else {
                numbers[index++] = secondValue;
                second++;
            }
        }
        System.arraycopy(numbers, 0, firstNum, 0, numbers.length);
    }

    /**
     * 双指针, 原地合并
     * <p>从右向左遍历, 可以直接将两个数组中较大的元素放入到末尾</p>
     * <p> 从左向右遍历, 就没有办法直接将两数组中较小的元素放入到开头, 因为会导致没有合并的元素丢失</p>
     */
    public static void merge(int[] firstNum, int firstLength, int[] secondNum, int secondLength) {
        int length = firstLength + secondLength;
        // NOTE: 不要使用数组的长度
        int first = firstLength - 1, second = secondLength - 1;
        // NOTE: 双指针遍历
        while (first >= 0 && second >= 0) {
            if (firstNum[first] < secondNum[second]) {
                firstNum[--length] = secondNum[second--];
            } else {
                firstNum[--length] = firstNum[first--];
            }
        }
        // NOTE: 如果第一个数组有剩余, 那么就放在原位即可; 如果第二个数组有剩余, 那么拷贝到第一个数组去
        if (second >= 0) {
            System.arraycopy(secondNum, 0, firstNum, 0, second + 1);
        }
    }
}
