package chapter07.count.string;

import java.util.*;

/**
 * <p>串联所有单词的子串</p>
 */
public class ConcatenateWordSubstring {

    /**
     * <p>滑动窗口: 固定窗口大小; 不在外初始化; 固定模版</p>
     */
    public List<Integer> findSubstringSimple(String source, String[] words) {
        int wordLength = words[0].length();
        List<Integer> substrings = new ArrayList<>();
        // 1. 初始化目标字符串数组中的字符串数量
        Map<String, Integer> map = new HashMap<>();
        for (int index = 0; index < words.length; index++) {
            map.put(words[index], map.getOrDefault(words[index], 0) + 1);
        }
        // 2. 需要以前 wordLength 个字符分别为起点执行滑动窗口
        // NOTE: 如果从 [0] 的位置开始移动滑动窗口, 那么滑动窗口中间就会从 [wordLength] 的位置开始移动
        // NOTE: 所以选择滑动窗口的起点时, 没有必要再单独以 [wordLength] 为起点开始移动
        for (int index = 0; index < wordLength; index++) {
            int valid = 0;
            Map<String, Integer> windows = new HashMap<>();
            // NOTE: 因为截取字符串时可能越界, 所以终止条件需要加上单词的长度; 因为截取字符串时左闭右开, 所以需要小于等于
            for (int left = index, right = index; right + wordLength <= source.length(); right += wordLength) {
                String rword = source.substring(right, right + wordLength);
                if (map.containsKey(rword)) {
                    windows.put(rword, windows.getOrDefault(rword, 0) + 1);
                    if (windows.get(rword).intValue() == map.get(rword).intValue()) {
                        valid++;
                    }
                }
                if (right - left + wordLength == wordLength * words.length) {
                    if (valid == map.size()) {
                        substrings.add(left);
                    }
                    String lword = source.substring(left, left + wordLength);
                    left += wordLength;
                    if (map.containsKey(lword)) {
                        if (windows.get(lword).intValue() == map.get(lword).intValue()) {
                            valid--;
                        }
                        windows.put(lword, windows.get(lword) - 1);
                    }
                }
            }
        }
        return substrings;
    }

}
