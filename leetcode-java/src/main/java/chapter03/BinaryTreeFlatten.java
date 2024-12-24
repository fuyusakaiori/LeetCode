package chapter03;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树展开为链表
 */
public class BinaryTreeFlatten {

    private static TreeNode previous = null;

    /**
     * 前序遍历, 深度优先, 迭代实现
     */
    public static void flattenIterate(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            root = stack.pop();
            nodes.add(root);
            if (root.right != null) {
                stack.push(root.right);
            }
            if (root.left != null) {
                stack.push(root.left);
            }
        }
        for (int index = 0; index < nodes.size() - 1; index++) {
            nodes.get(index).left = null;
            nodes.get(index).right = nodes.get(index + 1);
        }
    }

    /**
     * 前序遍历, 深度优先, 递归实现:
     */
    public static void flattenRecur(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        if (previous == null) {
            root.right = left;
        } else {
            previous.right = root;
        }
        root.left = null;
        previous = root;
        flattenRecur(left);
        flattenRecur(right);
    }


    /**
     * 迭代实现: O(1) 空间复杂度
     */
    public static void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode current = root;
        while (current != null) {
            if (current.left != null) {
                TreeNode previous = current.left;
                while (previous.right != null) {
                    previous = previous.right;
                }
                previous.right = current.right;
                current.right = current.left;
                current.left = null;
            }
            current = current.right;
        }
    }
}
