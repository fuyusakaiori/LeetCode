package chapter02;

import utils.ListNode;
import utils.RandomUtil;

import java.util.*;

/**
 * <p>排序链表</p>
 */
public class SortList {

    /**
     * <p>思路: 调用集合排序的方法</p>
     */
    public static ListNode sortListApi(ListNode head) {
        ListNode current = head;
        List<ListNode> nodes = new ArrayList<>();
        while (current != null) {
            nodes.add(current);
            current = current.next;
        }
        nodes.sort((first, second) -> first.value - second.value);
        ListNode dummy = new ListNode(0);
        current = dummy;
        for (ListNode node : nodes) {
            node.next = null;
            current.next = node;
            current = current.next;
        }
        return dummy.next;
    }

    /**
     * <p>思路: 自底向上的归并排序</p>
     * <p>1. 最初每个分区仅有一个结点, 然后两两合并成有两个结点的单元, 依次类推</p>
     * <p>2. 每轮合并的次数取决于分区的数量</p>
     */
    public static ListNode sortListMerge(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy;
        ListNode previous = current;
        ListNode next = null;
        int length = getLength(head);
        for (int subLength = 1; subLength < length; subLength <<= 1) {
            previous = dummy;
            current = dummy.next;
            while (current != null) {
                ListNode firstHead = current;
                for (int index = 1; index < subLength && current.next != null; index++) {
                    current = current.next;
                }
                ListNode firstTail = current;
                current = current.next;
                ListNode secondHead = current;
                for (int index = 1; index < subLength && current != null && current.next != null; index++) {
                    current = current.next;
                }
                ListNode secondTail = current;
                firstTail.next = null;
                if (secondTail != null) {
                    next = secondTail.next;
                    secondTail.next = null;
                }
                previous.next = MergeList.mergeTwoSortedLists(firstHead, secondHead);
                while (previous.next != null) {
                    previous = previous.next;
                }
                current = next;
                next = null;
            }
        }
        return dummy.next;
    }

    private static int getLength(ListNode head){
        int length = 0;
        while (head != null && ++length > 0){
            head = head.next;
        }
        return length;
    }


}

