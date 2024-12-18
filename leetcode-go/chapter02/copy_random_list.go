package main

type RandomListCopier struct {
	
}

// 随机链表的复制: 哈希表
func (copier *RandomListCopier) CopyRandomListHash(head *RandomNode) *RandomNode {
	current := head
	nodeMap := make(map[*RandomNode]*RandomNode)
	for current != nil {
		nodeMap[current] = &RandomNode{Val: current.Val}
		current = current.Next
	}
	current = head
	for current != nil {
		nodeMap[current].Next = nodeMap[current.Next]
		nodeMap[current].Random = nodeMap[current.Random]
		current = current.Next
	}
	return nodeMap[head]
}

// 随机链表的复制: 不依赖哈希表
func (copier *RandomListCopier) CopyRandomList(head *RandomNode) *RandomNode {
	current := head
	for current != nil {
		node := &RandomNode{Val: current.Val, Next: current.Next}
		current.Next = node
		current = current.Next.Next
	}
	current = head
	for current != nil {
		if current.Random != nil {
			current.Next.Random = current.Random.Next
		}
		current = current.Next.Next
	}
	current = head
	dummy := &RandomNode{Val: 0}
	clone := dummy
	for current != nil {
		clone.Next = current.Next
		clone = clone.Next
		current.Next = current.Next.Next
		current = current.Next
	}
	return dummy.Next
}
