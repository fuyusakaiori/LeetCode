package chapter08;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>括号的生成</p>
 */
public class GenerateParenthesis {

    public static List<String> generateParenthesis(int count) {
        List<String> brackets = new ArrayList<>();
        generateParenthesis(count, count, "", brackets);
        return brackets;
    }

    public static void generateParenthesis(int left, int right, String bracket, List<String> brackets) {
        if (left == 0 && right == 0) {
            brackets.add(bracket);
            return;
        }
        if (left < 0 || right < 0) {
            return;
        }
        generateParenthesis(left - 1, right, bracket + "(", brackets);
        if (right > left) {
            generateParenthesis(left, right - 1, bracket + ")", brackets);
        }
    }

}
