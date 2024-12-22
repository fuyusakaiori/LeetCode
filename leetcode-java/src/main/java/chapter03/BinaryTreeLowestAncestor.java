package chapter03;

import utils.TreeNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>二叉树的最近公共祖先</p>
 * <p>1. 二叉搜索树的最近公共祖先</p>
 * <p>2. 二叉树的最近公共祖先</p>
 */
public class BinaryTreeLowestAncestor {

    /**
     * 二叉搜索树的最近公共祖先: 和二叉树的最近公共祖先类似, 不过可以利用二叉搜索树的性质, 会更简单
     */
    public static class BinarySearchTree {

        /**
         * 类后序遍历, 深度优先, 递归实现
         * <p>二叉搜索树的性质</p>
         * <p>1. 如果两个结点的值都小于根结点, 那么必然最近的公共祖先也一定在左子树中</p>
         * <p>2 如果两个结点的值都大于根结点, 那么必然最近的公共祖先一定在右子树中</p>
         * <p>3 如果不满足的上述的情况, 那么就证明出现了分叉点, 这个分叉点实际上就是公共祖先</p>
         */
        public static TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode first, TreeNode second) {
            // 注意: 这里的判断逻辑是被简化了
            if (first.value < root.value && second.value < root.value) {
                return lowestCommonAncestorRecursive(root.left, first, second);
            }
            if (first.value > root.value && second.value > root.value) {
                return lowestCommonAncestorRecursive(root.right, first, second);
            }
            return root;
        }

        /**
         * 深度优先, 迭代实现
         */
        public static TreeNode lowestCommonAncestorLoop(TreeNode root, TreeNode first, TreeNode second) {
            while (true) {
                if (first.value < root.value && second.value < root.value) {
                    root = root.left;
                } else if (first.value > root.value && second.value > root.value) {
                    root = root.right;
                } else {
                    return root;
                }
            }
        }


    }

    /**
     * 二叉树的最近公共祖先
     */
    public static class BinaryTree {
        /**
         * 后序遍历, 深度优先, 递归实现
         * <p>1. 如果找到符合要求的结点就立刻返回这个结点, 如果遍历到叶子结点都没有, 那就返回空值</p>
         * <p>2.根结点会受到两个子结点: </p>
         * <p>2.1. 如果其中一个子结点为空, 那么证明那个子树里面没有符合要求的结点, 自己不是最近的公共祖先</p>
         * <p>2.2. 如果两个子结点都为空, 也是同理</p>
         * <p>2.3. 如果两个子结点不为空, 那么证明自己就是最近的公共祖先</p>
         */
        public static TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode first, TreeNode second) {
            if (root == null) {
                return null;
            }
            if (root == first || root == second) {
                return root;
            }
            TreeNode left = lowestCommonAncestorRecursive(root.left, first, second);
            TreeNode right = lowestCommonAncestorRecursive(root.right, first, second);
            if (left != null && right != null) {
                return root;
            }
            return left != null ? left : right;
        }

        /**
         * 深度优先, 迭代实现, 借助哈希表记录父节点, 然后目标节点自底向上遍历
         * <p>1. 找路径, 然后比较: 这两个题实际上都可以共用这个思路</p>
         * <p>2. 采用哈希表记录结点之间的父子关系</p>
         * <p>3. 然后从下向上开始遍历, 获取到根结点到达其中一个结点的路径</p>
         * <p>4. 然后从另一个结点开始, 从下向上遍历, 发现重复的就是公共结点</p>
         */
        public static TreeNode lowestCommonAncestorLoop(TreeNode root, TreeNode first, TreeNode second) {
            // 记录从子节点到根节点的路径, 或者说记录子节点的所有祖先节点
            Set<TreeNode> set = new HashSet<>();
            // 记录所有节点的父节点
            Map<TreeNode, TreeNode> map = new HashMap<>();
            // 开始记录
            lowestCommonAncestorLoop(root, map);
            // 从第一个子节点开始从下向上遍历
            while (first != root) {
                set.add(first);
                first = map.get(first);
            }
            // 从第二个子节点开始从下向上遍历
            while (second != root) {
                if (set.contains(second)) {
                    return second;
                }
                second = map.get(second);
            }
            return root;
        }

        public static void lowestCommonAncestorLoop(TreeNode root, Map<TreeNode, TreeNode> map) {
            if (root == null) {
                return;
            }
            map.put(root.left, root);
            map.put(root.right, root);
            lowestCommonAncestorLoop(root.left, map);
            lowestCommonAncestorLoop(root.right, map);
        }


    }

}
