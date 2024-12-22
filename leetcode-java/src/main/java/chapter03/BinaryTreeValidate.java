package chapter03;

import utils.TreeNode;

import java.util.*;

/**
 * <h2>验证二叉树</h2>
 * <h3>1. 验证二叉搜索树</h3>
 * <h3>2. 验证平衡二叉树</h3>
 * <h3>3. 验证完全二叉树</h3>
 * <h3>4. 验证对称二叉树</h3>
 * <h3>5. 验证两棵树是否相同</h3>
 */
public class BinaryTreeValidate {

    /**
     * 二叉搜索树验证
     * (1) 公式化: 模拟, 封装需要的信息
     * (2) 中序遍历: 二叉搜索树中序遍历必然有序
     */
    public static class BinarySearchTreeValidate {

        public static class TreeNodeWrapper {
            // 子树的最小值
            private final int minValue;
            // 子树的最大值
            private final int maxValue;
            // 是否为二叉搜索树
            private final boolean isValidate;

            public TreeNodeWrapper(int minValue, int maxValue, boolean isValidate) {
                this.minValue = minValue;
                this.maxValue = maxValue;
                this.isValidate = isValidate;
            }
        }

        /**
         * 套路
         * 1. 父结点需要比较左子树返回的最大值和右子树返回的最小值, 然后确定是否有效
         * 2. 这里需要返回三个信息, 而函数只能够有一个返回值, 所以需要封装三个条件
         */
        public static boolean isValidate(TreeNode root) {
            return isValidateFormula(root).isValidate;
        }

        public static TreeNodeWrapper isValidateFormula(TreeNode root) {
            if (root == null) {
                return null;
            }
            // 1. 初始化变量
            boolean isValidate = true;
            int minValue = root.value;
            int maxValue = root.value;
            // 2. 深度遍历
            TreeNodeWrapper left = isValidateFormula(root.left);
            TreeNodeWrapper right = isValidateFormula(root.right);
            // 3. 判断当前子树的最小和最大值, 用于递归返回的时候使用
            if (left != null) {
                // 其实可以只用更新最小值
                minValue = Math.min(minValue, left.minValue);
                maxValue = Math.max(maxValue, left.maxValue);
            }
            if (right != null) {
                // 其实可以只用更新最大值
                minValue = Math.min(minValue, right.minValue);
                maxValue = Math.max(maxValue, right.maxValue);
            }
            // 4. 判断是否为有效的二叉搜索树: 不要直接去判断是否有效, 只需要默认有效, 然后看哪些情况无效就行
            if (left != null && (!left.isValidate || left.maxValue >= root.value)) {
                isValidate = false;
            }
            if (right != null && (!right.isValidate || right.minValue <= root.value)) {
                isValidate = false;
            }
            return new TreeNodeWrapper(minValue, maxValue, isValidate);
        }

        /**
         * 中序遍历: 迭代实现
         */
        public static boolean isValidateLoop(TreeNode root) {
            long previous = Long.MIN_VALUE;
            LinkedList<TreeNode> stack = new LinkedList<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    if (previous >= root.value) {
                        return false;
                    }
                    previous = root.value;
                    root = root.right;
                }
            }
            return true;
        }

        /**
         * 中序遍历: 递归实现
         * 1. 自顶向下的方式, 不停地维护一个区间, 区间具有最大值和最小值
         * 2. 遍历到的结点的值必须在这个区间内, 否则就不满足二叉搜索树的条件
         */
        public static boolean isValidateRecursive(TreeNode root) {
            return isValidateRecursive(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        public static boolean isValidateRecursive(TreeNode root, long minValue, long maxValue) {
            if (root == null) {
                return true;
            }
            if (root.value <= minValue || root.value >= maxValue) {
                return false;
            }
            return isValidateRecursive(root.left, minValue, root.value) && isValidateRecursive(root.right, root.value, maxValue);
        }


    }

    /**
     * 平衡二叉树验证
     * (1) 公式化: 模拟, 封装需要的信息
     * (2) 后序遍历: 平衡二叉树会优先比较左子树和右子树的高度, 所以可以采用类似后序遍历的做法
     */
    public static class BalanceBinaryTreeValidate {

        public static class TreeNodeWrapper {
            private final int height;
            private final boolean isBalanced;

            public TreeNodeWrapper(int height, boolean isBalanced) {
                this.height = height;
                this.isBalanced = isBalanced;
            }
        }

        /**
         * 套路
         */
        public static boolean isBalanced(TreeNode root) {
            return isBalancedFormula(root).isBalanced;
        }

        public static TreeNodeWrapper isBalancedFormula(TreeNode root) {
            if (root == null) {
                return new TreeNodeWrapper(0, true);
            }
            TreeNodeWrapper left = isBalancedFormula(root.left);
            TreeNodeWrapper right = isBalancedFormula(root.right);
            int height = Math.max(left.height, right.height) + 1;
            boolean isBalanced = left.isBalanced && right.isBalanced && Math.abs(left.height - right.height) <= 1;
            return new TreeNodeWrapper(height, isBalanced);
        }

        /**
         * 后序遍历: 递归
         */
        public static boolean isBalancedRecursive(TreeNode root) {
            return isBalancedRecursiveHeight(root) >= 0;
        }

        public static int isBalancedRecursiveHeight(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftHeight = isBalancedRecursiveHeight(root.left);
            int rightHeight = isBalancedRecursiveHeight(root.right);
            if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
                return -1;
            } else {
                return Math.max(leftHeight, rightHeight) + 1;
            }
        }

    }

    /**
     * 完全二叉树验证
     */
    public static class CompleteBinaryTreeValidate {

        public static class TreeNodeWrapper {
            private final int index;
            private final TreeNode node;

            public TreeNodeWrapper(int index, TreeNode node) {
                this.index = index;
                this.node = node;
            }
        }

        /**
         * 层序遍历: 迭代实现
         */
        public static boolean isCompleteTreeLoop(TreeNode root) {
            boolean isComplete = true;
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root.left == null) {
                    isComplete = false;
                } else {
                    if (!isComplete) {
                        return false;
                    }
                    queue.offer(root.left);
                }
                if (root.right == null) {
                    isComplete = false;
                } else {
                    if (!isComplete) {
                        return false;
                    }
                    queue.offer(root.right);
                }
            }
            return true;
        }

        /**
         * 套路
         */
        public static boolean isCompleteFormula(TreeNode root) {
            int index = 0;
            List<TreeNodeWrapper> list = new ArrayList<>();
            if (root != null) {
                list.add(new TreeNodeWrapper(1, root));
            }
            while (index < list.size()) {
                TreeNodeWrapper wrapper = list.get(index++);
                if (wrapper.node != null) {
                    list.add(new TreeNodeWrapper(wrapper.index * 2, wrapper.node.left));
                    list.add(new TreeNodeWrapper(wrapper.index * 2 + 1, wrapper.node.right));
                }
            }
            return list.get(list.size() - 1).index == list.size();
        }

    }

    /**
     * 对称二叉树验证
     */
    public static class SymmetricBinaryTreeValidate {

        /**
         * 递归实现
         */
        public static boolean isSymmetricRecursive(TreeNode root) {
            return isSymmetricRecursive(root.left, root.right);
        }

        public static boolean isSymmetricRecursive(TreeNode left, TreeNode right) {
            if (left == null && right == null) {
                return true;
            }
            if (left != null && right != null) {
                return left.value == right.value &&
                        isSymmetricRecursive(left.left, right.right) && isSymmetricRecursive(left.right, right.left);
            }
            return false;
        }

        /**
         * 迭代实现
         */
        public static boolean isSymmetricLoop(TreeNode root) {
            TreeNode left = null, right = null;
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                 left = queue.poll();
                 right = queue.poll();
                 if (left == null && right == null) {
                     continue;
                 }
                 if ((left == null || right == null) || left.value != right.value) {
                     return false;
                 }
                queue.offer(left.left);
                queue.offer(right.right);
                queue.offer(left.right);
                queue.offer(right.left);
            }
            return true;
        }

    }

    /**
     * 相同的二叉树验证
     */
    public static class SameBinaryTreeValidate {

        public static boolean isSameTree(TreeNode left, TreeNode right) {
            if (left == null && right == null) {
                return true;
            }
            if (left != null && right != null) {
                return left.value == right.value
                        && isSameTree(left.left, right.left) && isSameTree(left.right, right.right);
            }
            return false;
        }

    }

}
