package chapter07.optimum.array;

import java.util.Arrays;

/**
 * <p>可获得的最大点数</p>
 */
public class MaxScore {

    /**
     * <p>滑动窗口</p>
     * <p>1. 因为滑动窗口只能对连续的子串使用, 所以在不连续的情况下就需要考虑如何转换成连续的</p>
     * <p>2. 因为最大值是从两侧获取的, 是不连续的, 反过来想, 最小值是从中间获取的, 一定是连续的</p>
     * <p>3. 最后就转换成对最小值使用滑动窗口, 而不是最大值</p>
     */
    public static int maxScore(int[] cardPoints, int count) {
        // 1. 滑动窗口: 不需要使用任何数据结构
        int curValue = 0;
        // 2. 初始化滑动窗口
        for (int index = 0; index < cardPoints.length - count; index++) {
            curValue += cardPoints[index];
        }
        int minValue = curValue;
        // 3. 滑动窗口左右指针
        for (int left = 0, right = cardPoints.length - count; right < cardPoints.length; right++) {
            curValue += cardPoints[right];
            curValue -= cardPoints[left++];
            minValue = Math.min(minValue, curValue);
        }
        return Arrays.stream(cardPoints).sum() - minValue;
    }

}
