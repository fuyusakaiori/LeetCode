package chapter03

import (
	"github.com/emirpasic/gods/queues/linkedlistqueue"
	"github.com/emirpasic/gods/stacks/linkedliststack"
	nodes "leetcode-go/node"
)

// 前序数组 + 中序数组构建二叉树
type PreOrderBuildBinaryTree struct {}

// 深度优先, 递归实现
func (tree *PreOrderBuildBinaryTree) BuildTreeRecur(preOrder, infixOrder []int) *nodes.TreeNode {
	infixMap := make(map[int]int)
	for index := range infixOrder {
		infixMap[infixOrder[index]] = index
	}
	var recur func(index, left, right int, preOrder []int, infixMap map[int]int) *nodes.TreeNode
	recur = func(index, left, right int, preOrder []int, infixMap map[int]int) *nodes.TreeNode {
		if left > right {
			return nil
		}
		mid := infixMap[preOrder[index]]
		root := &nodes.TreeNode{Val: preOrder[index]}
		root.Left = recur(index + 1, left, mid - 1, preOrder, infixMap)
		root.Right = recur(index + (mid - left) + 1, mid + 1, right, preOrder, infixMap)
		return root
	}
	return recur(0, 0, len(infixOrder) - 1, preOrder, infixMap)
}

// 深度优先, 迭代实现
func (tree *PreOrderBuildBinaryTree) BuildTreeIterate(preOrder, infixOrder []int) *nodes.TreeNode {
	preIndex, infixIndex := 0, 0
	root := &nodes.TreeNode{Val: preOrder[preIndex]}
	stack := linkedliststack.New()
	stack.Push(root)
	preIndex++
	for preIndex < len(preOrder) {
		element, _ := stack.Peek()
		node := element.(*nodes.TreeNode)
		if node.Val != infixOrder[infixIndex] {
			node.Left = &nodes.TreeNode{Val: preOrder[preIndex]}
			stack.Push(node.Left)
		} else {
			element, _ = stack.Peek()
			for !stack.Empty() && element.(*nodes.TreeNode).Val == infixOrder[infixIndex] {
				temp, _ := stack.Pop()
				node = temp.(*nodes.TreeNode)
				infixIndex++
				element, _ = stack.Peek()
			}
			node.Right = &nodes.TreeNode{Val: preOrder[preIndex]}
			stack.Push(node.Right)
		}
		preIndex++
	}
	return root
}

// 后序数组 + 中序数组构建二叉树
type PostOrderBuildBinaryTree struct {}

// 深度优先, 递归实现
func (tree *PostOrderBuildBinaryTree) BuildTreeRecur(infixOrder, postOrder []int) *nodes.TreeNode {
	infixMap := make(map[int]int)
	for index := range infixOrder {
		infixMap[infixOrder[index]] = index
	}
	var recur func(index, left, right int, postOrder []int, infixMap map[int]int) *nodes.TreeNode
	recur = func(index, left, right int, postOrder []int, infixMap map[int]int) *nodes.TreeNode {
		if left > right {
			return nil
		}
		mid := infixMap[postOrder[index]]
		root := &nodes.TreeNode{Val: postOrder[index]}
		root.Left = recur(index - (right - mid) - 1, left, mid - 1, postOrder, infixMap)
		root.Right = recur(index - 1, mid + 1, right, postOrder, infixMap)
		return root
	}
	return recur(len(postOrder) - 1, 0, len(infixOrder) - 1, postOrder, infixMap)
}

// 深度优先, 迭代实现
func (tree *PostOrderBuildBinaryTree) BuildTreeIterate(infixOrder, postOrder []int) *nodes.TreeNode {
	postIndex, infixIndex := len(postOrder) - 1, len(infixOrder) - 1
	root := &nodes.TreeNode{Val: postOrder[postIndex]}
	stack := linkedliststack.New()
	stack.Push(root)
	postIndex--
	for postIndex >= 0 {
		element, _ := stack.Peek()
		node := element.(*nodes.TreeNode)
		if node.Val != infixOrder[infixIndex] {
			node.Right = &nodes.TreeNode{Val: postOrder[postIndex]}
			stack.Push(node.Right)
		} else {
			element, _ = stack.Peek()
			for !stack.Empty() && element.(*nodes.TreeNode).Val == infixOrder[infixIndex] {
				temp, _ := stack.Pop()
				node = temp.(*nodes.TreeNode)
				infixIndex--
				element, _ = stack.Peek()
			}
			node.Left = &nodes.TreeNode{Val: postOrder[postIndex]}
			stack.Push(node.Left)
		}
		postIndex--
	}
	return root
}

// 最大二叉树
type MaxBinaryTree struct {}

// 深度优先, 递归实现
func (tree *MaxBinaryTree) ConstructMaximumBinaryTreeRecur(nums []int) *nodes.TreeNode {
	var recur func(left, right int, nums []int) *nodes.TreeNode
	recur = func(left, right int, nums []int) *nodes.TreeNode {
		if left > right {
			return nil
		}
		maxIndex, maxNum := 0, 0
		for index := left; index <= right; index++ {
			if nums[index] > maxNum {
				maxIndex = index
				maxNum = nums[index]
			}
		}
		root := &nodes.TreeNode{Val: nums[maxIndex]}
		root.Left = recur(left, maxIndex - 1, nums)
		root.Right = recur(maxIndex + 1, right, nums)
		return root
	}
	return recur( 0, len(nums) - 1, nums)
}

// 深度优先, 单调栈实现
func (tree *MaxBinaryTree) ConstructMaximumBinaryTreeStack(nums []int) *nodes.TreeNode {
	var root *nodes.TreeNode
	stack := linkedliststack.New()
	for index := 0; index < len(nums); index++ {
		root = &nodes.TreeNode{Val: nums[index]}
		for element, ok := stack.Peek(); element.(*nodes.TreeNode).Val < root.Val; {
			element, _ = stack.Pop()
			node := element.(*nodes.TreeNode)
			if element, ok = stack.Peek(); ok && element.(*nodes.TreeNode).Val > root.Val {
				root.Left = node
			} else {
				element.(*nodes.TreeNode).Right = node
			}
		}
		stack.Push(root)
	}
	for !stack.Empty() {
		element, _ := stack.Pop()
		root = element.(*nodes.TreeNode)
		if element, ok := stack.Peek(); ok {
			element.(*nodes.TreeNode).Right = root
		}
	}
	return root
}

// 合并二叉树
type MergeBinaryTree struct {}

// 深度优先, 递归实现
func (tree MergeBinaryTree) MergeTreesDfs(first, second *nodes.TreeNode) *nodes.TreeNode {
	if first == nil || second == nil {
		if first != nil {
			return first
		}
		return second
	}
	root := &nodes.TreeNode{Val: first.Val + second.Val}
	root.Left = tree.MergeTreesDfs(first.Left, second.Left)
	root.Right = tree.MergeTreesDfs(first.Right, second.Right)
	return root
}

// 广度优先, 迭代实现
func (tree MergeBinaryTree) MergeTreesBfs(first, second *nodes.TreeNode) *nodes.TreeNode {
	if first == nil {
		return second
	}
	if second == nil {
		return first
	}
	merge := &nodes.TreeNode{Val: first.Val + second.Val}
	queue := linkedlistqueue.New()
	firstQueue := linkedlistqueue.New()
	secondQueue := linkedlistqueue.New()
	queue.Enqueue(merge)
	firstQueue.Enqueue(first)
	secondQueue.Enqueue(second)
	for !firstQueue.Empty() || !secondQueue.Empty() {
		element, _ := queue.Dequeue()
		node := element.(*nodes.TreeNode)
		firstElement, _ := firstQueue.Dequeue(); secondElement, _ := secondQueue.Dequeue()
		firstNode, secondNode := firstElement.(*nodes.TreeNode), secondElement.(*nodes.TreeNode)
		firstLeft, firstRight, secondLeft, secondRight := firstNode.Left, firstNode.Right, secondNode.Left, secondNode.Right
		if firstLeft != nil || secondLeft != nil {
			if firstLeft != nil && secondLeft != nil {
				left := &nodes.TreeNode{Val: firstLeft.Val + secondLeft.Val}
				node.Left = left
				queue.Enqueue(left)
				firstQueue.Enqueue(firstLeft)
				secondQueue.Enqueue(secondLeft)
			} else if firstLeft != nil {
				node.Left = firstLeft
			} else {
				node.Left = secondLeft
			}
		}
		if firstRight != nil || secondRight != nil {
			if firstRight != nil && secondRight != nil {
				right := &nodes.TreeNode{Val: firstRight.Val + secondRight.Val}
				node.Right = right
				queue.Enqueue(right)
				firstQueue.Enqueue(firstRight)
				secondQueue.Enqueue(secondRight)
			} else if firstRight != nil {
				node.Right = firstRight
			} else {
				node.Right = secondRight
			}
		}
	}

	return merge
}
