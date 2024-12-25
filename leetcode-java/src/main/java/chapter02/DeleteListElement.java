package chapter02;

import utils.ListNode;

import java.util.LinkedList;

/**
 * <p>删除链表元素问题</p>
 * <p>1. 删除链表中的重复元素</p>
 * <p>2. 删除链表中的重复元素 II</p>
 * <p>3. 删除链表倒数第 N 个结点</p>
 * <p>4. 移除链表元素</p>
 * <p>5. 删除链表中的结点</p>
 * <p>注: 剩下两个题比较简单, 没有太多价值</p>
 */
public class DeleteListElement {

    /**
     * 删除链表中的重复元素: 保留一个重复的元素
     */
    public static class DeleteDuplicateI {

        public static ListNode deleteDuplicatesPrevious(ListNode head){
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

        public static ListNode deleteDuplicates(ListNode head){
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
    }

    /**
     * 删除链表中的重复元素 II: 不保留任何重复的元素
     */
    public static class DeleteDuplicateII {

        private static ListNode deleteDuplicatesPrevious(ListNode head){
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

        public static ListNode deleteDuplicates(ListNode head){
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

    }

    /**
     * 删除链表的倒数第 N 个节点
     */
    public static class RemoveNthFromEnd {

        /**
         * 思路: 计算链表长度
         */
        public static ListNode removeNthFromEndLength(ListNode head, int nth){
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
         * 思路: 双指针
         */
        public static ListNode removeNthFromEnd(ListNode head, int nth){
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

    /**
     * 移除链表元素
     */
    public static class RemoveElements {

        public ListNode removeElements(ListNode head, int target) {
            ListNode dummy = new ListNode(0, head);
            ListNode current = dummy;
            while (current.next != null) {
                if (current.next.value == target) {
                    current.next = current.next.next;
                } else {
                    current = current.next;
                }
            }
            return dummy.next;
        }

    }

    /**
     * 删除链表中的节点
     */
    public static class DeleteNode {

        /**
         * 脑筋急转弯
         */
        public void deleteNode(ListNode node) {
            node.value = node.next.value;
            node.next = node.next.next;
        }

    }

}
