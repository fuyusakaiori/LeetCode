package chapter03

import (
	"github.com/emirpasic/gods/queues/linkedlistqueue"
	nodes "leetcode-go/node"
	"leetcode-go/util"
)

// 二叉树的深度
type BinaryTreeDepth struct{}

type TreeNodeDepthWrapper struct {
	depth int
	node  *nodes.TreeNode
}

// 最大深度: 深度优先
func (tree *BinaryTreeDepth) MaxDepthDfs(root *nodes.TreeNode) int {
	if root == nil {
		return 0
	}
	leftDepth := tree.MaxDepthDfs(root.Left)
	rightDepth := tree.MaxDepthDfs(root.Right)
	return max(leftDepth, rightDepth) + 1
}

// 最大深度: 广度优先, 层数就是最大深度, 可以采用封装的方式也可以不采用, 但是推荐使用封装的方式, 简单
func (tree *BinaryTreeDepth) MaxDepthBfs(root *nodes.TreeNode) int {
	depth := 0
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(&TreeNodeDepthWrapper{depth: 1, node: root})
	}
	for !queue.Empty() {
		value, _ := queue.Dequeue()
		wrapper := value.(*TreeNodeDepthWrapper)
		depth = max(wrapper.depth, depth)
		if wrapper.node.Left != nil {
			queue.Enqueue(&TreeNodeDepthWrapper{depth: wrapper.depth + 1, node: wrapper.node.Left})
		}
		if wrapper.node.Right != nil {
			queue.Enqueue(&TreeNodeDepthWrapper{depth: wrapper.depth + 1, node: wrapper.node.Right})
		}
	}
	return depth
}

// 最小深度: 深度优先
func (tree *BinaryTreeDepth) MinDepthDfs(root *nodes.TreeNode) int {
	if root == nil {
		return 0
	}
	leftDepth := tree.MinDepthDfs(root.Left)
	rightDepth := tree.MinDepthDfs(root.Right)
	if leftDepth != 0 && rightDepth != 0 {
		return min(leftDepth, rightDepth) + 1
	}
	if leftDepth != 0 {
		return leftDepth + 1
	}
	return rightDepth + 1
}

// 最小深度: 广度优先, 首次遇到叶子节点就可以直接返回, 可以采用封装的方式也可以不采用, 但是推荐使用封装的方式, 简单
func (tree *BinaryTreeDepth) MinDepthBfs(root *nodes.TreeNode) int {
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(&TreeNodeDepthWrapper{depth: 1, node: root})
	}
	for !queue.Empty() {
		value, _ := queue.Dequeue()
		wrapper := value.(*TreeNodeDepthWrapper)
		if wrapper.node.Left == nil && wrapper.node.Right == nil {
			return wrapper.depth
		}
		if wrapper.node.Left != nil {
			queue.Enqueue(&TreeNodeDepthWrapper{depth: wrapper.depth + 1, node: wrapper.node.Left})
		}
		if wrapper.node.Right != nil {
			queue.Enqueue(&TreeNodeDepthWrapper{depth: wrapper.depth + 1, node: wrapper.node.Right})
		}
	}
	return 0
}

// 二叉树的宽度
type BinaryTreeWidth struct{}

type TreeNodeWidthWrapper struct {
	index int
	level int
	node  *nodes.TreeNode
}

// 最大宽度: 类广度优先, 递归实现
func (tree *BinaryTreeWidth) MaxWidthRecur(root *nodes.TreeNode) int {
	var recur func(node *nodes.TreeNode, index, level int, levels map[int]int) int
	recur = func(node *nodes.TreeNode, index, level int, levels map[int]int) int {
		if node == nil {
			return 0
		}
		if _, ok := levels[level]; !ok {
			levels[level] = index
		}
		leftMax := recur(node.Left, index*2, level+1, levels)
		rightMax := recur(node.Right, index*2+1, level+1, levels)
		return max(index-levels[level]+1, max(leftMax, rightMax))
	}
	return recur(root, 0, 0, make(map[int]int))
}

// 最大宽度: 广度优先, 迭代实现
func (tree *BinaryTreeWidth) MaxWidthIterate(root *nodes.TreeNode) int {
	width := 0
	start := &TreeNodeWidthWrapper{index: 0, level: 0, node: root}
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(start)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		wrapper := element.(*TreeNodeWidthWrapper)
		if wrapper.node != nil {
			queue.Enqueue(&TreeNodeWidthWrapper{index: wrapper.index * 2, level: wrapper.level + 1, node: wrapper.node.Left})
			queue.Enqueue(&TreeNodeWidthWrapper{index: wrapper.index*2 + 1, level: wrapper.level + 1, node: wrapper.node.Right})
			if start.level != wrapper.level {
				start = wrapper
			}
			width = max(width, wrapper.index-start.index+1)
		}
	}
	return width
}

// 二叉树的层平均值
type BinaryTreeLevelAverage struct{}

// 层平均值: 类广度优先, 递归实现
func (tree *BinaryTreeLevelAverage) AverageOfLevelsRecur(root *nodes.TreeNode) []float64 {
	averages := make([]float64, 0)
	levels := make(map[int][]int)
	var recur func(node *nodes.TreeNode, level int, levels map[int][]int)
	recur = func(node *nodes.TreeNode, level int, levels map[int][]int) {
		if node == nil {
			return
		}
		levels[level] = append(levels[level], node.Val)
		recur(node.Left, level+1, levels)
		recur(node.Right, level+1, levels)
	}
	recur(root, 0, levels)
	// NOTE: 注意哈表示是乱序的, 不要依赖顺序
	for index := 0; index < len(levels); index++ {
		sum := 0
		for _, element := range levels[index] {
			sum += element
		}
		averages = append(averages, float64(sum)/float64(len(levels[index])))
	}
	return averages
}

// 层平均值: 广度优先, 迭代实现
func (tree *BinaryTreeLevelAverage) AverageOfLevelsIterate(root *nodes.TreeNode) []float64 {
	sum, count := float64(0), float64(0)
	currentLevelEnd := root
	nextLevelEnd := &nodes.TreeNode{}
	averages := make([]float64, 0)
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		root = element.(*nodes.TreeNode)
		sum += float64(root.Val)
		count += 1
		if root.Left != nil {
			queue.Enqueue(root.Left)
			nextLevelEnd = root.Left
		}
		if root.Right != nil {
			queue.Enqueue(root.Right)
			nextLevelEnd = root.Right
		}
		if currentLevelEnd == root {
			averages = append(averages, sum/count)
			sum, count = 0, 0
			currentLevelEnd = nextLevelEnd
		}
	}
	return averages
}

// 二叉树的直径
type BinaryTreeDiameter struct{}

// 最大直径: 递归实现, 深度优先, 每次返回最大高度的子树, 然后每次递归都计算最大的直径就行
func (tree BinaryTreeDiameter) DiameterOfBinaryTree(root *nodes.TreeNode) int {
	diameter := 0
	var recur func(node *nodes.TreeNode) int
	recur = func(node *nodes.TreeNode) int {
		if node == nil {
			return 0
		}
		left := recur(node.Left)
		right := recur(node.Right)
		diameter = max(diameter, left+right)
		return max(left, right) + 1
	}
	recur(root)
	return diameter

}

// 二叉树的坡度
type BinaryTreeTilt struct{}

// 最大坡度: 递归实现, 深度优先, 每次返回子树节点之和, 然后计算差值
func (tree *BinaryTreeTilt) findTilt(root *nodes.TreeNode) int {
	tilt := 0
	var recur func(node *nodes.TreeNode) int
	recur = func(node *nodes.TreeNode) int {
		if node == nil {
			return 0
		}
		left := recur(node.Left)
		right := recur(node.Right)
		tilt += util.Abs(left - right)
		return left + right + node.Val
	}
	recur(root)
	return tilt
}

// 二叉树的左叶子之和
type BinaryTreeLeftLeaf struct{}

// 左叶子之和: 递归实现, 深度优先
func (tree *BinaryTreeLeftLeaf) SumOfLeftLeavesDfs(root *nodes.TreeNode) int {
	var recur func(current, parent *nodes.TreeNode) int
	recur = func(current, parent *nodes.TreeNode) int {
		if current == nil {
			return 0
		}
		if current.Left == nil && current.Right == nil && current == parent.Left {
			return current.Val
		}
		return recur(current.Left, current) + recur(current.Right, current)
	}
	return recur(root, root)
}

// 左叶子之和: 迭代实现, 广度优先
func (tree *BinaryTreeLeftLeaf) SumOfLeftLeavesBfs(root *nodes.TreeNode) int {
	sum := 0
	currentLevelEnd := root
	nextLevelEnd := &nodes.TreeNode{}
	queue := linkedlistqueue.New()
	if root != nil {
		queue.Enqueue(root)
	}
	for !queue.Empty() {
		element, _ := queue.Dequeue()
		root = element.(*nodes.TreeNode)
		if root.Left != nil {
			if tree.IsLeaf(root.Left) {
				sum += root.Left.Val
			} else {
				// NOTE: 已经知道是叶子节点就可以不用继续入队遍历了
				queue.Enqueue(root.Left)
				nextLevelEnd = root.Left
			}
		}
		if root.Right != nil {
			if !tree.IsLeaf(root.Right) {
				queue.Enqueue(root.Right)
				nextLevelEnd = root.Right
			}
		}
		if currentLevelEnd == root {
			currentLevelEnd = nextLevelEnd
		}
	}
	return sum
}

func (tree *BinaryTreeLeftLeaf) IsLeaf(root *nodes.TreeNode) bool {
	return root.Left == nil && root.Right == nil
}
