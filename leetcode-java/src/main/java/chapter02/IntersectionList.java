package chapter02;

import utils.ListNode;

import java.util.*;

/**
 * <p>链表相交</p>
 * <p>1. 相交链表: 链表中没有环, 判断是否相交</p>
 * <p>2. 相交链表 II: 链表存在环, 判断是否相交</p>
 */
public class IntersectionList {


    /**
     * <p>思路: 哈希表</p>
     */
    private static ListNode getIntersectionNodeHash(ListNode first, ListNode second){
        Set<ListNode> set = new HashSet<>();
        while (first != null) {
            set.add(first);
            first = first.next;
        }
        while (second != null) {
            if (set.contains(second)) {
                return second;
            }
            second = second.next;
        }
        return null;
    }

    /**
     * <p>思路: 先计算两个链表的长度, 然后让长的链表先走差值, 最后一起开始移动</p>
     */
    private static ListNode getIntersectionNode(ListNode first, ListNode second){
        int firstLength = getLength(first);
        int secondLength = getLength(second);
        int diffLength = Math.abs(firstLength - secondLength);
        for (int index = 0; index < diffLength; index++) {
            if (firstLength < secondLength) {
                second = second.next;
            } else {
                first = first.next;
            }
        }
        while (first != second) {
            if (first == null || second == null) {
                return null;
            }
            first = first.next;
            second = second.next;
        }
        return first;
    }

    private static int getLength(ListNode list){
        int length = 0;
        while (list != null && ++length > 0){
            list = list.next;
        }
        return length;
    }

    /**
     * <p>1. 两个链表都具有环, 但是不相交</p>
     * <p>2. 两个链表都有环, 且在入环之前相交</p>
     * <p>3. 两个链表都有环, 在入环之后相交</p>
     * <p>注: 不存在一个链表有环, 另外一个链表没环, 两个链表还相交的情况</p>
     */
    private static ListNode getIntersectionNodeLoop(ListNode l1, ListNode l2) {
        // 1. 如果没有环, 那么就调用刚才的方法去执行
        ListNode firstNode = CircleList.detectCycle(l1);
        ListNode secondNode = CircleList.detectCycle(l2);
        if (firstNode == null && secondNode == null){
            return getIntersectionNode(l1, l2);
        }else if (firstNode != null && secondNode != null){
            // 2. 两个链表都有环存在, 根据入环结点判断具体是哪种情况
            // 2.1 如果两个入环结点是相同的, 那么认为两个链表在进入环之前就相交了, 和没有坏一样
            if (firstNode == secondNode){
                return getIntersectionNode(l1, l2);
            }else{
                // 2.2 如果两个入环结点都不相同, 那么就需要判断是剩下两种情况的哪一种
                // 注: 做法就是让第一个入环结点一直遍历, 如果在回到自己之前能够遇见另外一个入环结点, 那么就是第三种情况
                ListNode current = firstNode.next;
                while (current != firstNode){
                    // 这时候第一个入环结点或者第二个入环结点都可以视为相交结点
                    if (current == secondNode)
                        return firstNode;
                    current = current.next;
                }
            }
        }
        // 3. 如果一个链表有环, 另外一个链表没环, 那么是不可能相交的
        return null;
    }

}
