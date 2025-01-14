package chapter07.optimum.string;

/**
 * <p>替换后最长重复字符</p>
 */
public class LongestRepeatCharacterReplacement {

    /**
     * <p>滑动窗口</p>
     * <p>1. 替换后的最长重复字符思路和最大连续 1 的个数是相同</p>
     * <p>2. 没有必要实际去替换字符串来表示相同, 只需要减少可以使用的替换次数就行</p>
     * <p>3. 替换的次数 = 滑动窗口大小 - 出现次数最多的字符 => 使用哈希表</p>
     */
    public static int characterReplacement(String source, int count) {
        int maxLength = 0;
        // 1. 滑动窗口
        int windowsMaxCount = 0;
        int[] windows = new int[26];
        // 2. 滑动窗口左右指针
        for (int left = 0, right = 0; right < source.length(); right++) {
            windows[source.charAt(right) - 'A']++;
            // 3. 统计滑动窗口中的出现最多的字符
            windowsMaxCount = Math.max(windowsMaxCount, windows[source.charAt(right) - 'A']);
            // 4. 滑动窗口左指针移动的条件: 超过可以使用的替换次数
            while (right - left + 1 - windowsMaxCount > count) {
                windows[source.charAt(left++) - 'A']--;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}
