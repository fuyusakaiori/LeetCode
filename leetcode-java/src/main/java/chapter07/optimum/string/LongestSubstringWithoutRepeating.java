package chapter07.optimum.string;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>无重复字符最长子串</p>
 */
public class LongestSubstringWithoutRepeating {

    /**
     * <p>滑动窗口: 使用哈希表作为窗口</p>
     */
    public static int lengthOfLongestSubstringHash(String string) {
        int maxLength = 0;
        // 1. 滑动窗口的数据结构: 少数题目可以不使用数据结构作为窗口, 仅使用双指针维护
        Set<Character> windows = new HashSet<>();
        // 2. 滑动窗口的左右指针
        for (int left = 0, right = 0; right < string.length(); right++) {
            char rch = string.charAt(right);
            // 3: 滑动窗口左指针移动的条件: 只要出现重复, 那么就移动左指针直到没有重复为止
            while (left < right && windows.contains(rch)) {
                char lch = string.charAt(left++);
                windows.remove(lch);
            }
            // 4. 添加新的元素到滑动窗口中
            windows.add(string.charAt(right));
            // NOTE: 最大的长度不需要在移动左指针的循环里更新
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

    /**
     * <p>滑动窗口: 使用数组作为窗口</p>
     * <p>字符串都可以将哈希表转换为数组</p>
     */
    public static int lengthOfLongestSubstringArray(String string) {
        int maxLength = 0;
        // NOTE: 存在空格, 滑动窗口需要包含整个 ASCII 表
        int[] windows = new int[128];
        for (int left = 0, right = 0; right < string.length(); right++) {
            int rch = string.charAt(right);
            while (left < right && windows[rch] != 0) {
                int lch = string.charAt(left++);
                windows[lch] = 0;
            }
            windows[rch]++;
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

}
