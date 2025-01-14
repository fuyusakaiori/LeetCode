package chapter07.optimum.string;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>最小覆盖子串</p>
 */
public class MinimumWindowSubstring {

    /**
     * <p>滑动窗口: 使用哈希表作为滑动窗口</p>
     * <p>1. 首先需要两个哈希表: </p>
     * <p>1.1 cnt: 记录需要覆盖的子串的每种字符的数量</p>
     * <p>1.2 window: 滑动窗口 记录遍历过程中的子串符合要求的每种字符的数量</p>
     * <p>2. 开始遍历: </p>
     * <p>2.1 右指针不停滑动, 将所有满足条件的字符全部放入 window</p>
     * <p>2.2 如果满足条件的字符数量已经达到覆盖子串的字符数量, 那么就认为这个字符已经满足要求, 增加有效值</p>
     * <p>2.3 如果有效值已经等于 cnt 的长度, 那么就认为当前子串已经完成覆盖, 但不一定是最小的</p>
     * <p>2.4 开始移动左指针, 如果满足条件的字符数量小于覆盖子串的字符数量, 那么就认为没有满足条件, 有效值减少</p>
     * <p>3. 退出循环</p>
     * <p>本质 cnt 哈希表的作用就是左指针移动的条件</p>
     */
    public static String minWindowHash(String source, String target) {
        int valid = 0;
        int start = 0;
        int minLength = 0;
        Map<Character, Integer> map = new HashMap<>();
        // 1. 滑动窗口
        Map<Character, Integer> windows = new HashMap<>();
        // 2. 统计目标字符串的字符数: 作为滑动窗口左指针移动的判断条件
        for (int index = 0; index < target.length(); index++) {
            map.put(target.charAt(index), map.getOrDefault(target.charAt(index), 0) + 1);
        }
        // 3. 滑动窗口左右指针
        for (int left = 0, right = 0; right < source.length(); right++) {
            char rch = source.charAt(right);
            // 4. 统计目标字符串中的字符
            if (map.containsKey(rch)) {
                windows.put(rch, windows.getOrDefault(rch, 0) + 1);
                // 5. 如果满足目标字符串中的字符数, 那么有效字符数就会增加
                if (windows.get(rch).intValue() == map.get(rch).intValue()) {
                    // NOTE: 整数型的包装类需要拆箱, 否则比较会出现问题
                    valid++;
                }
            }
            // 6. 滑动窗口左指针移动的条件: 有效数字是否已经和目标字符串一致
            while (valid == map.size()) {
                // 7. 每次移动左指针都需要重新调整最小长度
                if (minLength == 0 || minLength > right - left + 1) {
                    start = left;
                    minLength = right - left + 1;
                }
                char lch = source.charAt(left++);
                // 8. 如果移除的字符是目标字符串中的字符, 那么就需要调整滑动窗口中的字符数量
                if (map.containsKey(lch)) {
                    // 9. 需要提前判断是否需要减少有效数字
                    if (windows.get(lch).intValue() == map.get(lch).intValue()) {
                        valid--;
                    }
                    windows.put(lch, windows.get(lch) - 1);
                }
            }
        }
        return source.substring(start, start + minLength);
    }

    /**
     * <p>滑动窗口: 使用数组作为滑动窗口</p>
     */
    public static String minWindowArray(String source, String target) {
        int start = 0;
        int minLength = 0;
        int valid = 0;
        int currentValid = 0;
        int[] count = new int[26];
        int[] windows = new int[26];
        for (int index = 0; index < target.length(); index++) {
            if (count[target.charAt(index)] == 0) {
                valid++;
            }
            count[target.charAt(index)]++;
        }
        for (int left = 0, right = 0; right < source.length(); right++) {
            int rch = source.charAt(right);
            if (count[rch] != 0) {
                windows[rch]++;
                if (windows[rch] == count[rch]) {
                    currentValid++;
                }
            }
            while (valid == currentValid) {
                if (minLength == 0 || minLength > right - left + 1) {
                    start = left;
                    minLength = right - left + 1;
                }
                int lch = source.charAt(left++);
                if (count[lch] != 0) {
                    if (windows[lch] == count[lch]) {
                        currentValid--;
                    }
                    windows[lch]--;
                }
            }
        }
        return source.substring(start, start + minLength);
    }

}
