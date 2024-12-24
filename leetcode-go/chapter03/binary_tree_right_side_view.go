package chapter03

import (
	"github.com/emirpasic/gods/queues/linkedlistqueue"
	nodes "leetcode-go/node"
)

// 二叉树的右/左视图
type BinaryTreeRightSideView struct {}

// 深度优先, 递归实现
func (tree *BinaryTreeRightSideView) RightSideViewDfs(root *nodes.TreeNode) []int {
	views := make([]int, 0)
	levels := make(map[int]int)
	var recur func(node *nodes.TreeNode, level int, levels map[int]int)
	recur = func(node *nodes.TreeNode, level int, levels map[int]int) {
		if node == nil {
			return
		}
		if _, ok := levels[level]; !ok {
			levels[level] = node.Val
		}
		recur(node.Right, level + 1, levels)
		recur(node.Left, level + 1, levels)
	}
	recur(root, 0, levels)
	for index := 0; index < len(levels); index++ {
		views = append(views, levels[index])
	}
	return views
}

// 层序遍历, 广度优先, 迭代实现
func (tree *BinaryTreeRightSideView) RightSideViewBfs(root *nodes.TreeNode) []int {
	var currentLevelEnd *nodes.TreeNode = root
	var nextLevelEnd, rightSideView *nodes.TreeNode = nil, nil
	rightSideViews := make([]int, 0)
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		root = element.(*nodes.TreeNode)
		if rightSideView == nil {
			rightSideView = root
		}
		if root.Right != nil {
			queue.Enqueue(root.Right)
			nextLevelEnd = root.Right
		}
		if root.Left != nil {
			queue.Enqueue(root.Left)
			nextLevelEnd = root.Left
		}
		if currentLevelEnd == root {
			rightSideViews = append(rightSideViews, rightSideView.Val)
			rightSideView = nil
			currentLevelEnd = nextLevelEnd
		}
	}
	return rightSideViews
}
