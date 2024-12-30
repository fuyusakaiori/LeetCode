package chapter05.prefix;

import java.util.Arrays;

/**
 * <p>除自身以外数组的乘积</p>
 */
public class ProductExceptSelf {

    /**
     * <p>前缀和: 空间复杂度 O(n), 左右两侧同时计算前缀和</p>
     */
    public static int[] productExceptSelf(int[] nums) {
        int[] productions = new int[nums.length];
        int[] prefix = new int[nums.length + 1];
        int[] suffix = new int[nums.length + 1];
        // NOTE: 因为计算的是乘积, 所以需要将基础值填充为 1
        Arrays.fill(prefix, 1);
        Arrays.fill(suffix, 1);
        // NOTE: 计算前缀和 + 后缀和
        for (int index = 0; index < nums.length; index++) {
            prefix[index + 1] = prefix[index] * nums[index];
            suffix[index + 1] = suffix[index] * nums[nums.length - index - 1];
        }
        // NOTE: 根据前缀和计算乘积
        for (int index = 0; index < nums.length; index++) {
            productions[index] = prefix[index] * suffix[nums.length - index - 1];
        }
        return productions;
    }

    /**
     * <p>前缀和: 空间复杂度 O(1), 利用输出数组作为前缀和</p>
     */
    public static int[] productExceptSelfOptimize(int[] nums) {
        int[] productions = new int[nums.length];
        Arrays.fill(productions, 1);
        // NOTE: 计算前缀和
        int prefix = 1;
        for (int index = 0; index < nums.length; index++) {
            productions[index] *= prefix;
            prefix *= nums[index];
        }
        // NOTE: 计算后缀和的同时更新输出数组
        int suffix = 1;
        for (int index = nums.length - 1; index >= 0; index--) {
            // NOTE: 前缀和会减少, 后缀和会增加, 相乘就刚好是前后的乘积
            productions[index] *= suffix;
            suffix *= nums[index];
        }
        return productions;
    }

}
