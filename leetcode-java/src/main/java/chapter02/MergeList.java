package chapter02;

import utils.ListNode;

/**
 * <p>合并链表</p>
 * <p>1. 合并两个有序链表</p>
 * <p>2. 合并两个普通的链表</p>
 * <p>3. 合并 K 个升序链表</p>
 */
public class MergeList {

    /**
     * <p>思路: 哑元 + 比大小</p>
     */
    public static ListNode mergeTwoSortedLists(ListNode list1, ListNode list2){
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (list1 != null && list2 != null) {
            if (list1.value < list2.value) {
                current.next = list1;
                list1 = list1.next;
            } else{
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        if (list1 != null) {
            current.next = list1;
        }
        if (list2 != null) {
            current.next = list2;
        }
        return dummy.next;
    }

    /**
     * <p>思路: 找到合并链表的起始位置和终止位置就行</p>
     */
    public static ListNode mergeBetweenLists(ListNode list1, ListNode list2, int left, int right){
        ListNode dummy = new ListNode(0, list1);
        ListNode current = dummy, tail = list2;
        ListNode leftNode = null, rightNode = null;
        int index = 0;
        for (index = 0; index < left; index++) {
            current = current.next;
        }
        leftNode = current;
        for (index = left; index < right + 1; index++) {
            current = current.next;
        }
        rightNode = current.next;
        while (tail.next != null) {
            tail = tail.next;
        }
        leftNode.next = list2;
        tail.next = rightNode;
        return dummy.next;
    }

    /**
     * <p> 思路: 合并 K 个有序链表</p>
     */
    public static ListNode mergeKLists(ListNode[] lists){
        if (lists.length == 0) {
            return null;
        }
        return fork(lists, 0, lists.length - 1);
    }

    private static ListNode fork(ListNode[] lists, int left, int right){
        if (left >= right) {
            return lists[left];
        }
        int mid = left + ((right - left) >> 1);
        ListNode first = fork(lists, left, mid);
        ListNode second = fork(lists, mid + 1, right);
        return mergeTwoSortedLists(first, second);
    }


}
