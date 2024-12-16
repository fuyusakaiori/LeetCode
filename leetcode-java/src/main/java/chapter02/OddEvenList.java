package chapter02;

import utils.ListNode;

/**
 * <h2>奇偶链表</h2>
 * <p>问题描述: 让结点编号为奇数的指向奇数, 为偶数的指向偶数</p>
 */
public class OddEvenList {

    /**
     * <h3>思路: 采用双指针也就是分割链表的解法</h3>
     */
    private static ListNode oddEvenListIndex(ListNode head){
        if (head == null || head.next == null) {
            return head;
        }
        boolean flag = true;
        ListNode current = head;
        ListNode oDummy = new ListNode(0, head), oCurrent = oDummy;
        ListNode eDummy = new ListNode(0, head.next), eCurrent = eDummy;
        while (current != null) {
            if (flag) {
                oCurrent.next = current;
                oCurrent = oCurrent.next;
            } else {
                eCurrent.next = current;
                eCurrent = eCurrent.next;
            }
            flag = !flag;
            current = current.next;
        }
        eCurrent.next = null;
        oCurrent.next = eDummy.next;
        return oDummy.next;
    }

    /**
     * <h3>思路: 模拟</h3>
     */
    public static ListNode oddEvenList(ListNode head){
        if (head == null || head.next == null) {
            return head;
        }
        ListNode odd = head;
        ListNode even = head.next;
        ListNode link = even;
        while (even != null) {
            if (odd.next.next == null) {
                break;
            }
            odd.next = odd.next.next;
            even.next = even.next.next;
            odd = odd.next;
            even = even.next;
        }

        odd.next = link;
        return head;
    }
}
