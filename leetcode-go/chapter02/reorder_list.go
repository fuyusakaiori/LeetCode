package main

import "leetcode-go/node"

type ListReorder struct {}

// 重排链表
func (reorder *ListReorder) ReorderList(head *node.ListNode) *node.ListNode {
	slow, fast := head, head
	for fast != nil && fast.Next != nil {
		slow = slow.Next
		fast = fast.Next.Next
	}
	fast = head
	reverser := &ListReverser{}
	slow = reverser.ReverseListLoop(slow)
	for slow.Next != nil {
		fastNext := fast.Next
		slowNext := slow.Next
		fast.Next = slow
		slow.Next = fastNext
		fast = fastNext
		slow = slowNext
	}
	return head
}
