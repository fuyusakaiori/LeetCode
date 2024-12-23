package chapter03

import (
	"fmt"
	"github.com/emirpasic/gods/queues/linkedlistqueue"
	"github.com/emirpasic/gods/stacks/linkedliststack"
	nodes "leetcode-go/node"
)

// 树的遍历
type BinaryTreeTraverse interface {

	// 递归
	RecurTree(root *nodes.TreeNode)
	// 迭代
	IterateTree(root *nodes.TreeNode)

}

// 前序遍历
type BinaryTreePreOrderTraverse struct {}

func (traverse *BinaryTreePreOrderTraverse) RecurTree(root *nodes.TreeNode) {
	if root == nil {
		return
	}
	// NOTE: 这里可以做任何操作
	fmt.Printf("tree node value: %v", root.Val)
	traverse.RecurTree(root.Left)
	traverse.RecurTree(root.Right)
}

func (traverse *BinaryTreePreOrderTraverse) RecurTreeValue(root *nodes.TreeNode) []int {
	values := make([]int, 0)
	var recur func(node *nodes.TreeNode)
	recur = func(node *nodes.TreeNode) {
		if node == nil {
			return
		}
		values = append(values, node.Val)
		recur(node.Left)
		recur(node.Right)
	}
	recur(root)
	return values
}

func (traverse *BinaryTreePreOrderTraverse) IterateTree(root *nodes.TreeNode) {
	stack := linkedliststack.New()
	if root != nil {
		stack.Push(root)
	}
	for !stack.Empty() {
		value, _ := stack.Pop()
		root = value.(*nodes.TreeNode)
		// NOTE: 这里可以做任何操作
		fmt.Printf("tree node value: %v", root.Val)
		if root.Right != nil {
			stack.Push(root.Right)
		}
		if root.Left != nil {
			stack.Push(root.Left)
		}
	}
}

// 中序遍历
type BinaryTreeInfixOrderTraverse struct {}

func (traverse *BinaryTreeInfixOrderTraverse) RecurTree(root *nodes.TreeNode) {
	if root == nil {
		return
	}
	traverse.RecurTree(root.Left)
	// NOTE: 这里可以做任何操作
	fmt.Printf("tree node value: %v", root.Val)
	traverse.RecurTree(root.Right)
}

func (traverse *BinaryTreeInfixOrderTraverse) RecurTreeValue(root *nodes.TreeNode) []int {
	values := make([]int, 0)
	var recur func(node *nodes.TreeNode)
	recur = func(node *nodes.TreeNode) {
		if node == nil {
			return
		}
		recur(node.Left)
		values = append(values, node.Val)
		recur(node.Right)
	}
	recur(root)
	return values
}

func (traverse *BinaryTreeInfixOrderTraverse) IterateTree(root *nodes.TreeNode) {
	stack := linkedliststack.New()
	for !stack.Empty() || root != nil {
		if root != nil {
			stack.Push(root)
			root = root.Left
		} else {
			value, _ := stack.Pop()
			root = value.(*nodes.TreeNode)
			// NOTE: 这里可以做任何操作
			fmt.Printf("tree node value: %v", root.Val)
			root = root.Right
		}
	}
}

// 后序遍历
type BinaryTreePostOrderTraverse struct {}

func (traverse *BinaryTreePostOrderTraverse) RecurTree(root *nodes.TreeNode) {
	if root == nil {
		return
	}
	traverse.RecurTree(root.Left)
	traverse.RecurTree(root.Right)
	// NOTE: 这里可以做任何操作
	fmt.Printf("tree node value: %v", root.Val)
}

func (traverse *BinaryTreePostOrderTraverse) RecurTreeValue(root *nodes.TreeNode) []int {
	values := make([]int, 0)
	var recur func(node *nodes.TreeNode)
	recur = func(node *nodes.TreeNode) {
		if node == nil {
			return
		}
		recur(node.Left)
		recur(node.Right)
		values = append(values, node.Val)
	}
	recur(root)
	return values
}

func (traverse *BinaryTreePostOrderTraverse) IterateTree(root *nodes.TreeNode) {
	firstStack := linkedliststack.New()
	secondStack := linkedliststack.New()
	if root != nil {
		firstStack.Push(root)
	}
	for !firstStack.Empty() {
		value, _ := firstStack.Pop()
		root = value.(*nodes.TreeNode)
		secondStack.Push(root)
		if root.Left != nil {
			firstStack.Push(root.Left)
		}
		if root.Right != nil {
			firstStack.Push(root.Right)
		}
	}
	for !secondStack.Empty() {
		value, _ := secondStack.Pop()
		fmt.Printf("tree node value: %v", value.(*nodes.TreeNode).Val)
	}
}

// 层序遍历
type BinaryTreeLevelOrderTraverse struct {}

func (traverse *BinaryTreeLevelOrderTraverse) RecurTree(root *nodes.TreeNode) {
	levels := make(map[int][]*nodes.TreeNode)
	var recur func(node *nodes.TreeNode, level int, levels map[int][]*nodes.TreeNode)
	recur = func(node *nodes.TreeNode, level int, levels map[int][]*nodes.TreeNode) {
		if node == nil {
			return
		}
		if _, ok := levels[level]; !ok {
			levels[level] = make([]*nodes.TreeNode, 0)
		}
		levels[level] = append(levels[level], node)
		recur(node.Left, level + 1, levels)
		recur(node.Right, level + 1, levels)
	}
	recur(root, 0, levels)
	// NOTE: 哈希表是乱序的
	for index := 0; index < len(levels); index++ {
		for _, treeNode := range levels[index] {
			fmt.Printf("tree node value: %v", treeNode.Val)
		}
	}
}

func (traverse *BinaryTreeLevelOrderTraverse) IterateTree(root *nodes.TreeNode) {
	var currentLevelEnd *nodes.TreeNode = root
	var nextLevelEnd *nodes.TreeNode = nil
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
	}
	for !queue.Empty() {
		value, _ := queue.Dequeue()
		root = value.(*nodes.TreeNode)
		// NOTE: 这里可以做任何操作
		fmt.Printf("tree node value: %v", root.Val)
		if root.Left != nil {
			queue.Enqueue(root.Left)
			nextLevelEnd = root.Left
		}
		if root.Right != nil {
			queue.Enqueue(root.Right)
			nextLevelEnd = root.Right
		}
		if currentLevelEnd == root {
			currentLevelEnd = nextLevelEnd
		}
	}
}

// 锯齿状层序遍历
type BinaryTreeZigZagLevelOrderTraverse struct {}

func (traverse *BinaryTreeZigZagLevelOrderTraverse) IterateDeque(root *nodes.TreeNode) [][]int {

	return nil
}

func (traverse *BinaryTreeZigZagLevelOrderTraverse) IterateStack(root *nodes.TreeNode) [][]int {

	return nil
}




