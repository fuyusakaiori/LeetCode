package chapter02;

import utils.ListNode;

/**
 * 旋转链表
 */
public class RotationList {

    /**
     * <p>思路: </p>
     * <p>1. 利用快慢指针找到需要旋转的部分的头结点, 然后记录头结点, 作为新的头结点</p>
     * <p>2. 然后继续遍历找到的尾结点, 然后将尾结点连接到旧的头结点, 最后返回新的头结点</p>
     */
    private static ListNode rotateList(ListNode head, int k){
        if (head == null || head.next == null) {
            return head;
        }
        int length = getLength(head);
        int offset = k % length;
        ListNode dummy = new ListNode(0);
        ListNode current = head, start = null, end = null;
        for (int index = 0; index < length - offset - 1; index++) {
            current = current.next;
        }
        end = current;
        for (int index = length - offset - 1; index < length - 1; index++) {
            current = current.next;
        }
        start = current;
        start.next = head;
        dummy.next = end.next;
        end.next = null;
        return dummy.next;
    }

    private static int getLength(ListNode head){
        int length = 0;
        while(head != null && ++length > 0){
            head = head.next;
        }
        return length;
    }
}
