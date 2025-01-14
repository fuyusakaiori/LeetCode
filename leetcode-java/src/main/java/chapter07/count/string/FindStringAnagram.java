package chapter07.count.string;

import java.util.*;

/**
 * <h3>找到字符串中所有字母异位词</h3>
 */
public class FindStringAnagram {

    /**
     * <p>滑动窗口: 固定窗口大小; 不在外初始化; 固定模版</p>
     */
    public static List<Integer> findAnagramsSimple(String source, String target) {
        int valid = 0;
        List<Integer> anagrams = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();
        // 1. 滑动窗口
        Map<Character, Integer> windows = new HashMap<>();
        // 2. 初始化目标字符串中的字符数量
        for (int index = 0; index < target.length(); index++) {
            map.put(target.charAt(index), map.getOrDefault(target.charAt(index), 0) + 1);
        }
        // 3. 滑动窗口左右指针
        for (int left = 0, right = 0; right < source.length(); right++) {
            char rch = source.charAt(right);
            // 4. 统计有效的字符数量
            if (map.containsKey(rch)) {
                windows.put(rch, windows.getOrDefault(rch, 0) + 1);
                if (windows.get(rch).intValue() == map.get(rch).intValue()) {
                    valid++;
                }
            }
            // 5. 滑动窗口左指针的移动条件
            if (right - left + 1 == target.length()) {
                if (valid == map.size()) {
                    anagrams.add(left);
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
        return anagrams;
    }

    /**
     * <p>滑动窗口: 固定窗口大小; 在外初始化; 官方解法</p>
     */
    public static List<Integer> findAnagrams(String source, String target) {
        if (target.length() > source.length()) {
            return new ArrayList<>();
        }
        int[] map = new int[26];
        // 1. 滑动窗口
        int[] windows = new int[26];
        List<Integer> anagrams = new ArrayList<>();
        // 2. 初始化滑动窗口
        for (int index = 0; index < target.length(); index++) {
            map[target.charAt(index) - 'a']++;
            windows[source.charAt(index) - 'a']++;
        }
        if (Arrays.equals(map, windows)) {
            anagrams.add(0);
        }
        // 3. 滑动窗口左右指针
        for (int left = 0, right = target.length(); right < source.length(); right++) {
            windows[source.charAt(right) - 'a']++;
            windows[source.charAt(left++) - 'a']--;
            if (Arrays.equals(windows, map)) {
                anagrams.add(left);
            }
        }
        return anagrams;
    }
}
