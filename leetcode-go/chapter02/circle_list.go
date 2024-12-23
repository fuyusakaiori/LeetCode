package main

import "leetcode-go/node"

type ListCircleChecker struct {}

// 环形链表 II: 哈希表
func (checker *ListCircleChecker) DetectCycleHash(head *node.ListNode) *node.ListNode {
	current := head
	set := make(map[*node.ListNode]struct{})
	for current != nil {
		if _, ok := set[current]; ok {
			return current
		}
		set[current] = struct{}{}
		current = current.Next
	}
	return nil
}

// 环形链表: 快慢指针
func (checker *ListCircleChecker) DetectCycle(head *node.ListNode) *node.ListNode {
	flag := true
	slow, fast := head, head
	for flag || slow != fast {
		if fast == nil || fast.Next == nil {
			return nil
		}
		flag = false
		slow = slow.Next
		fast = fast.Next.Next
	}
	fast = head
	for slow != fast {
		slow = slow.Next
		fast = fast.Next
	}
	return slow
}