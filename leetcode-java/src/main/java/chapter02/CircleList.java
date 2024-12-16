package chapter02;

import utils.ListNode;
import java.util.Set;
import java.util.HashSet;

/**
 * <h2>环形链表</h2>
 * <h3>1. 环形链表</h3>
 * <h3>2. 环形链表 II</h3>
 * <h3>注: 这两个题思路基本没有区别</h3>
 */
public class CircleList {

    /**
     * <h3>思路: 环形链表 II</h3>
     */
    private static ListNode detectCycleHashSet(ListNode head){
        ListNode current = head;
        Set<ListNode> set = new HashSet<>();
        while (current != null) {
            if (!set.add(current)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * <h3>思路: 环形链表 II</h3>
     */
    public static ListNode detectCycle(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        do {
            if (fast == null || fast.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        } while (slow != fast);
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
