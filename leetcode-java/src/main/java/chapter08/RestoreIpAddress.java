package chapter08;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>复原 IP 地址</p>
 */
public class RestoreIpAddress {

    /**
     * <p>回溯</p>
     * <p>1. 虽然思路非常直观, 但是有不少的边界条件需要处理</p>
     * <p>因为需要对字符串进行截取, 所以要考虑索引是否越界</p>
     * <p>因为分段不能有前导 0, 所以需要单独处理前导 0 的情况</p>
     * <P>因为分段必须是有效的, 所以还需要判断分段是否在合法范围内</P>
     * <p>2. 虽然理论上不采用内部循环也是可以实现的, 但是实际实现起来感觉非常费劲,</p>
     */
    public static List<String> restoreIpAddressesTest(String source) {
        List<String> segments = new ArrayList<>();
        List<String> ips = new ArrayList<>();
        restoreIpAddresses(0, source, segments, ips);
        return ips;
    }

    public static void restoreIpAddresses(int start, String source, List<String> segments, List<String> ips) {
        if (segments.size() == 4) {
            // NOTE: 因为字符串的所有字符都需要被使用, 所以还需要确保指针已经移动到末尾
            if (start == source.length()) {
                ips.add(String.join(".", segments));
            }
            return;
        }
        if (start == source.length()) {
            return;
        }
        // NOTE: 默认分段长度最多为 3; 如果字符串剩余长度不满足要求, 那么就只能以剩余长度作为截取长度
        int segmentLength = Math.min(3, source.length() - start);
        // NOTE: 如果存在前导 0, 那么分段长度只能是 1
        segmentLength = source.charAt(start) == '0' ? 1 : segmentLength;
        // NOTE: 开始遍历截取字符串
        for (int index = start; index < segmentLength; index++) {
            String segment = source.substring(start, index + 1);
            // NOTE: 判断分段是否有效
            if (Integer.parseInt(segment) > 255) {
                continue;
            }
            segments.add(segment);
            restoreIpAddresses(index + 1, source, segments, ips);
            segments.remove(segments.size() - 1);
        }
    }

}
