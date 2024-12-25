package chapter02;

import utils.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>复制带随机指针的链表</p>
 */
public class RandomListCopy {

    /**
     * <p>思路: 哈希表实现</p>
     */
    public static ListNode copyComplexListHash(ListNode head) {
        ListNode current = head;
        Map<ListNode, ListNode> map = new HashMap<>();
        while (current != null) {
            map.put(current, new ListNode(current.value));
            current = current.next;
        }
        current = head;
        while (current != null) {
            map.get(current).next = map.get(current.next);
            map.get(current).random = map.get(current.random);
            current = current.next;
        }
        return map.get(head);
    }

    /**
     * <p>思路: 原链表中插入克隆结点, 复制完成后分离</p>
     */
    public static ListNode copyComplexList(ListNode head) {
        ListNode current = head;
        ListNode node = null;
        while (current != null) {
            node = new ListNode(current.value);
            node.next = current.next;
            current.next = node;
            current = current.next.next;
        }
        current = head;
        while (current != null) {
            if (current.random != null) {
                current.next.random = current.random.next;
            }
            current = current.next.next;
        }
        current = head;
        ListNode dummy = new ListNode(0);
        ListNode copy = dummy;
        while (current != null) {
            copy.next = current.next;
            copy = copy.next;
            current.next = current.next.next;
            current = current.next;
        }
        return dummy.next;
    }

}
