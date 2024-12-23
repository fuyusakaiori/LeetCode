package main

import "leetcode-go/node"

type ListRotator struct {}

// 旋转链表
func (rotator *ListRotator) RotateRightLength(head *node.ListNode, k int) *node.ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	length := rotator.getLength(head)
	offset := k % length
	dummy := &node.ListNode{Val: 0}
	current := head
	for index := 0; index < length - offset - 1; index++ {
		current = current.Next
	}
	start := current
	for index := length - offset - 1; index < length - 1; index++ {
		current = current.Next
	}
	end := current
	// TODO: 必须先将最后的节点指向头结点, 否则无法兼容 k % length == 0 的情况
	end.Next = head
	dummy.Next = start.Next
	start.Next = nil
	return dummy.Next
}

// 旋转链表 => 删除倒数第 N 个节点
func (rotator *ListRotator) RotateRight(head *node.ListNode, k int) *node.ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	first, second := head, head
	offset := k % rotator.getLength(head)
	for index := 0; index < offset; index++ {
		first = first.Next
	}
	for first.Next != nil {
		first = first.Next
		second = second.Next
	}
	dummy := &node.ListNode{Val: 0}
	first.Next = head
	dummy.Next = second.Next
	second.Next = nil
	return dummy.Next
}

func (rotator *ListRotator) getLength(head *node.ListNode) int {
	length := 0
	for head != nil {
		length++
		head = head.Next
	}
	return length
}

// 旋转链表: 类似于删除倒数第 N 个节点
func rotateRight(head *node.ListNode, k int) *node.ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	// 1. 获取链表长度
	length := 0
	for current := head; current != nil; current = current.Next {
		length++
	}
	// 2. 取模
	k = k % length
	if k == 0 {
		return head
	}
	// 3. 遍历链表
	slow, fast := head, head
	for index := 0; index < k; index++ {
		fast = fast.Next
	}
	for fast.Next != nil {
		slow, fast = slow.Next, fast.Next
	}
	// 4. 拆分链表
	nHead := slow.Next
	slow.Next, fast.Next = nil, head
	return nHead
}
