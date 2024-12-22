package chapter03;

import utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>1. 填充每个节点的下一个右侧节点指针</p>
 * <p>2. 填充每个节点的下一个右侧节点指针 II</p>
 * <h2>填充每个结点的 Next 指针</h2>
 * <p>1. 层序遍历非常简单</p>
 * <p>2. 深度遍历需要使用已经建立好的 Next 指针</p>
 * <p>两个问题</p>
 * <p>1. 树为满二叉树的时候, 填充 Next 指针</p>
 * <p>2. 树为普通二叉树的时候, 填充 Next 指针</p>
 * <p>总结: 填充指针这种类型的题目, 通常都可以考虑在填充之后立刻使用这些指针</p>
 */
public class BinaryTreeConnectNext {

    /**
     * 填充每个节点的下一个右侧节点指针: 满二叉树
     */
    public static class FullBinaryTree {

        /**
         * 层序遍历, 广度优先
         */
        public static TreeNode connectNextBFS(TreeNode root) {
            TreeNode previous = null;
            TreeNode currentLevelEnd = root;
            TreeNode nextLevelEnd = null;
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (previous != null) {
                    previous.next = node;
                }
                previous = node;
                if (node.left != null) {
                    queue.offer(node.left);
                    nextLevelEnd = node.left;
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    nextLevelEnd = node.right;
                }
                if (currentLevelEnd == root) {
                    previous = null;
                    currentLevelEnd = nextLevelEnd;
                }
            }
            return root;
        }

        /**
         * 类前序遍历, 深度优先, 空间复杂度在不计压栈空间时可以认为是 O(1)
         */
        public static TreeNode connectNextDFS(TreeNode root) {
            connectNextDFS(root, null);
            return root;
        }

        // 思路: 记录自己的相邻节点, 然后进行连接
        public static void connectNextDFS(TreeNode root, TreeNode neighbor) {
            if (root == null) {
                return;
            }
            root.next = neighbor;
            connectNextDFS(root.left, root.right);
            // 相邻节点的左子结点就是下一层节点的相邻节点
            connectNextDFS(root.right, neighbor != null ? neighbor.left : null);
        }

        /**
         * 类层序遍历, 广度优先: 真正意义上空间复杂度为 O(1)
         */
        public static TreeNode connectNextLoop(TreeNode root) {
            if (root == null) {
                return null;
            }
            TreeNode current = root;
            while (current.left != null) {
                // 将当前父节点作为链表的头结点
                TreeNode head = current;
                // 遍历链表
                while (head != null) {
                    // 将父节点的左子结点的 next 指向父节点的右子节点
                    head.left.next = head.right;
                    // 如果父节点有 next, 那么就可以将父节点的右子节点的 next 指向相邻节点的左子结点
                    if (head.next != null) {
                        head.right.next = head.next.left;
                    }
                    head = head.next;
                }
                // 从下一层的最左节点继续
                current = current.left;
            }
            return root;
        }

    }

    /**
     * 填充每个节点的下一个右侧节点指针 II: 普通二叉树
     */
    public static class BinaryTree {

        /**
         * 层序遍历, 广度优先, 和满二叉树完全相同的做法
         */
        public static TreeNode connectNextBFS(TreeNode root) {
            TreeNode previous = null;
            TreeNode currentLevelEnd = root;
            TreeNode nextLevelEnd = null;
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (previous != null) {
                    previous.next = node;
                }
                previous = node;
                if (node.left != null) {
                    queue.offer(node.left);
                    nextLevelEnd = node.left;
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    nextLevelEnd = node.right;
                }
                if (currentLevelEnd == root) {
                    previous = null;
                    currentLevelEnd = nextLevelEnd;
                }
            }
            return root;
        }

        /**
         * 层序遍历, 广度优先, 真正意义上空间复杂度为 O(1)
         */
        public static TreeNode connectNextLoop(TreeNode root) {
            if (root == null) {
                return null;
            }
            TreeNode current = root;
            while (current != null) {
                TreeNode head = current;
                // 记录前驱节点的目的, 因为需要向后遍历链表找到有子节点的父节点, 所以需要记录前驱节点, 从而连接起来
                // 记录后继节点的目的, 因为不清楚父节点有没有子节点, 所以记录下一层从哪里开始, 是从左子结点开始还是右子节点
                TreeNode previous = null, next = null;
                while (head != null) {
                    if (head.left != null) {
                        if (previous != null) {
                            previous.next = head.left;
                        }
                        if (next == null) {
                            next = head;
                        }
                        previous = head.left;
                    }
                    if (head.right != null) {
                        if (previous != null) {
                            previous.next = head.right;
                        }
                        if (next == null) {
                            next = head;
                        }
                        previous = head.right;
                    }
                    head = head.next;
                }
                current = next;
            }
            return root;
        }

    }

}
