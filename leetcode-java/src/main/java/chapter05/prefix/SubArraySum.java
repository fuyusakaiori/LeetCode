package chapter05.prefix;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>和为 K 的子数组</p>
 */
public class SubArraySum {

    /**
     * <p>暴力解</p>
     */
    private static int subArraySum1(int[] numbers, int target){
        int count = 0;
        for (int first = 0;first < numbers.length; first++){
            int sum = 0;
            for (int second = first; second < numbers.length; second++){
                if((sum += numbers[second]) == target) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * <p>前缀和: 数组</p>
     */
    private static int subArraySum2(int[] numbers, int target){
        int[] prefix = new int[numbers.length + 1];
        for(int index = 0;index < numbers.length;index++){
            prefix[index + 1] = prefix[index] + numbers[index];
        }
        int count = 0;
        for (int left = 0; left < prefix.length; left++){
            for (int right = left + 1; right < prefix.length; right++){
                if (prefix[left] == prefix[right] - target) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * <p>前缀和: 哈希表</p>
     */
    public static int subArraySum(int[] nums, int target) {
        int count = 0;
        int prefixSum = 0;
        Map<Integer, Integer> prefixSums = new HashMap<>();
        for (int index = 0; index < nums.length; index++) {
            prefixSum += nums[index];
            count += prefixSums.getOrDefault(prefixSum - target, 0);
            prefixSums.put(prefixSum, prefixSums.getOrDefault(prefixSum, 0) + 1);
        }
        return count;
    }

}
