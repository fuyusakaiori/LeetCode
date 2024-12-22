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
     * 迭代实现: 前序遍历
     */
    public static void flattenLoop(TreeNode root) {
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
     * 递归实现:
     * <p>1. 前序遍历</p>
     * <p>2. 后序遍历</p>
     */
    public static void flattenRecursive(TreeNode root) {
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
        flattenRecursive(left);
        flattenRecursive(right);
    }


    /**
     * 迭代实现: O(1) 空间复杂度
     */
    public static void flatten(TreeNode root) {

    }
}
