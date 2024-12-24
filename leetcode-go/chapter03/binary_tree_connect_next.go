package chapter03

import (
	"github.com/emirpasic/gods/queues/linkedlistqueue"
	nodes "leetcode-go/node"
)

// 填充每个节点的下一个右侧节点指针
type FullBinaryTreeConnectNext struct {}

// 广度优先, 迭代实现, 空间复杂度为 O(1)
func (tree *FullBinaryTreeConnectNext) Connect(root *nodes.TreeNode) *nodes.TreeNode {
	if root == nil {
		return nil
	}
	current := root
	for current.Left != nil {
		head := current
		for head != nil {
			head.Left.Next = head.Right
			if head.Next != nil {
				head.Right.Next = head.Next.Left
			}
			head = head.Next
		}
		current = current.Left
	}
	return root
}

// 层序遍历, 广度优先，迭代实现
func (tree *FullBinaryTreeConnectNext) ConnectBfs(root *nodes.TreeNode) *nodes.TreeNode {
	var currentLevelEnd *nodes.TreeNode = root
	var previous, nextLevelEnd *nodes.TreeNode = nil, nil
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		node := element.(*nodes.TreeNode)
		if previous != nil {
			previous.Next = node
		}
		previous = node
		if node.Left != nil {
			queue.Enqueue(node.Left)
			nextLevelEnd = node.Left
		}
		if node.Right != nil {
			queue.Enqueue(node.Right)
			nextLevelEnd = node.Right
		}
		if currentLevelEnd == node {
			previous = nil
			currentLevelEnd = nextLevelEnd
		}
	}
	return root
}

// 前序遍历, 深度优先, 递归实现
func (tree *FullBinaryTreeConnectNext) ConnectDfs(root *nodes.TreeNode) *nodes.TreeNode {
	var recur func(current, neighbor *nodes.TreeNode)
	recur = func(current, neighbor *nodes.TreeNode) {
		if current == nil {
			return
		}
		current.Next = neighbor
		if neighbor != nil {
			neighbor = neighbor.Left
		}
		recur(current.Left, current.Right)
		recur(current.Right, neighbor)
	}
	recur(root, nil)
	return root
}


// 填充每个节点的下一个右侧节点指针 II
type BinaryTreeConnectNext struct {}

// 广度优先, 迭代实现, 空间复杂度为 O(1)
func (tree *BinaryTreeConnectNext) Connect(root *nodes.TreeNode) *nodes.TreeNode {
	if root == nil {
		return nil
	}
	current := root
	for current != nil {
		var head *nodes.TreeNode = current
		var previous, next *nodes.TreeNode = nil, nil
		for head != nil {
			if head.Left != nil {
				if previous != nil {
					previous.Next = head.Left
				}
				if next == nil {
					next = head.Left
				}
				previous = head.Left
			}
			if head.Right != nil {
				if previous != nil {
					previous.Next = head.Right
				}
				if next == nil {
					next = head.Right
				}
				previous = head.Right
			}
			head = head.Next
		}
		current = next
	}
	return root
}

// 层序遍历, 广度优先, 迭代实现
func (tree *BinaryTreeConnectNext) ConnectBfs(root *nodes.TreeNode) *nodes.TreeNode {
	var currentLevelEnd *nodes.TreeNode = root
	var previous, nextLevelEnd *nodes.TreeNode = nil, nil
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		node := element.(*nodes.TreeNode)
		if previous != nil {
			previous.Next = node
		}
		previous = node
		if node.Left != nil {
			queue.Enqueue(node.Left)
			nextLevelEnd = node.Left
		}
		if node.Right != nil {
			queue.Enqueue(node.Right)
			nextLevelEnd = node.Right
		}
		if currentLevelEnd == node {
			previous = nil
			currentLevelEnd = nextLevelEnd
		}
	}
	return root
}