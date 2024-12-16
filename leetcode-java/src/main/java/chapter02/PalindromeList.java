package chapter02;

import utils.ListNode;

import java.util.LinkedList;

/**
 * <h2>回文链表</h2>
 */
public class PalindromeList {

    /**
     * <h3>思路: 栈实现</h3>
     */
    public static boolean isPalindromeStack(ListNode head) {
        ListNode current = head;
        LinkedList<Integer> stack = new LinkedList<>();
        while (current != null) {
            stack.push(current.value);
            current = current.next;
        }
        current = head;
        while (current != null) {
            if (current.value != stack.pop()) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    /**
     * <h3>思路: 栈实现, 优化</h3>
     */
    public static boolean isPalindromeStackOptimize(ListNode head) {
        boolean flag = false;
        ListNode current = head;
        ListNode slow = head, fast = head;
        LinkedList<Integer> stack = new LinkedList<>();
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        while (current != slow) {
            stack.push(current.value);
            current = current.next;
        }
        if (flag) {
            stack.push(current.value);
        }
        while (current != null) {
            if (!stack.isEmpty() && current.value != stack.pop()) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    /**
     * <h3>思路：</h3>
     * <h3>1. 快慢指针找到中点, 然后从中点开始逆序直到结尾</h3>
     * <h3>2. 快指针和慢指针两个分别从头和尾开始遍历, 直到中点</h3>
     */
    public static boolean isPalindrome(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        // 快慢指针找中点
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode first = head;
        ListNode second = ReverseList.reverseListLoop(slow);
        while (second != null) {
            if (first.value != second.value) {
                return false;
            }
            first = first.next;
            second = second.next;
        }
        return true;
    }

}
