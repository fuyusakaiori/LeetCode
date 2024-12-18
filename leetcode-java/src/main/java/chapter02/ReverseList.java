package chapter02;

import utils.ListNode;

/**
 * <h2>反转链表</h2>
 * <h3>1.反转链表</h3>
 * <h3>2.反转链表 II</h3>
 * <h3>3.两两交换链表中的结点</h3>
 * <h3>4.K个一组反转</h3>
 * <p>万能解法: 集合、数组、栈都是可以完成的</p>
 */
public class ReverseList {

    /**
     * <h3>思路: 反转链表 迭代</h3>
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
     * <h3>思路: 反转链表 递归</h3>
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
     * <h3>思路: 反转链表 II</h3>
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
     * <h3>思路: K个一组反转链表 <=> 两两交换链表中的结点</h3>
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
