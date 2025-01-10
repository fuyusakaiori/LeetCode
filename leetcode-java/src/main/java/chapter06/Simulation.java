package chapter06;


/**
 * <h3>====计算模拟====</h3>
 * <h3>6. 字符串相乘 & 字符串相加 & 二进制求和</h3>
 * <h3>====字符串模拟====</h3>
 * <h3>注: 所有字符串模拟的题目放在第九章中</h3>
 * <h3>====其他模拟====</h3>
 * <h3>7. 提莫攻击</h3>
 * <h3>8. 比较含退格的字符串</h3>
 * <h2>总结</h2>
 * <h3>1. 模拟就是按照题目的意思, 去模仿题目的行为</h3>
 * <h3>2. 如果模拟题和矩阵有关, 那么可能会使用到深度搜索或者广度搜索的技巧, 不过本质还是模拟</h3>
 * <h3>3. 因为绝大多数的矩阵题目都是双重循环套深度搜索, 没有太多优化技巧可言, 所以说本质还是模拟</h3>
 */
public class Simulation {


    /**
     * <h3>思路: 字符串相加 (大数相加)</h3>
     * <h3>注: 模拟就行, 没有其他做法, 不过可以简化代码</h3>
     */
    private static String addStrings(String first, String second){
        int carry = 0;
        int sum = 0;
        int firstIndex = first.length() - 1;
        int secondIndex = second.length() - 1;
        StringBuilder result = new StringBuilder();
        while (firstIndex >= 0 || secondIndex >= 0 || carry != 0){
            int firstNum = firstIndex >= 0 ? first.charAt(firstIndex--) - '0' : 0;
            int secondNum = secondIndex >= 0 ? second.charAt(secondIndex--) - '0' : 0;
            sum = firstNum + secondNum + carry;
            result.insert(0, sum % 10);
            carry = sum / 10;
        }
        return result.toString();
    }

    /**
     * <h3>思路: 字符串相乘 (大数相乘)</h3>
     * <h3>注: 可以优化</h3>
     */
    private static String multiplyString(String first, String second){
        String longStr = first.length() <= second.length() ? second: first;
        String shortStr = first.length() <= second.length() ? first: second;
        if("0".equals(shortStr))
            return "0";
        String result = "";
        for(int index = shortStr.length() - 1;index >= 0;index--){
            String add = multiplyStrings(longStr, shortStr.charAt(index));
            result = addStrings(result, setZero(add, shortStr.length() - 1 - index));
        }
        return result;
    }

    private static String setZero(String str, int index){
        StringBuilder sb = new StringBuilder(str);
        for(int count = 0;count < index;count++){
            sb.append("0");
        }
        return sb.toString();
    }

    private static String multiplyStrings(String first, char second){
        int carry = 0;
        int result = 0;
        int number = second - '0';
        StringBuilder sb = new StringBuilder();
        for(int index = first.length() - 1;index >= 0;index--){
            result = number * (first.charAt(index) - '0') + carry;
            sb.insert(0, result % 10);
            carry = result / 10;
        }
        if(carry != 0) sb.insert(0, carry);
        return sb.toString();
    }


}
