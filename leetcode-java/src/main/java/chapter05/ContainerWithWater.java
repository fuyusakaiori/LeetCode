package chapter05;

import java.util.LinkedList;

/**
 * <h2>容器盛水</h2>
 * <h3>1. 盛最多的水</h3>
 * <h3>2. 接雨水</h3>
 */
public class ContainerWithWater {

    /**
     * <h3>思路: 盛最多的水</h3>
     * <h3>1. 动态规划改进形成的贪心算法</h3>
     * <h3>2. 直接采用双指针实现贪心算法</h3>
     * <h3>注: 后者非常不容易直接想到, 而前者和后者的思路基本一致, 但是前者是基于动态规划更容易想到</h3>
     * <h3>注: 这个题不能使用记忆化搜索, 只能采用剪枝的策略, 否则会超出内存</h3>
     */
    private static int maxArea1(int[] height){
        return dfs(height, 0, height.length - 1);
    }

    private static int dfs(int[] height, int left, int right){
        if (left >= right)
            return 0;
        // 1. 计算当前的面积大小
        int cur = Math.min(height[left], height[right]) * (right - left);
        int first = 0, second = 0;
        // 2. 如果左边界比右边界小, 那么之后移动右边界的行为就是没有必要的, 因为面积始终受限于左边界, 算出来的面积一定小于等于此前的面积
        if (height[left] <= height[right])
            first = dfs(height, left + 1, right);
        if (height[left] > height[right])
            second = dfs(height, left, right - 1);
        return Math.max(cur, Math.max(first, second));
    }

    /**
     * <h3>基于动态规划的贪心改成迭代的形式就是双指针</h3>
     */
    private static int maxArea2(int[] height){
        int max = 0;
        int left = 0, right = height.length - 1;
        while (left < right){
            max = Math.max(max, Math.min(height[left], height[right]) * (right - left));
            if (height[left] <= height[right]) {
                left++;
            }else{
                right--;
            }
        }
        return max;
    }

}
