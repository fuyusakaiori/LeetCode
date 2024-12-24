package chapter03

import nodes "leetcode-go/node"

// 翻转二叉树
type BinaryTreeInvert struct {}

// 递归实现
func (tree *BinaryTreeInvert) InvertTree(root *nodes.TreeNode) *nodes.TreeNode {
	if root == nil {
		return nil
	}
	left := tree.InvertTree(root.Left)
	right := tree.InvertTree(root.Right)
	root.Left = right
	root.Right = left
	return root
}