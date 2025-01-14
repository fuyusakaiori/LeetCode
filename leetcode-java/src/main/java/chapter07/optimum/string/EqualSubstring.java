package chapter07.optimum.string;

/**
 * <p>尽可能使字符串相等</p>
 */
public class EqualSubstring {

    /**
     * <p>滑动窗口</p>
     */
    public static int equalSubstring(String source, String target, int maxCost) {
        int maxLength = 0;
        // 1. 初始化开销数组, 也可以放在滑动窗口里
        int[] costs = new int[source.length()];
        for (int index = 0; index < source.length(); index++) {
            costs[index] = Math.abs(source.charAt(index) - target.charAt(index));
        }
        // 2. 滑动窗口左右指针
        for (int left = 0, right = 0; right < source.length(); right++) {
            maxCost -= costs[right];
            while (maxCost < 0) {
                maxCost += costs[left++];
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

}
