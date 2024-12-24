package chapter03;

import utils.TreeNode;

import java.util.*;

/**
 * 二叉树的右/左视图
 */
public class BinaryTreeRightSideView {

    /**
     * 深度优先, 递归实现
     */
    public static List<Integer> rightSideViewDfs(TreeNode root) {
        Map<Integer, Integer> views = new HashMap<>();
        rightSideViewDfs(root, 0, views);
        return new ArrayList<>(views.values());
    }

    public static void rightSideViewDfs(TreeNode root, int level, Map<Integer, Integer> views) {
        if (root == null) {
            return;
        }
        views.putIfAbsent(level, root.value);
        rightSideViewDfs(root.right, level + 1, views);
        rightSideViewDfs(root.left, level + 1, views);
    }

    /**
     * 广度优先, 迭代实现
     */
    public static List<Integer> rightSideViewBfs(TreeNode root) {
        TreeNode view = null;
        TreeNode currentLevelEnd = root;
        TreeNode nextLevelEnd = null;
        List<Integer> views = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            root = queue.poll();
            view = view != null ? view : root;
            if (root.left != null) {
                queue.offer(root.left);
                nextLevelEnd = root.left;
            }
            if (root.right != null) {
                queue.offer(root.right);
                nextLevelEnd = root.right;
            }
            if (currentLevelEnd == root) {
                views.add(view.value);
                currentLevelEnd = nextLevelEnd;
                view = null;
            }
        }
        return views;
    }
}
