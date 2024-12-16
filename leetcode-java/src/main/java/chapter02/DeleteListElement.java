package chapter02;

import utils.ListNode;

import java.util.LinkedList;

/**
 * <h2>删除链表元素问题</h2>
 * <h3>1. 删除链表中的重复元素</h3>
 * <h3>2. 删除链表中的重复元素 II</h3>
 * <h3>3. 删除链表倒数第 N 个结点</h3>
 * <h3>4. 移除链表元素</h3>
 * <h3>5. 删除链表中的结点</h3>
 * <h3>注: 剩下两个题比较简单, 没有太多价值</h3>
 */
public class DeleteListElement {

    /**
     * <h3>思路: 删除排序链表中的重复元素</h3>
     */
    private static ListNode deleteDuplicates1(ListNode head){
        ListNode current = head;
        ListNode previous = new ListNode(Integer.MIN_VALUE, head);
        while (current != null){
            if (current.value != previous.value){
                previous.next = current;
                previous = current;
            }
            current = current.next;
        }
        previous.next = null;
        return head;
    }

    private static ListNode deleteDuplicates2(ListNode head){
        ListNode current = head;
        while (current != null && current.next != null){
            if (current.value == current.next.value){
                current.next = current.next.next;
            }else{
                current = current.next;
            }
        }
        return head;
    }

    /**
     * <h3>思路: 删除链表中的重复元素 II</h3>
     */
    private static ListNode deleteDuplicates3(ListNode head){
        // 注: 防止要删除的结点就是头结点
        ListNode dummy = new ListNode(0, head);
        ListNode previous = dummy, current = head;
        // 1. 这里必须保证当前结点和下个结点都不为空
        while (current != null && current.next != null){
            // 2. 如果发现重复结点, 就转换为 删除链表中的重复元素 这个题的做法
            if (current.value != current.next.value){
                ListNode start = current;
                // 3. 不断循环遍历直到找到不相等的结点
                while (current != null && current.value == start.value){
                    current = current.next;
                }
                // 4. 删除重复的结点
                previous.next = current;
                continue;
            }
            // 5. 如果没有重复直接向后移动
            previous = current;
            current = current.next;
        }

        return dummy.next;
    }

    private static ListNode deleteDuplicates4(ListNode head){
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy;
        while (current.next != null && current.next.next != null){
            if (current.next.value == current.next.next.value){
                int value = current.next.value;
                while (current.next != null && current.next.value == value){
                    current.next = current.next.next;
                }
            }else{
                current = current.next;
            }
        }
        return dummy.next;
    }

    /**
     * 思路: 删除链表倒数第 N 个结点, 栈
     */
    private static ListNode removeNthFromEndStack(ListNode head, int nth){
        LinkedList<ListNode> stack = new LinkedList<>();
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy;
        while (current != null){
            stack.push(current);
            current = current.next;
        }
        while (nth-- >= 0){
            current = stack.pop();
        }
        assert current != null;
        current.next = current.next.next;
        return dummy.next;
    }

    /**
     * <h3>思路: 计算链表长度</h3>
     */
    private static ListNode removeNthFromEnd1(ListNode head, int nth){
        int length = getLength(head);
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy;
        for (int index = 0; index < length - nth; index++) {
            current = current.next;
        }
        current.next = current.next.next;
        return dummy.next;
    }

    private static int getLength(ListNode list) {
        int length = 0;
        while(list != null && ++length > 0){
            list = list.next;
        }
        return length;
    }

    /**
     * <h3>思路: 双指针</h3>
     */
    private static ListNode removeNthFromEnd3(ListNode head, int nth){
        ListNode dummy = new ListNode(0, head);
        ListNode first = dummy, second = head;
        while (nth-- > 0) {
            second = second.next;
        }
        while (second != null) {
            first = first.next;
            second = second.next;
        }
        first.next = first.next.next;
        return dummy.next;
    }
}
