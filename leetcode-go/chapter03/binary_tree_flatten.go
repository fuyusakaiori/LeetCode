package chapter03

import (
	"github.com/emirpasic/gods/stacks/linkedliststack"
	nodes "leetcode-go/node"
)

type BinaryTreeFlatten struct {}

// 前序遍历, 深度优先, 递归实现
func (tree *BinaryTreeFlatten) FlattenRecur(root *nodes.TreeNode)  {
	var previous *nodes.TreeNode
	var recur func(node *nodes.TreeNode)
	recur = func(node *nodes.TreeNode) {
		if node == nil {
			return
		}
		left := node.Left
		right := node.Right
		if previous == nil {
			node.Right = left
		} else {
			// NOTE: 让前驱节点的右指针指向左子结点, 这样会导致右子节点断开, 所以需要提前记录
			previous.Right = node
		}
		// NOTE: 让当前节点的左指针断开, 所以也需要提前记录
		node.Left = nil
		previous = node
		recur(left)
		recur(right)
	}
	recur(root)
}

// 前序遍历, 深度优先, 迭代实现
func (tree *BinaryTreeFlatten) FlattenIterate(root *nodes.TreeNode)  {
	stack := linkedliststack.New()
	treeNodes := make([]*nodes.TreeNode, 0)
	if root != nil {
		stack.Push(root)
	}
	for !stack.Empty() {
		element, _ := stack.Pop()
		root = element.(*nodes.TreeNode)
		treeNodes = append(treeNodes, root)
		if root.Right != nil {
			stack.Push(root.Right)
		}
		if root.Left != nil {
			stack.Push(root.Left)
		}
	}
	for index := 0; index < len(treeNodes) - 1; index++ {
		treeNodes[index].Left = nil
		treeNodes[index].Right = treeNodes[index + 1]
	}
}

// 类前序遍历, 空间复杂度为 O(1)
func (tree *BinaryTreeFlatten) Flatten(root *nodes.TreeNode)  {
	current := root
	for current != nil {
		if current.Left != nil {
			// NOTE: 需要将左子树的最右子节点指向右子树
			previous := current.Left
			for previous.Right != nil {
				// NOTE: 不停地将左子结点向右子树遍历, 直到最右
				previous = previous.Right
			}
			previous.Right = current.Right
			current.Right = current.Left
			current.Left = nil
		}
		current = current.Right
	}
}
