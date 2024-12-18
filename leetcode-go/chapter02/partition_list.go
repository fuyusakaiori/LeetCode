package main

import (
	"math"
)

type ListPartitioner struct {}

// 分隔链表
func (partitioner *ListPartitioner) Partition(head *ListNode, target int) *ListNode {
	current := head
	sDummy, bDummy := &ListNode{Val: 0}, &ListNode{Val: 0}
	sCurrent, bCurrent := sDummy, bDummy
	for current != nil {
		if current.Val < target {
			sCurrent.Next = current
			sCurrent = sCurrent.Next
		} else {
			bCurrent.Next = current
			bCurrent = bCurrent.Next
		}
		current = current.Next
	}
	bCurrent.Next = nil
	sCurrent.Next = bDummy.Next
	return sDummy.Next
}

func (partitioner *ListPartitioner) SplitListToParts(head *ListNode, k int) []*ListNode  {

}

// 分隔链表
func splitListToParts(head *ListNode, cnt int) []*ListNode {
	// 1. 遍历链表获取长度
	length := 0
	for current := head; current != nil; current = current.Next {
		length++
	}
	// 2. 遍历链表
	part, lists := 0, make([]*ListNode, cnt)
	for index, current := 0, head; index < cnt && current != nil; index++ {
		length = length - part
		part = int(math.Ceil(float64(length) / float64(cnt-index)))
		nHead := current
		for index := 0; index < part-1; index++ {
			current = current.Next
		}
		current, current.Next = current.Next, nil
		lists[index] = nHead
	}
	return lists
}
