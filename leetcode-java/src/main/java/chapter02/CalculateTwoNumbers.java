package chapter02;

import utils.ListNode;

import java.io.File;
import java.util.LinkedList;

/**
 * <h2>两数相加</h2>
 * <h3>1. 两数相加: 链表采用逆序的方式存储数字</h3>
 * <h3>2. 两数相加 II: 链表采用顺序的方式存储数字</h3>
 */
public class CalculateTwoNumbers {

    /**
     * <h3>两数之和: 模拟</h3>
     */
    public static ListNode addTwoNumbers(ListNode first, ListNode second) {
        int sum = 0, carry = 0;
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (first != null || second != null) {
            int firstValue = first != null ? first.value: 0;
            int secondValue = second != null ? second.value: 0;
            sum = (firstValue + secondValue + carry) % 10;
            carry = (firstValue + secondValue + carry) / 10;
            current.next = new ListNode(sum);
            current = current.next;
            if (first != null) {
                first = first.next;
            }
            if (second != null) {
                second = second.next;
            }
        }
        if (carry != 0) {
            current.next = new ListNode(carry);
        }
        return dummy.next;
    }

    /**
     * 两数之和 II: 模拟
     */
    public static ListNode addTwoNumbersReverse(ListNode first, ListNode second) {
        int sum = 0, carry = 0;
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        first = ReverseList.reverseListLoop(first);
        second = ReverseList.reverseListLoop(second);
        return ReverseList.reverseListLoop(addTwoNumbers(first, second));
    }

    /**
     * 两数之和 II: 栈
     */
    private static ListNode addTwoNumbersByStack(ListNode first, ListNode second){
        LinkedList<Integer> firstStack = new LinkedList<>();
        LinkedList<Integer> secondStack = new LinkedList<>();
        // 压栈
        while (first != null){
            firstStack.push(first.value);
            first = first.next;
        }
        while (second != null){
            secondStack.push(second.value);
            second = second.next;
        }
        // 模拟计算
        int carry = 0;
        int result = 0;
        ListNode node = null;
        ListNode dummy = null;
        while (!firstStack.isEmpty() || !secondStack.isEmpty() || carry != 0){
            int firstValue = firstStack.isEmpty() ? 0: firstStack.pop();
            int secondValue = secondStack.isEmpty() ? 0: secondStack.pop();
            result = firstValue + secondValue + carry;
            carry = result / 10;
            node = new ListNode(result % 10);
            // 头插法
            node.next = dummy;
            dummy = node;
        }
        return dummy;
    }

}
