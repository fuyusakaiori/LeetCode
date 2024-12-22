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
         * 递归实现: 深度优先
         */
        public static int maxDepthDFS(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return Math.max(maxDepthDFS(root.left), maxDepthDFS(root.right)) + 1;
        }

        /**
         * 迭代实现: 广度优先
         */
        public static int maxDepthBFS(TreeNode root) {
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
         * 递归实现: 深度优先
         */
        public static int minDepthDFS(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftMin = minDepthDFS(root.left);
            int rightMin = minDepthDFS(root.right);
            if (leftMin == 0 || rightMin == 0) {
                return leftMin != 0 ? leftMin + 1: rightMin + 1;
            }
            return Math.min(leftMin, rightMin) + 1;
        }

        /**
         * 迭代实现: 广度优先, 只要第一次遇到叶子节点就可以直接返回
         */
        public static int minDepthBFS(TreeNode root) {
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
         * 递归实现: 深度优先
         */
        public static int maxWidthDFS(TreeNode root) {
            return maxWidthDFS(root, 0, 0, new HashMap<>());
        }

        public static int maxWidthDFS(TreeNode root, int level, int index, Map<Integer, Integer> map) {
            if (root == null) {
                return 0;
            }
            map.putIfAbsent(level, index);
            int leftMax = maxWidthDFS(root.left, level + 1, index * 2, map);
            int rightMax = maxWidthDFS(root.left, level + 1, index * 2 + 1, map);
            return Math.max(index - map.get(index) + 1, Math.max(leftMax, rightMax));
        }

        /**
         * 迭代实现: 广度优先
         */
        public static int maxWidthBFS(TreeNode root) {
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
         * 递归实现: 深度优先
         */
        public static List<Double> averageOfLevelsDFS(TreeNode root) {
            List<Double> averages = new ArrayList<>();
            Map<Integer, List<Double>> map = new HashMap<>();
            averageOfLevelsDFS(root, 0, map);
            for (List<Double> level : map.values()) {
                averages.add(level.stream().collect(Collectors.averagingDouble(Double::doubleValue)));
            }
            return averages;
        }

        public static void averageOfLevelsDFS(TreeNode root, int level, Map<Integer, List<Double>> map) {
            if (root == null) {
                return;
            }
            map.computeIfAbsent(level, key -> new ArrayList<>()).add((double) root.value);
            averageOfLevelsDFS(root.left, level + 1, map);
            averageOfLevelsDFS(root.right, level + 1, map);
        }

        /**
         * 迭代实现: 广度优先
         */
        public static List<Double> averageOfLevelsBFS(TreeNode root) {
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
            diameterOfBinaryTreeDFS(root);
            return max;
        }

        public static int diameterOfBinaryTreeDFS(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftDepth = diameterOfBinaryTreeDFS(root.left);
            int rightDepth = diameterOfBinaryTreeDFS(root.right);
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
            findTiltDFS(root);
            return tiltSum;
        }

        public static int findTiltDFS(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int leftSum = findTiltDFS(root.left);
            int rightSum = findTiltDFS(root.right);
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
        public static int sumOfLeftLeavesDFS(TreeNode root) {
            return sumOfLeftLeavesDFS(root, root);
        }

        public static int sumOfLeftLeavesDFS(TreeNode root, TreeNode parent) {
            if (root == null) {
                return 0;
            }
            if (parent.left == root && root.left == null && root.right == null) {
                return root.value;
            }
            return sumOfLeftLeavesDFS(root.left, root) + sumOfLeftLeavesDFS(root.right, root);
        }

        /**
         * 广度优先, 迭代实现
         */
        public static int sumOfLeftLeavesBFS(TreeNode root) {

            return 0;
        }
    }
}
