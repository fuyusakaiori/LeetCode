package chapter02;


import utils.ListNode;
import utils.TestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>链表分区</p>
 * <p>1. 分隔链表: 分隔成两个部分; 或者分隔成三个部分</p>
 * <p>2. 分隔链表: 分隔成指定数量部分</p>
 */
public class ListPartition {

    /**
     * <p>思路: 每个区域都用头尾指针记录, 然后将每个区域串联起来</p>
     */
    private static ListNode partition(ListNode head, int target) {
        if (head == null || head.next == null)
            return head;
        // 1. 准备六个变量: 左分区的头尾指针, 中间分区的头尾指针, 右分区的头尾指针
        ListNode current = head;
        ListNode sDummy = new ListNode(0), sCurrent = sDummy;
        ListNode eDummy = new ListNode(0), eCurrent = eDummy;
        ListNode bDummy = new ListNode(0), bCurrent = bDummy;
        // 2. 开始遍历链表
        while (current != null){
            if (current.value < target){
                sCurrent.next = current;
                sCurrent = sCurrent.next;
            }else if (current.value > target){
                bCurrent.next = current;
                bCurrent = bCurrent.next;
            }else {
                eCurrent.next = current;
                eCurrent = eCurrent.next;
            }
            current = current.next;
        }
        // 只要使用哑元, 就可以完全避免空指针异常
        bCurrent.next = null;
        sCurrent.next = eDummy.next;
        eCurrent.next = bDummy.next;
        return sDummy.next;
    }

    /**
     * <p></p>
     */
    private static ListNode[] splitList(ListNode head, int k){
        if(head == null)
            return new ListNode[k];
        int length = 0;
        ListNode current = head;
        // 1. 计算长度
        while (current != null && ++length > 0)current = current.next;
        // 2. 开始遍历分割
        int index = 0;
        // 2.1 每个部分多少个结点
        int numbers = length % k == 0 ? length / k : length / k + 1;
        int counts = numbers;
        current = head;
        ListNode next = head.next;
        ListNode [] parts = new ListNode[k];
        while (current != null){
            if (numbers == 1){
                parts[index++] = head;
                head = current.next;
                current.next = null;
                length -= counts;
                if(k != 1){
                    numbers = length % (--k) == 0 ? length / k : length / k + 1;
                    counts = numbers;
                }
            }else{
                numbers--;
            }
            current = next;
            next = next != null ? next.next: null;
        }
        if(head != null)
            parts[index] = head;
        return parts;
    }
}
