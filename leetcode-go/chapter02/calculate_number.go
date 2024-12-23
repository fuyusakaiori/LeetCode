package main

import "leetcode-go/node"

type TwoNumbersAdder struct {

}

// 两数之和
func (adder *TwoNumbersAdder) AddTwoNumbers(first, second *node.ListNode) *node.ListNode {
	sum, carry := 0, 0
	dummy := &node.ListNode{Val: 0}
	current := dummy
	for first != nil || second != nil {
		firstValue, secondValue := 0, 0
		if first != nil {
			firstValue = first.Val
			first = first.Next
		}
		if second != nil {
			secondValue = second.Val
			second = second.Next
		}
		sum = (firstValue + secondValue + carry) % 10
		carry = (firstValue + secondValue + carry) / 10
		current.Next = &node.ListNode{Val: sum}
		current = current.Next
	}
	if carry != 0 {
		current.Next = &node.ListNode{Val: carry}
	}
	return dummy.Next
}

// 两数之和 II
func (adder *TwoNumbersAdder) AddTwoNumbersII(first, second *node.ListNode) *node.ListNode {
	reverse := &ListReverser{}
	first = reverse.ReverseListLoop(first)
	second = reverse.ReverseListLoop(second)
	return reverse.ReverseListLoop(adder.AddTwoNumbers(first, second))
}
