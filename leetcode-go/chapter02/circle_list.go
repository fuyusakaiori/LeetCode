package main

type ListCircleChecker struct {}

// 环形链表 II: 哈希表
func (checker *ListCircleChecker) DetectCycleHash(head *ListNode) *ListNode {
	current := head
	set := make(map[*ListNode]struct{})
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
func (checker *ListCircleChecker) DetectCycle(head *ListNode) *ListNode {
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