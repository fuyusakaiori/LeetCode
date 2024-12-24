package chapter03

import (
	"github.com/emirpasic/gods/stacks/linkedliststack"
	nodes "leetcode-go/node"
	"math"
)

// 二叉搜索树的最小绝对差
type BinarySearchTreeMinDiff struct {}

// 中序遍历, 递归实现
func (tree *BinarySearchTreeMinDiff) GetMinimumDifferenceRecur(root *nodes.TreeNode) int {
	previous, diff := -1, math.MaxInt
	var recur func(node *nodes.TreeNode)
	recur = func(node *nodes.TreeNode) {
		if node == nil {
			return
		}
		recur(node.Left)
		if previous != -1  {
			diff = min(diff, node.Val -previous)
		}
		previous = node.Val
		recur(node.Right)
	}
	recur(root)
	return diff
}

// 中序遍历, 迭代实现
func (tree *BinarySearchTreeMinDiff) GetMinimumDifferenceIterate(root *nodes.TreeNode) int {
	previous, diff := -1, math.MaxInt
	stack := linkedliststack.New()
	for !stack.Empty() || root != nil {
		if root != nil {
			stack.Push(root)
			root = root.Left
		} else {
			element, _ := stack.Pop()
			root = element.(*nodes.TreeNode)
			if previous != -1 {
				diff = min(diff, root.Val - previous)
			}
			previous = root.Val
			root = root.Right
		}
	}
	return diff
}

// 二叉搜索树的范围和
type BinarySearchTreeRangeSum struct {}

// 中序遍历, 递归实现
func (tree *BinarySearchTreeRangeSum) RangeSumBSTRecur(root *nodes.TreeNode, low, high int) int {
	if root == nil {
		return 0
	}
	if root.Val < low {
		return tree.RangeSumBSTRecur(root.Right, low, high)
	}
	if root.Val > high {
		return tree.RangeSumBSTRecur(root.Left, low, high)
	}
	return root.Val + tree.RangeSumBSTRecur(root.Left, low, high) + tree.RangeSumBSTRecur(root.Right, low, high)
}

// 中序遍历, 迭代实现
func (tree *BinarySearchTreeRangeSum) RangeSumBSTIterate(root *nodes.TreeNode, low, high int) int {
	rangeSum := 0
	stack := linkedliststack.New()
	for !stack.Empty() || root != nil {
		if root != nil {
			stack.Push(root)
			root = root.Left
		} else {
			element, _ := stack.Pop()
			root = element.(*nodes.TreeNode)
			if low <= root.Val && root.Val <= high {
				rangeSum += root.Val
			}
			root = root.Right
		}
	}
	return rangeSum
}

// 二叉搜索树中的众数
type BinarySearchTreeMode struct {}

// 中序遍历, 递归实现
func (tree *BinarySearchTreeMode) FindModeRecur(root *nodes.TreeNode) []int {
	previous := -1
	frequency, maxFrequency := 0, 0
	modes := make([]int, 0)
	var recur func(node *nodes.TreeNode)
	recur = func(node *nodes.TreeNode) {
		if node == nil {
			return
		}
		recur(node.Left)
		if previous == node.Val {
			frequency++
		} else {
			frequency = 1
			previous = node.Val
		}
		if maxFrequency == frequency {
			modes = append(modes, node.Val)
		}
		if maxFrequency < frequency {
			maxFrequency = frequency
			modes = make([]int, 0)
			modes = append(modes, node.Val)
		}
		recur(node.Right)
	}
	recur(root)
	return modes
}

// 中序遍历, 迭代实现
func (tree *BinarySearchTreeMode) FindModeIterate(root *nodes.TreeNode) []int {
	previous := -1
	frequency, maxFrequency := 0, 0
	modes := make([]int, 0)
	stack := linkedliststack.New()
	for !stack.Empty() || root != nil {
		if root != nil {
			stack.Push(root)
			root = root.Left
		} else {
			element, _ := stack.Pop()
			root = element.(*nodes.TreeNode)
			if previous == root.Val {
				frequency++
			} else {
				frequency = 1
				previous = root.Val
			}
			if maxFrequency == frequency {
				modes = append(modes, root.Val)
			}
			if maxFrequency < frequency {
				maxFrequency = frequency
				modes = make([]int, 0)
				modes = append(modes, root.Val)
			}
			root = root.Right
		}
	}
	return modes
}

// 不同二叉搜索树的数量
type BinarySearchTreeDifferent struct {}

// 记忆化递归
func (tree *BinarySearchTreeDifferent) NumsTreeRecur(n int) int {
	dp := make([]int, n + 1)
	var recur func(num int) int
	recur = func(num int) int {
		if num == 0 || num == 1 {
			return 1
		}
		if dp[num] != 0 {
			return dp[num]
		}
		sum := 0
		for index := 0; index < num; index++ {
			 sum += recur(index) * recur(num -index- 1)
		}
		dp[num] = sum
		return dp[num]
	}
	return recur(n)
}

// 动态规划
func (tree *BinarySearchTreeDifferent) NumTreeDp(n int) int {
	dp := make([]int, n + 1)
	dp[0], dp[1] = 1, 1
	for index := 2; index < len(dp); index++ {
		for left, right := 0, index - 1; left <= index && right >= 0; left, right = left + 1, right - 1 {
			dp[index] += dp[left] * dp[right]
		}
	}
	return dp[len(dp) - 1]
}

// 不同的二叉搜索树 II
func (tree *BinarySearchTreeDifferent) GenerateTrees(n int) []*nodes.TreeNode {
	var recur func(left, right int) []*nodes.TreeNode
	recur = func(left, right int) []*nodes.TreeNode {
		trees := make([]*nodes.TreeNode, 0)
		if left > right {
			return append(trees, nil)
		}
		for index := left; index <= right; index++ {
			leftTrees := recur(left, index - 1)
			rightTrees := recur(index + 1, right)
			for _, leftTree := range leftTrees {
				for _, rightTree := range rightTrees {
					root := &nodes.TreeNode{Val: index}
					root.Left = leftTree
					root.Right = rightTree
					trees = append(trees, root)
				}
			}
		}
		return trees
	}
	return recur(1, n)
}


// 将有序数组/链表转换为二叉搜索树
type ConvertArrayListToBinarySearchTree struct {}

// 将有序数组转换为二叉搜索树
func (tree *ConvertArrayListToBinarySearchTree) SortedArrayToBST(nums []int) *nodes.TreeNode {
	var recur func(left, right int) *nodes.TreeNode
	recur = func(left, right int) *nodes.TreeNode {
		if left > right {
			return nil
		}
		mid := left + ((right - left) >> 1)
		root := &nodes.TreeNode{Val: nums[mid]}
		root.Left = recur(left, mid - 1)
		root.Right = recur(mid + 1, right)
		return root
	}
	return recur(0, len(nums) - 1)
}

// 将有序链表转换为二叉搜索树
func (tree *ConvertArrayListToBinarySearchTree) SortedListToBST(head *nodes.ListNode) *nodes.TreeNode {
	var recur func(current, tail *nodes.ListNode) *nodes.TreeNode
	recur = func(current, tail *nodes.ListNode) *nodes.TreeNode {
		if current == tail {
			return nil
		}
		mid := tree.FindListMid(head, tail)
		root := &nodes.TreeNode{Val: mid.Val}
		root.Left = recur(head, mid)
		root.Right = recur(mid.Next, tail)
		return root
	}
	return recur(head, nil)
}

func (tree *ConvertArrayListToBinarySearchTree) FindListMid(head, tail *nodes.ListNode) *nodes.ListNode {
	slow, fast := head, head
	for fast != tail && fast.Next != tail {
		slow = slow.Next
		fast = fast.Next.Next
	}
	return slow
}

// 二叉搜索树中的第 K 小的元素
type BinarySearchTreeKthSmallest struct {}

// 中序遍历, 递归实现
func (tree *BinarySearchTreeKthSmallest) KthSmallestRecur(root *nodes.TreeNode, k int) int {
	var recur func(node *nodes.TreeNode) int
	recur = func(node *nodes.TreeNode) int {
		if node == nil {
			return -1
		}
		left := recur(node.Left)
		k--
		if k == 0 {
			return node.Val
		}
		right := recur(node.Right)
		if left != -1 {
			return left
		}
		return right
	}
	return recur(root)
}

// 中序遍历, 迭代实现
func (tree *BinarySearchTreeKthSmallest) KthSmallestIterate(root *nodes.TreeNode, k int) int {
	stack := linkedliststack.New()
	for !stack.Empty() || root != nil {
		if root != nil {
			stack.Push(root)
			root = root.Left
		} else {
			element, _ := stack.Pop()
			root = element.(*nodes.TreeNode)
			k--
			if k == 0 {
				return root.Val
			}
			root = root.Right
		}
	}
	return 0
}

// 恢复二叉搜索树
type BinarySearchTreeRecover struct {}

// 中序遍历, 递归实现
func (tree *BinarySearchTreeRecover) RecoverTreeRecur(root *nodes.TreeNode)  {
	var previous *nodes.TreeNode = nil
	var first, second *nodes.TreeNode = nil, nil
	var recur func(node *nodes.TreeNode)
	recur = func(node *nodes.TreeNode) {
		if node == nil {
			return
		}
		recur(node.Left)
		if previous != nil && previous.Val > node.Val {
			if first == nil {
				first = previous
			}
			second = node
		}
		previous = node
		recur(node.Right)
	}
	recur(root)
	if first != nil && second != nil {
		first.Val, second.Val = second.Val, first.Val
	}
}

// 中序遍历, 迭代实现
func (tree *BinarySearchTreeRecover) RecoverTreeIterate(root *nodes.TreeNode) {
	var previous *nodes.TreeNode = nil
	var first, second *nodes.TreeNode = nil, nil
	stack := linkedliststack.New()
	for !stack.Empty() || root != nil {
		if root != nil {
			stack.Push(root)
			root = root.Left
		} else {
			element, _ := stack.Pop()
			root = element.(*nodes.TreeNode)
			if previous != nil && previous.Val > root.Val {
				if first == nil {
					first = previous
				}
				second = root
			}
			previous = root
			root = root.Right
		}
	}
	if first != nil && second != nil {
		first.Val, second.Val = second.Val, first.Val
	}
}
