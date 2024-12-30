package chapter05.pointer;

/**
 * <p>轮转数组</p>
 */
public class RotateArray {

    /**
     * <p>遍历: 使用额外数组</p>
     */
    public static void rotate1(int[] numbers, int k){
        int[] move = new int[numbers.length];
        for (int index = 0;index < numbers.length;index++){
            move[(index + k) % numbers.length] = numbers[index];
        }
        System.arraycopy(move, 0, numbers, 0, numbers.length);
    }

    /**
     * <p>环状数组: 类似于旋转链表</p>
     */
    public static void rotateCycle(int[] numbers, int k){
        int count = 0;
        int offset = k % numbers.length;
        // NOTE: 只要所有的元素都被调换到正确的位置, 那么就可以结束
        for (int start = 0; count < numbers.length; start++) {
            int current = start;
            int previous = numbers[current];
            // NOTE: 从起点开始, 逐次将元素迁移到对应的位置去
            do {
                int next = (current + offset) & numbers.length;
                int temp = numbers[next];
                // NOTE: 让当前位置的元素等于之前位置的元素, 其实就是将之前的元素迁移到当前的这个位置
                numbers[next] = previous;
                previous = temp;
                current = next;
                count++;
            } while (start != current);
            // NOTE: 每次移动都有可能将重新回到起点, 如果此时继续交换, 那么会因为起点的值没有变化而导致交换的结果出错
        }
    }

    /**
     * <p>数组翻转: 类似于旋转矩阵</p>
     */
    public static void rotateReverse(int[] numbers, int k){
        // 注: 记得取模, 因为移动的位数可能超过数组的长度
        k %= numbers.length;
        // 1. 先将整个数组翻转
        reverse(numbers, 0, numbers.length - 1);
        // 2. 翻转后半部分
        reverse(numbers, k, numbers.length - 1);
        // 3. 翻转前半部分
        reverse(numbers, 0, k - 1);
    }

    private static void reverse(int[] numbers, int first, int last){
        while (first < last){
            int temp = numbers[first];
            numbers[first] = numbers[last];
            numbers[last] = temp;
            first++;
            last--;
        }
    }

}
