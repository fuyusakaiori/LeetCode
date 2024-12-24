package chapter03

import (
	"github.com/emirpasic/gods/queues/linkedlistqueue"
	nodes "leetcode-go/node"
	"strconv"
)

// 路径总和
type BinaryTreePathSumI struct{}

// 深度优先, 递归实现
func (tree *BinaryTreePathSumI) HasPathSumDfs(root *nodes.TreeNode, target int) bool {
	if root == nil {
		return false
	}
	if root.Left == nil && root.Right == nil {
		return target == root.Val
	}
	return tree.HasPathSumDfs(root.Left, target-root.Val) || tree.HasPathSumDfs(root.Right, target-root.Val)
}

// 广度优先, 迭代实现
func (tree *BinaryTreePathSumI) HasPathSumBfs(root *nodes.TreeNode, target int) bool {
	var currentLevelEnd, nextLevelEnd *nodes.TreeNode = root, nil
	sums := linkedlistqueue.New()
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
		sums.Enqueue(root.Val)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		root = element.(*nodes.TreeNode)
		value, _ := sums.Dequeue()
		sum := value.(int)
		if root.Left == nil && root.Right == nil && target == sum {
			return true
		}
		if root.Left != nil {
			queue.Enqueue(root.Left)
			sums.Enqueue(sum + root.Left.Val)
			nextLevelEnd = root.Left
		}
		if root.Right != nil {
			queue.Enqueue(root.Right)
			sums.Enqueue(sum + root.Right.Val)
			nextLevelEnd = root.Right
		}
		if currentLevelEnd == root {
			currentLevelEnd = nextLevelEnd
		}
	}
	return false
}

// 路径总和 II
type BinaryTreePathSumII struct{}

// 深度优先, 递归实现
func (tree *BinaryTreePathSumII) PathSumDfs(root *nodes.TreeNode, target int) [][]int {
	path := make([]int, 0)
	paths := make([][]int, 0)
	var recur func(node *nodes.TreeNode, path []int, target int)
	recur = func(node *nodes.TreeNode, path []int, target int) {
		if node == nil {
			return
		}
		target -= node.Val
		path = append(path, node.Val)
		if node.Left == nil && node.Right == nil && target == 0 {
			paths = append(paths, append([]int{}, path...))
			return
		}
		recur(node.Left, path, target)
		recur(node.Right, path, target)
	}
	recur(root, path, target)
	return paths
}

// 广度优先, 迭代实现
func (tree *BinaryTreePathSumII) PathSumBfs(root *nodes.TreeNode, target int) [][]int {
	return nil
}

// 路径总和 III
type BinaryTreePathSumIII struct{}

// 深度优先, 递归实现
func (tree *BinaryTreePathSumIII) PathSumDfs(root *nodes.TreeNode, target int) int {
	var recur func(node *nodes.TreeNode, flag bool, target int) int
	recur = func(node *nodes.TreeNode, flag bool, target int) int {
		if node == nil {
			return 0
		}
		paths := 0
		if target == node.Val {
			paths++
		}
		if !flag {
			paths += recur(node.Left, false, target) + recur(node.Right, false, target)
		}
		paths += recur(node.Left, true, target - node.Val) + recur(node.Right, true, target - node.Val)
		return paths
	}
	return recur(root, false, target)
}

// 前缀和, 深度优先, 递归实现
func (tree *BinaryTreePathSumIII) PathSumPrefix(root *nodes.TreeNode, target int) int {
	prefix := map[int]int{0:1}
	var recur func(node *nodes.TreeNode, current, target int, prefix map[int]int) int
	recur = func(node *nodes.TreeNode, current, target int, prefix map[int]int) int {
		if node == nil {
			return 0
		}
		current += node.Val
		paths := prefix[current - target]
		prefix[current]++
		paths += recur(node.Left, current, target, prefix)
		paths += recur(node.Right, current, target, prefix)
		prefix[current]--
		return paths
	}
	return recur(root, 0, target, prefix)
}

// 二叉树的所有路径
type BinaryTreePathAll struct{}

// 深度优先, 递归实现
func (tree *BinaryTreePathAll) BinaryTreePathsDfs(root *nodes.TreeNode) []string {
	paths := make([]string, 0)
	var recur func(node *nodes.TreeNode, path string)
	recur = func(node *nodes.TreeNode, path string) {
		if node == nil {
			return
		}
		path += strconv.Itoa(node.Val)
		if node.Left == nil && node.Right == nil {
			paths = append(paths, path)
		} else {
			path += "->"
			recur(node.Left, path)
			recur(node.Right, path)
		}
	}
	recur(root, "")
	return paths
}

// 求根节点到叶子节点的数字之和
type BinaryTreePathSumAll struct{}

// 深度优先, 递归实现
func (tree *BinaryTreePathSumAll) SumNumbersDfs(root *nodes.TreeNode) int {
	sum := 0
	paths := make([]string, 0)
	var recur func(node *nodes.TreeNode, path string)
	recur = func(node *nodes.TreeNode, path string) {
		if node == nil {
			return
		}
		path += strconv.Itoa(node.Val)
		if node.Left == nil && node.Right == nil {
			paths = append(paths, path)
			return
		} else {
			recur(node.Left, path)
			recur(node.Right, path)
		}
	}
	recur(root, "")
	for _, path := range paths {
		number, _ := strconv.Atoi(path)
		sum += number
	}
	return sum
}
