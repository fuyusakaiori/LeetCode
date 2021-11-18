package chapter01.sort;

import utils.RandomUtil;

import java.util.Arrays;
import java.util.Random;

public class QuickSort
{
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(10, 20);
        int[] array = random.randomArrayWithReplica();
        System.out.println(Arrays.toString(array));
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    /*
    递归最折磨的就是找边界, 有时候非常容易误判边界问题
    因为你需要拆分, 一旦拆分, 起始点就很有可能不是从零开始的, 最大的边界也很有可能不是数组长度-1
    你使用的只能够是递归传递的边界参数
     */
    private static void quickSort(int[] numbers, int left, int right){
        if (left < right){
            int critical = right - left;
            // TODO 随机挑选一个值
            int randomIndex = new Random().nextInt(critical + 1);
            // TODO
            swap(numbers, left + randomIndex, right);
            // 开始分区计算
            // TODO
            int[] criticals = partition(numbers, left, right, numbers[right]);
            quickSort(numbers, left, criticals[0] - 1);
            quickSort(numbers, criticals[1] + 1, right);
        }

    }

    // 荷兰国旗问题
    private static int[] partition(int[] numbers, int left, int right, int target){
        int index = left;
        int leftIndex = left;
        int rightIndex = right;
        // rightIndex 代表的是边界, 而不是边界值的位置
        while (index != rightIndex + 1){
            if (numbers[index] > target){
                swap(numbers, index, rightIndex);
                rightIndex--;
            }else if (numbers[index] < target){
                swap(numbers, index, leftIndex);
                leftIndex++;
                index++;
            }else{
                index++;
            }
        }

        return new int[]{leftIndex, rightIndex};
    }

    private static void swap(int[] numbers, int first, int second){
        int temp = numbers[first];
        numbers[first] = numbers[second];
        numbers[second] = temp;
    }
}
