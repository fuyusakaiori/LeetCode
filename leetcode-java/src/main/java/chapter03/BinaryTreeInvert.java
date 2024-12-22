package chapter03;

import utils.TreeNode;

/**
 * 翻转二叉树
 */
public class BinaryTreeInvert {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

}
