package main

import "leetcode-go/node"

type ListMerger struct {

}

// 合并两个有序链表
func (merger *ListMerger) MergeTwoLists(first, second *node.ListNode) *node.ListNode {
	dummy := &node.ListNode{Val: 0}
	current := dummy
	for first != nil && second != nil {
		if first.Val < second.Val {
			current.Next = first
			first = first.Next
		} else {
			current.Next = second
			second = second.Next
		}
		current = current.Next
	}
	if first != nil {
		current.Next = first
	}
	if second != nil {
		current.Next = second
	}
	return dummy.Next
}

// 合并 K 个有序链表
func (merger *ListMerger) MergeKLists(lists []*node.ListNode) *node.ListNode {
	if len(lists) == 0 {
		return nil
	}
	return merger.fork(lists, 0, len(lists) - 1)
}

func (merger *ListMerger) fork(lists []*node.ListNode, left, right int) *node.ListNode {
	if left >= right {
		return lists[left]
	}
	mid := left + ((right - left) >> 1)
	first := merger.fork(lists, left, mid)
	second := merger.fork(lists, mid + 1, right)
	return merger.MergeTwoLists(first, second)
}

