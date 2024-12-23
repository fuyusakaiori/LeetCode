package main

import (
	"leetcode-go/node"
	"math"
)

type ListIntersecter struct {}

// 相交链表: 哈希表
func (intersection *ListIntersecter) GetIntersectionNodeHash(first, second *node.ListNode) *node.ListNode {
	set := make(map[*node.ListNode]struct{})
	for first != nil {
		set[first] = struct{}{}
		first = first.Next
	}
	for second != nil {
		if _, ok := set[second]; ok {
			return second
		}
		second = second.Next
	}
	return nil
}

// 相交链表: 计算长度
func (intersection *ListIntersecter) GetIntersectionNode(first, second *node.ListNode) *node.ListNode {
	firstLength := intersection.getLength(first)
	secondLength := intersection.getLength(second)
	diffLength := int(math.Abs(float64(firstLength) - float64(secondLength)))
	for index := 0; index < diffLength; index++ {
		if firstLength < secondLength {
			second = second.Next
		} else {
			first = first.Next
		}
	}
	for first != second {
		if first == nil || second.Next == nil {
			return nil
		}
		first = first.Next
		second = second.Next
	}
	return first
}

func (intersection *ListIntersecter) getLength(list *node.ListNode) int {
	length := 0
	for list != nil {
		list = list.Next
		length++
	}
	return length
}
