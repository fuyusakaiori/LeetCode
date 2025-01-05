package chapter08;

import java.util.*;

/**
 * <p>电话号码的字母组合</p>
 */
public class LetterCombinations {

    /**
     * <p>回溯</p>
     */
    public static List<String> letterCombinations(String digits) {
        Map<Character, String> map = new HashMap<>(){{
            put('2', "abc");put('3', "def");put('4', "ghi");
            put('5', "jkl");put('6', "mno");put('7', "pqrs");
            put('8', "tuv");put('9', "wxyz");
        }};
        String combination = new String();
        List<String> combinations = new ArrayList<>();
        letterCombinations(0, digits, combination, combinations, map);
        return combinations;
    }

    private static void letterCombinations(int index, String digits, String combination, List<String> combinations, Map<Character, String> map) {
        if (index == digits.length()) {
            combinations.add(combination);
            return;
        }
        char digit = digits.charAt(index);
        String chars = map.getOrDefault(digit, "");
        for (int start = 0; start < chars.length(); start++) {
            letterCombinations(index + 1, digits, combination + chars.charAt(start), combinations, map);
        }
    }


}
