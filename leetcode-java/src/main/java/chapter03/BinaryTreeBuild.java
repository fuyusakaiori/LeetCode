package chapter03;

import utils.TreeNode;

import java.util.*;

/**
 * <p>构建二叉树</p>
 * <p>1. 从前序和中序遍历构建二叉树</p>
 * <p>2. 从后序和中序遍历构建二叉树</p>
 * <p>3. 构建最大二叉树</p>
 * <p>4. 合并二叉树</p>
 */
public class BinaryTreeBuild {
    
    /**
     * 前序数组 + 中序数组构建二叉树
     * <p>核心思路: 以中序遍历 + 前序遍历为例, 中序遍历 + 后序遍历是完全相同的</p>
     * <p>1. 前序遍历中的每个结点都可以看做是根结点, 而中序遍历中的根结点左右两侧一定是左右子树</p>
     * <p>2. 那么我们只需要找到根结点在中序遍历中的位置, 然后连接上左右两侧的子树就可以了</p>
     * <p>3. 子树内部就继续采用递归的形式生成</p>
     * <p>+--------------------------------------+</p>
     * <p>pre: 3 9 20 15 7</p>
     * <p>infix: 9 |3| 15 20 7</p>
     * <p>left: 9, right: 15 20 7</p>
     * <p>每个子树内部依旧采用这种方法划分就行</p>
     * <p>+--------------------------------------+</p>
     * <p>注: 前序和后序是没有办法生成唯一的二叉树的, 根结点是不确定的</p>
     * <p>注: 数组中的元素不能够重复, 否则根结点也不确定</p>
     */
    public static class PreOrderBuildBinaryTree {

        /**
         * 深度优先, 迭代实现
         */
        public static TreeNode buildTreeIterate(int[] infixOrder, int[] preOrder) {
            int infixIndex = 0, preIndex = 0;
            TreeNode root = new TreeNode(preOrder[preIndex++]);
            LinkedList<TreeNode> stack = new LinkedList<>();
            stack.push(root);
            while (preIndex < preOrder.length) {
                TreeNode node = stack.peek();
                // 不断遍历前序数组, 直到发现和中序数组相同的元素, 那么就证明找到了根节点左右子树的分界点
                if (node.value != infixOrder[infixIndex]) {
                    // 第一个不相同的节点就是根节点的左子树
                    node.left = new TreeNode(preOrder[preIndex]);
                    stack.push(node.left);
                } else {
                    while (!stack.isEmpty() && infixOrder[infixIndex] == stack.peek().value) {
                        stack.pop();
                        infixIndex++;
                    }
                    // 循环弹出后发现的第二个不相同的节点就是根节点的右子树
                    node.right = new TreeNode(preOrder[preIndex]);
                    stack.push(node.right);
                }
                preIndex++;
            }
            return root;
        }

        /**
         * 深度优先, 递归实现
         */
        public static TreeNode buildTreeRecur(int[] infixOrder, int[] preOrder) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int index = 0; index < infixOrder.length; index++) {
                map.put(infixOrder[index], index);
            }
            return buildTreeRecur(0, 0, preOrder.length - 1, preOrder, map);
        }

        public static TreeNode buildTreeRecur(int index, int left, int right, int[] preOrder, Map<Integer, Integer> map) {
            if (left > right) {
                return null;
            }
            TreeNode root = new TreeNode(preOrder[index]);
            int mid = map.get(preOrder[index]);
            // 左子树递增就可以遍历
            root.left = buildTreeRecur(index + 1, left, mid - 1, preOrder, map);
            // 右子树需要跳过所有子树的节点才可以遍历
            root.right = buildTreeRecur(index + (mid - left) + 1, mid + 1, right, preOrder, map);
            return root;
        }

    }

    /**
     * 后序数组 + 中序数组构建二叉树
     */
    public static class PostOrderBuildBinaryTree {

        /**
         * 深度优先, 迭代实现
         */
        public static TreeNode buildTreeIterate(int[] infixOrder, int[] postOrder) {
            int infixIndex = infixOrder.length - 1, postIndex = postOrder.length - 1;
            TreeNode root = new TreeNode(postOrder[postIndex--]);
            LinkedList<TreeNode> stack = new LinkedList<>();
            stack.push(root);
            while (postIndex >= 0) {
                TreeNode node = stack.peek();
                if (node.value != infixOrder[infixIndex]) {
                    node.right = new TreeNode(postOrder[postIndex]);
                    stack.push(node.right);
                } else {
                    // 注意: 直接用 node 看的就不是当前的 value
                    while (!stack.isEmpty() && stack.peek().value == infixOrder[infixIndex]) {
                        node = stack.pop();
                        infixIndex--;
                    }
                    node.left = new TreeNode(postOrder[postIndex]);
                    stack.push(node.left);
                }
                postIndex--;
            }
            return root;
        }

        /**
         * 深度优先, 递归实现
         */
        public static TreeNode buildTreeRecur(int[] infixOrder, int[] postOrder) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int index = 0; index < infixOrder.length; index++) {
                map.put(infixOrder[index], index);
            }
            // index 指针遍历的是后序数组, left 和 right 指针遍历的中序数组
            return buildTreeRecur(postOrder.length - 1, 0, infixOrder.length - 1, postOrder, map);
        }

        public static TreeNode buildTreeRecur(int index, int left, int right, int[] postOrder, Map<Integer, Integer> map) {
            if (left > right) {
                return null;
            }
            int mid = map.get(postOrder[index]);
            TreeNode root = new TreeNode(postOrder[index]);
            // 左子树需要跳过右子树递减才能遍历: right - mid 是右子树的节点数量, index - (right - mid) 就会跳过右子树的所有节点
            root.left = buildTreeRecur(index - (right - mid) - 1, left, mid - 1, postOrder, map);
            // 右子树递减就可以遍历
            root.right = buildTreeRecur(index - 1, mid + 1, right, postOrder, map);
            return root;
        }
        
    }

    /**
     * 构建最大二叉树
     */
    public static class MaxBuildBinaryTree {

        /**
         * 深度优先, 迭代实现, 单调栈
         * <p>1. 从左向右遍历, 如果当前元素小于前驱元素, 那么当前元素只能是前驱元素的右子节点;</p>
         * <p>2. 如果当前元素大于前驱元素, 那么前驱元素只能是当前元素的左子结点, 但不一定是直接相连的左子结点</p>
         */
        public static TreeNode buildTreeStack(int[] nums) {
            TreeNode root = null;
            LinkedList<TreeNode> stack = new LinkedList<>();
            for (int index = 0; index < nums.length; index++) {
                root = new TreeNode(nums[index]);
                // 如果发现当前元素比栈顶元素大, 那么证明当前元素可以作为栈顶元素的根节点, 且栈顶元素必然为左子结点
                while (!stack.isEmpty() && stack.peek().value < root.value) {
                    TreeNode node = stack.pop();
                    // 再次检查新的栈顶元素是否比当前元素大:
                    if (!stack.isEmpty() && stack.peek().value < root.value) {
                        // 如果新的栈顶元素比当前元素小, 那么只能将出栈的元素变成新栈顶元素的右子节点, 因为新的栈顶元素可能才是当前元素的左子结点
                        stack.peek().right = node;
                    } else {
                        // 如果新的栈顶元素比当前元素大, 那么证明出栈的元素只能是当前节点的左子结点
                        root.left = node;
                    }
                }
                stack.push(root);
            }
            // 如果完全单调递减, 那么栈中肯定会有剩余的元素, 需要单独处理
            while (!stack.isEmpty()) {
                root = stack.pop();
                if (!stack.isEmpty()) {
                    stack.peek().right = root;
                }
            }
            return root;
        }

        /**
         * 深度优先, 递归实现
         */
        public static TreeNode buildTreeRecur(int[] nums) {
            return buildTreeRecur(0, nums.length - 1, nums);
        }

        public static TreeNode buildTreeRecur(int left, int right, int[] nums) {
            if (left > right) {
                return null;
            }
            // 获取最大的元素的 index
            int maxNum = Integer.MIN_VALUE;
            int maxIndex = 0;
            for (int index = left; index <= right; index++) {
                if (nums[index] > maxNum) {
                    maxNum = nums[index];
                    maxIndex = index;
                }
            }
            TreeNode root = new TreeNode(maxNum);
            root.left = buildTreeRecur(left, maxIndex - 1, nums);
            root.right = buildTreeRecur(maxIndex + 1, right, nums);
            return root;
        }

    }

    /**
     * 合并二叉树
     */
    public static class MergeBinaryTree {

        /**
         * 深度优先, 递归实现
         */
        public static TreeNode mergeTreesDfs(TreeNode first, TreeNode second) {
            if (first == null || second == null) {
                return first != null ? first : second;
            }
            TreeNode root = new TreeNode(first.value + second.value);
            root.left = mergeTreesDfs(first.left, second.left);
            root.right = mergeTreesDfs(first.right, second.right);
            return root;
        }

        /**
         * 广度优先, 迭代实现
         */
        public static TreeNode mergeTreesBfs(TreeNode first, TreeNode second) {
            if (first == null) {
                return second;
            }
            if (second == null) {
                return first;
            }
            Queue<TreeNode> queue = new LinkedList<>();
            Queue<TreeNode> firstQueue = new LinkedList<>();
            Queue<TreeNode> secondQueue = new LinkedList<>();
            TreeNode root = new TreeNode(first.value + second.value);
            queue.offer(root);
            firstQueue.offer(first);
            secondQueue.offer(second);
            while (!firstQueue.isEmpty() || !secondQueue.isEmpty()) {
                TreeNode node = queue.poll(), firstNode = firstQueue.poll(), secondNode = secondQueue.poll();
                TreeNode firstLeft = firstNode.left, firstRight = firstNode.right, secondLeft = secondNode.left, secondRight = secondNode.right;
                if (firstLeft != null || secondLeft != null) {
                    if (firstLeft != null && secondLeft != null) {
                        TreeNode left = new TreeNode(firstLeft.value + secondLeft.value);
                        node.left = left;
                        queue.offer(left);
                        firstQueue.offer(firstLeft);
                        secondQueue.offer(secondLeft);
                    } else if (firstLeft != null) {
                        // 注意: 这个地方没有必要再入队遍历了, 和合并链表的原理类似
                        node.left = firstLeft;
                    } else {
                        node.left = secondLeft;
                    }
                }
                if (firstRight != null || secondRight != null) {
                    if (firstRight != null && secondRight != null) {
                        TreeNode right = new TreeNode(firstRight.value + secondRight.value);
                        node.right = right;
                        queue.offer(right);
                        firstQueue.offer(firstRight);
                        secondQueue.offer(secondRight);
                    } else if (firstRight != null) {
                        node.right = firstRight;
                    } else {
                        node.right = secondRight;
                    }
                }
            }
            return root;
        }

    }
}
