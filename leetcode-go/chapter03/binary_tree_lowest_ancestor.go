package chapter03

import nodes "leetcode-go/node"

// 二叉搜索树的最近公共祖先
type BinarySearchTreeLowestAncestor struct {}

// 递归实现
func (tree *BinarySearchTreeLowestAncestor) LowestCommonAncestorRecur(root, first, second *nodes.TreeNode) *nodes.TreeNode {
	if first.Val < root.Val && second.Val < root.Val {
		return tree.LowestCommonAncestorRecur(root.Left, first, second)
	}
	if first.Val > root.Val && second.Val > root.Val {
		return tree.LowestCommonAncestorRecur(root.Right, first, second)
	}
	return root
}

// 迭代实现
func (tree *BinarySearchTreeLowestAncestor) LowestCommonAncestorIterate(root, first, second *nodes.TreeNode) *nodes.TreeNode {
	for {
		if first.Val < root.Val && second.Val < root.Val {
			root = root.Left
		} else if first.Val > root.Val && second.Val > root.Val {
			root = root.Right
		} else {
			return root
		}
	}
}


// 二叉树的最近公共祖先
type BinaryTreeLowestAncestor struct {}

// 递归实现
func (tree *BinaryTreeLowestAncestor) LowestCommonAncestorRecur(root, first, second *nodes.TreeNode) *nodes.TreeNode {
	if root == nil {
		return nil
	}
	if root == first || root == second {
		return root
	}
	left := tree.LowestCommonAncestorRecur(root.Left, first, second)
	right := tree.LowestCommonAncestorRecur(root.Right, first, second)
	if left != nil && right != nil {
		return root
	}
	if left != nil {
		return left
	}
	return right
}

// 哈希表实现: 通解
func (tree *BinaryTreeLowestAncestor) LowestCommonAncestorHash(root, first, second *nodes.TreeNode) *nodes.TreeNode {
	fathers := make(map[*nodes.TreeNode]*nodes.TreeNode)
	ancestors := make(map[*nodes.TreeNode]struct{})
	tree.RecordFather(root, fathers)
	for first != root {
		ancestors[first] = struct{}{}
		first = fathers[first]
	}
	for second != root {
		if _, ok := ancestors[second]; ok {
			return second
		}
		second = fathers[second]
	}
	return root
}

func (tree *BinaryTreeLowestAncestor) RecordFather(root *nodes.TreeNode, fathers map[*nodes.TreeNode]*nodes.TreeNode)  {
	if root == nil {
		return
	}
	fathers[root.Left] = root
	fathers[root.Right] = root
	tree.RecordFather(root.Left, fathers)
	tree.RecordFather(root.Right, fathers)
}