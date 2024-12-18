package main

type ListOddEvener struct {}

// 奇偶链表: 模拟
func (oddEvener *ListOddEvener) oddEvenList(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	odd, even := head, head.Next
	link := even
	for even != nil {
		if odd.Next == nil {
			break
		}
		odd.Next = odd.Next.Next
		even.Next = even.Next.Next
		odd = odd.Next
		even = even.Next
	}
	odd.Next = link
	return head
}

// 奇偶链表 => 分割链表
func (oddEvener *ListOddEvener) oddEvenListSplit(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	flag := true
	current := head
	oDummy, eDummy := &ListNode{Val: 0, Next: head}, &ListNode{Val: 0, Next: head.Next}
	oCurrent, eCurrent := oDummy, eDummy
	for current != nil {
		if flag {
			oCurrent.Next = current
			oCurrent = oCurrent.Next
		} else {
			eCurrent.Next = current
			eCurrent = eCurrent.Next
		}
		flag = !flag
		current = current.Next
 	}
	eCurrent.Next = nil
	oCurrent.Next = eDummy.Next
	return oDummy.Next
}
