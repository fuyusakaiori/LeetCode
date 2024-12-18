package main

type ListFlattener struct {}

// 扁平化多级双向链表
func (flattener *ListFlattener) Flatten(root *Node) *Node {
	if root == nil {
		return nil
	}
	flattener.dfs(root)
	return root
}

func (flattener *ListFlattener) dfs(head *Node) *Node {
	var current *Node = head
	var tail *Node = nil
	for current != nil {
		if current.Child != nil {
			tail = flattener.dfs(current.Child)
			tail.Next = current.Next
			if current.Next != nil {
				current.Next.Prev = tail
			}
			current.Next = current.Child
			current.Child.Prev = current
			current.Child = nil
		} else  {
			tail = current
			current = current.Next
		}
	}
	return tail
}
