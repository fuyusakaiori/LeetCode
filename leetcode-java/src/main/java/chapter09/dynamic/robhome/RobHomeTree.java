package chapter09.dynamic.robhome;

import utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>打家劫舍 III</p>
 */
public class RobHomeTree {

    /**
     * <p>记忆化搜索: 如果无法使用数组作为记忆化表, 可以使用哈希表</p>
     */
    public static int rob(TreeNode root) {
        Map<TreeNode, Integer> dp = new HashMap<>();
        return rob(root, dp);
    }

    public static int rob(TreeNode root, Map<TreeNode, Integer> dp) {
        if (root == null) {
            return 0;
        }
        if (dp.containsKey(root)) {
            return dp.get(root);
        }
        int first = rob(root.left, dp) + rob(root.right, dp);
        int second = root.value;
        if (root.left != null) {
            second += rob(root.left.left, dp) + rob(root.left.right, dp);
        }
        if (root.right != null) {
            second += rob(root.right.left, dp) + rob(root.right.right, dp);
        }
        dp.put(root, Math.max(first, second));
        return dp.get(root);
    }

}
