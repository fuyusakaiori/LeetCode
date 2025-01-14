package chapter05.prefix;

import java.util.*;

/**
 * <p>长度最小的子数组</p>
 */
public class MinLengthSubArray {

    /**
     * <p>前缀和: 有序哈希表</p>
     * <p>如果直接使用哈希表, 那么比目标值大的前缀和就获取不到了</p>
     */
    public static int minSubArrayLenHash(int target, int[] nums) {
        int minLength = 0;
        int current = 0;
        NavigableMap<Integer, Integer> prefix = new TreeMap<>(){{
            put(0, -1);
        }};
        for (int index = 0; index < nums.length; index++) {
            current += nums[index];
            if (prefix.floorKey(current - target) != null) {
                int start = prefix.get(prefix.floorKey(current - target));
                minLength = minLength == 0 ? index - start : Math.min(minLength, index - start);
            }
            prefix.put(current, index);
        }
        return minLength;
    }

    /**
     * <p>前缀和: 二分搜索</p>
     * <p>因为前缀和是完全有序的, 所以可以考虑使用二分搜索</p>
     */
    public static int minSubArrayLenBinarySearch(int target, int[] nums) {
        int minLength = 0;
        int[] prefix = new int[nums.length + 1];
        // 1. 计算前缀和
        for (int index = 0; index < nums.length; index++) {
            prefix[index + 1] = prefix[index] + nums[index];
        }
        // 2. 前缀和 + 目标值进行二分搜索
        for (int index = 1; index <= nums.length; index++) {
            // NOTE: 哈希表前缀和的思想是拿着前缀和 - 目标值进行搜索, 但是二分搜索是对前缀和搜索, 所以作差是没办法搜索的, 只能求和
            int current = prefix[index - 1] + target;
            // 3. 二分搜索
            int location = Arrays.binarySearch(prefix, current);
            // 4. 判断是否搜索成功
            if (location < 0) {
                // NOTE: 如果没有搜索到, 会返回插入的位置
                location = - location - 1;
            }
            if (location <= nums.length) {
                minLength = minLength == 0 ? location - index + 1 : Math.min(minLength, location - index + 1);
            }
        }
        return minLength;
    }

}
