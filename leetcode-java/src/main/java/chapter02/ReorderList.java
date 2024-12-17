package chapter02;

import utils.ListNode;

/**
 * <h2>重排链表</h2>
 */
public class ReorderList {


    /**
     * <h3>思路: 找中点 + 逆序 + 衔接</h3>
     * <h3>注: 几乎考察了链表常用的几个技巧</h3>
     */
    private static ListNode reorderList(ListNode head){
        ListNode next = null;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        slow = ReverseList.reverseListLoop(slow);
        while (slow.next != null) {
            next = fast.next;
            fast.next = slow;
            fast = next;
            next = slow.next;
            slow.next = fast;
            slow = next;
        }
        return head;
    }
}
