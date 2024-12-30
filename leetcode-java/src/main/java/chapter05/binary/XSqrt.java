package chapter05.binary;

/**
 * <p>X 的平方根</p>
 */
public class XSqrt {

    /**
     * <p>遍历: 两数相乘逼近目标值</p>
     */
    public static int mySqrtManual(int target) {
        long number = 0;
        while(number * number <= target){
            number++;
        }
        return (int) number - 1;
    }

    /**
     * <p>二分查找: 两数相乘逼近目标值</p>
     */
    public static int mySqrtBinarySearchManual(int target) {
        long sqrt = 0;
        long left = 0, right = target;
        while (left <= right) {
            long mid = left + ((right - left) >> 1);
            // NOTE: 如果相等其实也可以直接返回
            if (mid * mid <= target) {
                sqrt = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return (int) sqrt;
    }


    /**
     * <p>牛顿迭代: 数值分析算法</p>
     */
    private static int mySqrtNewton(int target){
        // 注: 这里强制转换回舍弃掉小数, 可能在取整的时候出现错误
        int number = (int) Math.exp(0.5 * Math.log(target));
        // 注: 内置函数在计算结果的时候会存在误差, 需要我们手动去修正误差
        return (long) (number + 1) * (number + 1) <= target ? number + 1: number;
    }

}
