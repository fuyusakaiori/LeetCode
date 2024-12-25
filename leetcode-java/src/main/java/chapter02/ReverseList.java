package chapter02;

import utils.ListNode;

/**
 * <p>反转链表</p>
 * <p>1.反转链表</p>
 * <p>2.反转链表 II</p>
 * <p>3.两两交换链表中的结点</p>
 * <p>4.K个一组反转</p>
 * <p>万能解法: 集合、数组、栈都是可以完成的</p>
 */
public class ReverseList {

    /**
     * <p>思路: 反转链表 迭代</p>
     */
    public static ListNode reverseListLoop(ListNode head){
        ListNode previous = null;
        ListNode current = head;
        ListNode next = null;
        while (current != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    /**
     * <p>思路: 反转链表 递归</p>
     */
    public static ListNode reverseListRecursive(ListNode head){
        if (head == null || head.next == null) {
            return head;
        }
        ListNode tail = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return tail;
    }

    /**
     * <p>思路: 反转链表 II</p>
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy, start = null, end = null;
        ListNode leftNode = null, rightNode = null;
        int index = 0;
        for (index = 0; index < left - 1; index++) {
            current = current.next;
        }
        leftNode = current;
        for (index = left; index < right + 1; index++) {
            current = current.next;
        }
        rightNode = current;
        start = leftNode.next;
        end = rightNode.next;
        leftNode.next = null;
        rightNode.next = null;
        reverseListLoop(start);
        leftNode.next = rightNode;
        start.next = end;
        return dummy.next;
    }

    /**
     * <p>思路: K个一组反转链表 <=> 两两交换链表中的结点</p>
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0 ,head);
        ListNode current = head, previous = dummy, start = null, end = null;
        while (current != null) {
            for (int index = 0; index < k - 1; index++) {
                current = current.next;
                if (current == null) {
                    return dummy.next;
                }
            }
            start = previous.next;
            end = current.next;
            previous.next = null;
            current.next = null;
            reverseListLoop(start);
            previous.next = current;
            start.next = end;
            previous = start;
            current = end;
        }
        return dummy.next;
    }
}
