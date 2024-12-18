package main

import (
	"slices"
)

type ListSorter struct {}

// 排序链表: 直接调用排序方法
func (sorter *ListSorter) SortListApi(head *ListNode) *ListNode {
	current := head
	nodes := make([]*ListNode, 0)
	for current != nil {
		nodes = append(nodes, current)
		current = current.Next
	}
	slices.SortFunc(nodes, func(first, second *ListNode) int {
		return first.Val - second.Val
	})
	dummy := &ListNode{Val: 0}
	current = dummy
	for _, node := range nodes {
		node.Next = nil
		current.Next = node
		current = current.Next
	}
	return dummy.Next
}

// 排序链表: 迭代实现归并排序
func (sorter *ListSorter) SortList(head *ListNode) *ListNode {
	dummy := &ListNode{Val: 0, Next: head}
	var current *ListNode = dummy
	var previous *ListNode = dummy
	var next *ListNode = nil
	length := sorter.getLength(head)
	merger := &ListMerger{}
	for subLength := 1; subLength < length; subLength <<= 1 {
		previous = dummy
		current = dummy.Next
		for current != nil {
			firstHead := current
			for index := 1; index < subLength && current.Next != nil; index++ {
				current = current.Next
			}
			firstTail := current
			current = current.Next
			secondHead := current
			for index := 1; index < subLength && current != nil && current.Next != nil; index++ {
					current = current.Next
			}
			secondTail := current
			firstTail.Next = nil
			if secondTail != nil {
				next = secondTail.Next
				secondTail.Next = nil
			}
			previous.Next = merger.MergeTwoLists(firstHead, secondHead)
			for previous.Next != nil {
				previous = previous.Next
			}
			current = next
			next = nil
		}
	}
	return dummy.Next
}

func (sorter *ListSorter) getLength(head *ListNode) int {
	length := 0
	for head != nil {
		length++
		head = head.Next
	}
	return length
}
