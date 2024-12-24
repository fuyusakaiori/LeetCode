package chapter03

import (
	"github.com/emirpasic/gods/queues/linkedlistqueue"
	"github.com/emirpasic/gods/stacks/linkedliststack"
	nodes "leetcode-go/node"
	"leetcode-go/util"
	"math"
)

// 验证二叉搜索树
type BinarySearchTreeValidate struct{}

type TreeNodeSearchWrapper struct {
	maxValue int
	minValue int
	isSearch bool
}

// 套路解决, 递归实现
func (tree *BinarySearchTreeValidate) IsValidateBst(root *nodes.TreeNode) bool {
	var recur func(node *nodes.TreeNode) *TreeNodeSearchWrapper
	recur = func(node *nodes.TreeNode) *TreeNodeSearchWrapper {
		if node == nil {
			return nil
		}
		isSearch := true
		minValue := node.Val
		maxValue := node.Val
		left := recur(node.Left)
		right := recur(node.Right)
		if left != nil {
			minValue = min(minValue, left.minValue)
			maxValue = max(maxValue, left.maxValue)
		}
		if right != nil {
			minValue = min(minValue, right.minValue)
			maxValue = max(maxValue, right.maxValue)
		}
		if left != nil && (!left.isSearch || (left.maxValue > node.Val)) {
			isSearch = false
		}
		if right != nil && (!right.isSearch || (right.minValue < node.Val)) {
			isSearch = false
		}
		return &TreeNodeSearchWrapper{minValue: minValue, maxValue: maxValue, isSearch: isSearch}
	}
	return recur(root).isSearch
}

// 中序遍历, 迭代实现
func (tree *BinarySearchTreeValidate) IsValidateIterate(root *nodes.TreeNode) bool {
	previous := math.MinInt64
	stack := linkedliststack.New()
	for !stack.Empty() || root != nil {
		if root != nil {
			stack.Push(root)
			root = root.Left
		} else {
			element, _ := stack.Pop()
			root = element.(*nodes.TreeNode)
			if previous != math.MinInt64 && previous >= root.Val {
				return false
			}
			previous = root.Val
			root = root.Right
		}
	}
	return true
}

// 递归实现
func (tree *BinarySearchTreeValidate) IsValidateRecur(root *nodes.TreeNode) bool {
	var recur func(node *nodes.TreeNode, minValue, maxValue int) bool
	recur = func(node *nodes.TreeNode, minValue, maxValue int) bool {
		if node == nil {
			return true
		}
		if node.Val <= minValue || node.Val >= maxValue {
			return false
		}
		// NOTE: 向左子节点遍历时最大值就是当前节点; 向右子节点遍历时最小值就是当前节点
		return recur(node.Left, minValue, node.Val) && recur(node.Right, node.Val, maxValue)
	}
	return recur(root, math.MinInt, math.MaxInt)
}

// 验证二叉平衡树
type BalanceBinaryTreeValidate struct{}

type TreeNodeBalanceWrapper struct {
	height    int
	isBalance bool
}

// 套路解决, 递归实现
func (tree *BalanceBinaryTreeValidate) IsBalanced(root *nodes.TreeNode) bool {
	var recur func(node *nodes.TreeNode) *TreeNodeBalanceWrapper
	recur = func(node *nodes.TreeNode) *TreeNodeBalanceWrapper {
		if node == nil {
			return &TreeNodeBalanceWrapper{height: 0, isBalance: true}
		}
		left := recur(node.Left)
		right := recur(node.Right)
		height := max(left.height, right.height) + 1
		isBalance := left.isBalance && right.isBalance && util.Abs(right.height-left.height) <= 1
		return &TreeNodeBalanceWrapper{height: height, isBalance: isBalance}
	}
	return recur(root).isBalance
}

// 不封装信息, 递归实现
func (tree *BalanceBinaryTreeValidate) IsBalancedRecur(root *nodes.TreeNode) bool {
	var recur func(node *nodes.TreeNode) int
	recur = func(node *nodes.TreeNode) int {
		if node == nil {
			return 0
		}
		left := recur(node.Left)
		right := recur(node.Right)
		if left == -1 || right == -1 || util.Abs(left-right) > 1 {
			return -1
		}
		return max(left, right) + 1
	}
	return recur(root) != -1
}

// 验证完全二叉树
type CompleteBinaryTreeValidate struct{}

type TreeNodeCompleteWrapper struct {
	index      int
	node       *nodes.TreeNode
}

// 套路解决, 深度优先, 迭代实现
func (tree *CompleteBinaryTreeValidate) IsCompleteTreeDfs(root *nodes.TreeNode) bool {
	index := 0
	queue := make([]*TreeNodeCompleteWrapper, 0)
	if root != nil {
		queue = append(queue, &TreeNodeCompleteWrapper{index: 1, node: root})
	}
	for index < len(queue) {
		wrapper := queue[index]
		index++
		if wrapper.node != nil {
			queue = append(queue, &TreeNodeCompleteWrapper{index: wrapper.index * 2, node: wrapper.node.Left})
			queue = append(queue, &TreeNodeCompleteWrapper{index: wrapper.index * 2 + 1, node: wrapper.node.Right})
		}
	}
	return queue[len(queue) - 1].index == len(queue)
}

// 广度优先, 迭代实现
func (tree *CompleteBinaryTreeValidate) IsCompleteTreeBfs(root *nodes.TreeNode) bool {
	isComplete := true
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		root = element.(*nodes.TreeNode)
		if root.Left == nil {
			isComplete = false
		} else {
			if !isComplete {
				return false
			}
			queue.Enqueue(root.Left)
		}
		if root.Right == nil {
			isComplete = false
		} else {
			if !isComplete {
				return false
			}
			queue.Enqueue(root.Right)
		}
	}
	return true
}

// 验证对称二叉树
type SymmetricBinaryTreeValidate struct{}

// 深度优先, 递归实现
func (tree *SymmetricBinaryTreeValidate) IsSymmetricDfs(root *nodes.TreeNode) bool {
	var recur func(left, right *nodes.TreeNode) bool
	recur = func(left, right *nodes.TreeNode) bool {
		if left == nil && right == nil {
			return true
		}
		if left != nil && right != nil {
			return left.Val == right.Val && recur(left.Left, right.Right) && recur(left.Right, right.Left)
		}
		return false
	}
	return recur(root, root)
}

// 广度优先, 迭代实现
func (tree *SymmetricBinaryTreeValidate) IsSymmetricBfs(root *nodes.TreeNode) bool {
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
		queue.Enqueue(root)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		first := element.(*nodes.TreeNode)
		element, _ = queue.Dequeue()
		second := element.(*nodes.TreeNode)
		if first == nil && second == nil {
			continue
		}
		if first == nil || second == nil || first.Val != second.Val {
			return false
		}
		queue.Enqueue(first.Left)
		queue.Enqueue(second.Right)
		queue.Enqueue(first.Right)
		queue.Enqueue(second.Left)
	}
	return true
}

// 相同的二叉树验证
type SameBinaryTreeValidate struct{}

func (tree *SameBinaryTreeValidate) IsSameTree(first, second *nodes.TreeNode) bool {
	if first == nil || second == nil {
		if first == nil && second == nil {
			return true
		}
		return false
	}
	return first.Val == second.Val && tree.IsSameTree(first.Left, second.Left) && tree.IsSameTree(first.Right, second.Right)
}