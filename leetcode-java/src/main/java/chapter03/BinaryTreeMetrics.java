package chapter03;

import utils.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>二叉树深度、宽度、平均值、直径/h2>
 * <p>1. 二叉树的最大深度</p>
 * <p>2. 二叉树的最小深度</p>
 * <p>3. 二叉树的最大宽度</p>
 * <p>4. 二叉树层平均值</p>
 * <p>5. 二叉树的直径</p>
 * <p>6. 二叉树的坡度</p>
 * <p>7. 二叉树的左叶子之和</p>
 */
public class BinaryTreeMetrics {

    /**
     * 二叉树的深度: 最大深度和最小深度
     */
    public static class DepthMetric {

        public static class TreeNodeWrapper {
            private final int depth;
            private final TreeNode node;

            public TreeNodeWrapper(int depth, TreeNode node) {
                this.depth = depth;
                this.node = node;
            }
        }

        /**
         * 深度优先, 递归实现
         */
        public static int maxDepthDfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return Math.max(maxDepthDfs(root.left), maxDepthDfs(root.right)) + 1;
        }

        /**
         * 广度优先, 迭代实现
         */
        public static int maxDepthBfs(TreeNode root) {
            int maxDepth = 0;
            Queue<TreeNodeWrapper> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(new TreeNodeWrapper(1, root));
            }
            while (!queue.isEmpty()) {
                TreeNodeWrapper wrapper = queue.poll();
                maxDepth = Math.max(maxDepth, wrapper.depth);
                if (wrapper.node.left != null) {
                    queue.offer(new TreeNodeWrapper(wrapper.depth + 1, wrapper.node.left));
                }
                if (wrapper.node.right != null) {
                    queue.offer(new TreeNodeWrapper(wrapper.depth + 1, wrapper.node.right));
                }
            }
            return maxDepth;
        }

        /**
         * 深度优先, 递归实现
         */
        public static int minDepthDfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftMin = minDepthDfs(root.left);
            int rightMin = minDepthDfs(root.right);
            if (leftMin == 0 || rightMin == 0) {
                return leftMin != 0 ? leftMin + 1: rightMin + 1;
            }
            return Math.min(leftMin, rightMin) + 1;
        }

        /**
         * 广度优先, 迭代实现, 只要第一次遇到叶子节点就可以直接返回
         */
        public static int minDepthBfs(TreeNode root) {
            Queue<TreeNodeWrapper> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(new TreeNodeWrapper(1, root));
            }
            while (!queue.isEmpty()) {
                TreeNodeWrapper wrapper = queue.poll();
                if (wrapper.node.left == null && wrapper.node.right == null) {
                    return wrapper.depth;
                }
                if (wrapper.node.left != null) {
                    queue.offer(new TreeNodeWrapper(wrapper.depth + 1, wrapper.node.left));
                }
                if (wrapper.node.right != null) {
                    queue.offer(new TreeNodeWrapper(wrapper.depth + 1, wrapper.node.right));
                }
            }
            return 0;
        }
    }

    /**
     * 二叉树的宽度: 最大宽度, 最好的做法是利用 index 的差值
     */
    public static class WidthMetric {

        public static class TreeNodeWrapper {
            private final int index;
            private final int level;
            private final TreeNode node;

            public TreeNodeWrapper(int index, int level, TreeNode node) {
                this.index = index;
                this.level = level;
                this.node = node;
            }
        }

        /**
         * 类层序遍历, 深度优先, 递归实现
         */
        public static int maxWidthDfs(TreeNode root) {
            return maxWidthDfs(root, 0, 0, new HashMap<>());
        }

        public static int maxWidthDfs(TreeNode root, int level, int index, Map<Integer, Integer> map) {
            if (root == null) {
                return 0;
            }
            map.putIfAbsent(level, index);
            int leftMax = maxWidthDfs(root.left, level + 1, index * 2, map);
            int rightMax = maxWidthDfs(root.left, level + 1, index * 2 + 1, map);
            return Math.max(index - map.get(index) + 1, Math.max(leftMax, rightMax));
        }

        /**
         * 层序遍历, 广度优先, 迭代实现
         */
        public static int maxWidthBfs(TreeNode root) {
            int maxWidth = 0;
            TreeNodeWrapper startWrapper = new TreeNodeWrapper(0, 0, root);
            Queue<TreeNodeWrapper> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(startWrapper);
            }
            while (!queue.isEmpty()) {
                TreeNodeWrapper wrapper = queue.poll();
                // 注意: 这里需要将空节点入队, 所以不需要判断左子结点或者右子节点是否为空
                if (wrapper.node != null) {
                    queue.offer(new TreeNodeWrapper(wrapper.index * 2, wrapper.level + 1, wrapper.node.left));
                    queue.offer(new TreeNodeWrapper(wrapper.index * 2 + 1, wrapper.level + 1, wrapper.node.right));
                    // 注意: 这里需要切换每层的起始位置
                    if (startWrapper.level != wrapper.level) {
                        startWrapper = wrapper;
                    }
                    maxWidth = Math.max(maxWidth, wrapper.index - startWrapper.index + 1);
                }
            }
            return maxWidth;
        }

    }

    /**
     * 二叉树的层平均值
     */
    public static class LevelAverageMetric {

        /**
         * 类层序遍历, 深度优先, 递归实现
         */
        public static List<Double> averageOfLevelsDfs(TreeNode root) {
            List<Double> averages = new ArrayList<>();
            Map<Integer, List<Double>> map = new HashMap<>();
            averageOfLevelsDfs(root, 0, map);
            for (List<Double> level : map.values()) {
                averages.add(level.stream().collect(Collectors.averagingDouble(Double::doubleValue)));
            }
            return averages;
        }

        public static void averageOfLevelsDfs(TreeNode root, int level, Map<Integer, List<Double>> map) {
            if (root == null) {
                return;
            }
            map.computeIfAbsent(level, key -> new ArrayList<>()).add((double) root.value);
            averageOfLevelsDfs(root.left, level + 1, map);
            averageOfLevelsDfs(root.right, level + 1, map);
        }

        /**
         * 层序遍历, 广度优先, 迭代实现
         */
        public static List<Double> averageOfLevelsBfs(TreeNode root) {
            TreeNode currentLevelEnd = root;
            TreeNode nextLevelEnd = null;
            List<Double> averages = new ArrayList<>();
            List<Double> level = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                level.add((double) root.value);
                if (root.left != null) {
                    queue.offer(root.left);
                    nextLevelEnd = root.left;
                }
                if (root.right != null) {
                    queue.offer(root.right);
                    nextLevelEnd = root.right;
                }
                if (currentLevelEnd == root) {
                    averages.add(level.stream().collect(Collectors.averagingDouble(Double::doubleValue)));
                    level.clear();
                    currentLevelEnd = nextLevelEnd;
                }
            }
            return averages;
        }

    }

    /**
     * 二叉树的直径
     */
    public static class DiameterMetric {

        private static int max = 0;

        public static int diameterOfBinaryTree(TreeNode root) {
            diameterOfBinaryTreeDfs(root);
            return max;
        }

        public static int diameterOfBinaryTreeDfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftDepth = diameterOfBinaryTreeDfs(root.left);
            int rightDepth = diameterOfBinaryTreeDfs(root.right);
            max = Math.max(max, leftDepth + rightDepth);
            return Math.max(leftDepth, rightDepth) + 1;
        }

    }

    /**
     * 二叉树的坡度
     */
    public static class TiltMetric {

        private static int tiltSum = 0;

        /**
         * 深度优先, 递归实现
         */
        public static int findTilt(TreeNode root) {
            findTiltDfs(root);
            return tiltSum;
        }

        public static int findTiltDfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftSum = findTiltDfs(root.left);
            int rightSum = findTiltDfs(root.right);
            tiltSum += Math.abs(leftSum - rightSum);
            return leftSum + rightSum + root.value;
        }

    }

    /**
     * 二叉树的左叶子之和
     */
    public static class LeftLeafSumMetric {

        /**
         * 深度优先, 递归实现
         */
        public static int sumOfLeftLeavesDfs(TreeNode root) {
            return sumOfLeftLeavesDfs(root, root);
        }

        public static int sumOfLeftLeavesDfs(TreeNode root, TreeNode parent) {
            if (root == null) {
                return 0;
            }
            if (parent.left == root && root.left == null && root.right == null) {
                return root.value;
            }
            return sumOfLeftLeavesDfs(root.left, root) + sumOfLeftLeavesDfs(root.right, root);
        }

        /**
         * 广度优先, 迭代实现
         */
        public static int sumOfLeftLeavesBfs(TreeNode root) {
            int leftLeafSum = 0;
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root.left != null) {
                    if (isLeaf(root.left)) {
                        leftLeafSum += root.left.value;
                    }
                }
                if (root.right != null) {
                    if (!isLeaf(root.right)) {
                        queue.offer(root.right);
                    }
                }
            }
            return leftLeafSum;
        }

        private static boolean isLeaf(TreeNode root) {
            return root.left == null && root.right == null;
        }
    }
}
