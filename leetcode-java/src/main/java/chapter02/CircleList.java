package chapter02;

import utils.ListNode;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>环形链表</p>
 * <p>1. 环形链表</p>
 * <p>2. 环形链表 II</p>
 * <p>注: 这两个题思路基本没有区别</p>
 */
public class CircleList {

    /**
     * <p>思路: 哈希表</p>
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
     * <p>思路: 快慢指针</p>
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
