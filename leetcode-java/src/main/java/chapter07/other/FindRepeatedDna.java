package chapter07.other;

import java.util.*;

/**
 * <p>重复的 DNA 序列</p>
 */
public class FindRepeatedDna {

    /**
     * <p>滑动窗口: 固定窗口大小</p>
     */
    public static List<String> findRepeatedDnaSequences(String source) {
        List<String> sequences = new ArrayList<>();
        // 1. 滑动窗口: 记录字符串的次数
        Map<String, Integer> windows = new HashMap<>();
        // 2. 滑动窗口左右指针
        for (int left = 0, right = 10; right <= source.length(); right++) {
            String sequence = source.substring(left++, right);
            // NOTE: 滑动窗口记录次数的目的是为了去重
            if (windows.containsKey(sequence) && windows.get(sequence) == 1) {
                sequences.add(sequence);
            }
            windows.put(sequence, windows.getOrDefault(sequence, 0) + 1);
        }
        return sequences;
    }

    private static final int offsetConstant = 131313;

    /**
     * <p>字符串哈希表 + 前缀和</p>
     */
    public static List<String> findRepeatedDnaSequences2(int cnt, String s){
        List<String> result = new LinkedList<>();
        // 1. 前缀和
        int[] prefixSum = new int[s.length() + 1];
        int[] offset = new int[s.length() + 1];
        offset[0] = 1;
        // 2. 初始化
        for(int idx = 1;idx <= s.length();idx++){
            prefixSum[idx] = prefixSum[idx - 1] * offsetConstant + s.charAt(idx - 1);
            offset[idx] = offset[idx - 1] * offsetConstant;
        }
        // 3. 滑动窗口
        Map<Integer, Integer> window = new HashMap<>();
        // 4. 移动窗口
        for(int idx = 1;idx + cnt - 1 <= s.length();idx++){
            int hash = prefixSum[idx + cnt - 1] - prefixSum[idx - 1] * offset[cnt];
            int frequency = window.getOrDefault(hash, 0);
            if(frequency == 1)
                result.add(s.substring(idx - 1, idx + cnt - 1));
            window.put(hash, frequency + 1);
        }
        return result;
    }
}
