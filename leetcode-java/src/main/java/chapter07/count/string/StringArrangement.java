package chapter07.count.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>字符串排列</p>
 */
public class StringArrangement {

    /**
     * <p>滑动窗口: 固定窗口大小; 不建议在外初始化; 固定模版解法</p>
     */
    public static boolean checkInclusionSimple(String target, String source) {
        int valid = 0;
        Map<Character, Integer> map = new HashMap<>();
        // 1. 滑动窗口
        Map<Character, Integer> windows = new HashMap<>();
        // 2. 初始化目标字符串的字符数量
        for (int index = 0; index < target.length(); index++) {
            map.put(target.charAt(index), map.getOrDefault(target.charAt(index), 0) + 1);
        }
        // 3. 滑动窗口左右指针
        for (int left = 0, right = 0; right < source.length(); right++) {
            char rch = source.charAt(right);
            // 4. 统计有效字符数量
            if (map.containsKey(rch)) {
                windows.put(rch, windows.getOrDefault(rch, 0) + 1);
                if (windows.get(rch).intValue() == map.get(rch).intValue()) {
                    valid++;
                }
            }
            // 5. 判断有效字符是否连续 => 只要滑动窗口的大小等于目标字符串的大小, 那么就需要移动滑动窗口左指针
            if (right - left + 1 == target.length()) {
                if (valid == map.size()) {
                    return true;
                }
                char lch = source.charAt(left++);
                if (map.containsKey(lch)) {
                    if (windows.get(lch).intValue() == map.get(lch).intValue()) {
                        valid--;
                    }
                    windows.put(lch, windows.get(lch) - 1);
                }
            }
        }
        return false;
    }

    /**
     * <p>滑动窗口: 固定窗口大小; 可以在外初始化; 官方解法</p>
     */
    public static boolean checkInclusion(String target, String source) {
        // NOTE: 使用数组就必须提前判断不符合条件的情况
        if (target.length() > source.length()) {
            return false;
        }
        int[] map = new int[26];
        // 1. 滑动窗口
        int[] windows = new int[26];
        // 2. 初始化滑动窗口
        for (int index = 0; index < target.length(); index++) {
            map[target.charAt(index) - 'a']++;
            windows[source.charAt(index) - 'a']++;
        }
        if (Arrays.equals(map, windows)) {
            return true;
        }
        // 3. 滑动窗口左右指针
        for (int left = 0, right = target.length(); right < source.length(); right++) {
            windows[source.charAt(right) - 'a']++;
            // NOTE: 滑动窗口左指针移动是不需要任何条件的
            windows[source.charAt(left++) - 'a']--;
            if (Arrays.equals(map, windows)) {
                return true;
            }
        }
        return false;
    }

}
