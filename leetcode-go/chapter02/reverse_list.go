package main

type ListReverser struct {
}

// 反转链表: 迭代
func (reverse *ListReverser) ReverseListLoop(head *ListNode) *ListNode {
	var previous *ListNode = nil
	var current *ListNode = head
	var next *ListNode = nil
	for current != nil {
		next = current.Next
		current.Next = previous
		previous = current
		current = next
	}
	return previous
}

// 反转链表: 递归
func (reverse *ListReverser) ReverseListRecursive(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	tail := reverse.ReverseListRecursive(head.Next)
	head.Next.Next = head
	head.Next = nil
	return tail
}

// 反转链表 II
func (reverse *ListReverser) ReverseBetween(head *ListNode, left, right int) *ListNode {
	dummy := &ListNode{Val: 0, Next: head}
	current := dummy
	for index := 0; index < left - 1; index++ {
		current = current.Next
	}
	leftNode := current
	for index := left - 1; index < right; index++ {
		current = current.Next
	}
	rightNode := current
	startNode := leftNode.Next
	endNode := rightNode.Next
	leftNode.Next = nil
	rightNode.Next = nil
	reverse.ReverseListLoop(startNode)
	leftNode.Next = rightNode
	startNode.Next = endNode
	return dummy.Next
}

// 反转链表: K 个一组
func (reverse *ListReverser) ReverseKGroup(head *ListNode, k int) *ListNode {
	dummy := &ListNode{Val: 0, Next: head}
	current, previous := head, dummy
	for current != nil {
		// 注意: 移动的单位只能是 k - 1
		for index := 0; index < k - 1; index++ {
			if current.Next == nil {
				return dummy.Next
			}
			current = current.Next
		}
		start := previous.Next
		end := current.Next
		previous.Next = nil
		current.Next = nil
		reverse.ReverseListLoop(start)
		previous.Next = current
		start.Next = end
		previous = start
		current = end
	}
	return dummy.Next
}

// 反转链表: 两两交换
func (reverse *ListReverser) SwapPairs(head *ListNode) *ListNode {
	dummy := &ListNode{Val: 0, Next: head}
	current := dummy
	for current.Next != nil && current.Next.Next != nil {
		node1, node2 := current.Next, current.Next.Next
		current.Next = node2
		node1.Next = node2.Next
		node2.Next = node1
		current = node2.Next
	}
	return dummy.Next
}
