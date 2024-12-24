package chapter03;

import utils.TreeNode;

import java.util.*;

/**
 * <p>二叉树路径问题</p>
 * <p>1. 路径总和 </p>
 * <p>2. 路径总和 II</p>
 * <p>3. 路径总和 III</p>
 * <p>4. 二叉树的所有路径</p>
 * <p>4. 求根结点到叶子结点数字之和</p>
 */
public class BinaryTreePath {

    /**
     * 路径总和
     */
    public static class PathSumI {

        /**
         * 路径总和: 深度优先, 递归实现
         * <p>1. 深度遍历: 目标值不断减去结点值</p>
         * <p>2. 如果在非叶子结点变为 0, 那么到达叶子结点就会为负数, 返回 false</p>
         * <p>3. 如果到达叶子结点恰好为 0, 那么这条路径就是有效的</p>
         */
        public static boolean hasPathSumDfs(TreeNode root, int target) {
            // 注意: 这个判断其实是用来处理空值的
            if (root == null) {
                return false;
            }
            if (root.left == null && root.right == null) {
                return root.value == target;
            }
            return hasPathSumDfs(root.left, target - root.value) ||
                    hasPathSumDfs(root.right, target - root.value);
        }

        /**
         * 路径总和: 广度优先, 迭代实现
         */
        public static boolean hasPathSumBfs(TreeNode root, int target) {
            int sum = 0;
            Queue<Integer> sums = new LinkedList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                sum = sums.poll();
                if (root.left == null && root.right == null && sum == target) {
                    return true;
                }
                if (root.left != null) {
                    queue.offer(root.left);
                    sums.offer(sum + root.left.value);
                }
                if (root.right != null) {
                    queue.offer(root.right);
                    sums.offer(sum + root.right.value);
                }
            }
            return false;
        }

    }

    /**
     * 路径总和 II
     */
    public static class PathSumII {

        /**
         * 路径总和 II: 递归实现, 深度搜索
         */
        public static List<List<Integer>> pathSumDfs(TreeNode root, int target) {
            Deque<Integer> path = new LinkedList<>();
            List<List<Integer>> paths = new LinkedList<>();
            pathSumDfs(root, target, path, paths);
            return paths;
        }

        public static void pathSumDfs(TreeNode root, int target, Deque<Integer> path, List<List<Integer>> paths) {
            if (root == null) {
                return;
            }
            target -= root.value;
            path.offerLast(root.value);
            if (root.left == null && root.right == null && target == 0) {
                paths.add(new ArrayList<>(path));
            }
            pathSumDfs(root.left, target, path, paths);
            pathSumDfs(root.right, target, path, paths);
            path.pollLast();
        }

        /**
         * 路径总和 II: 迭代实现, 广度搜索
         * 1. 依然借助路径总和问题的想法, 采用双队列来分别记录遍历的情况和路径和的情况
         * 2. 但是问题在于如何在遍历到叶子结点的时候将相应的路径添加进去
         * 3. 因为遍历的时候是添加一层的结点进去, 并且没有办法在此时得知该路径是否有效, 所以只用链表是不够的
         * 4. 原先的想法就是使用哈希表保存路径和路径和的映射关系, 但是可以使用结点和结点的映射关系
         * 5. 前者从逻辑上来说比较直观, 但是效率很差, 因为需要频繁创建链表对象
         * 6. 后者虽然在计算和的时候需要遍历, 但是不需要创建大量的对象
         */
        private static List<List<Integer>> pathSumBfs(TreeNode root, int target){
            Map<TreeNode, TreeNode> map = new HashMap<>();
            List<List<Integer>> paths = new LinkedList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            Queue<Integer> pathSum = new LinkedList<>();
            if (root != null){
                queue.offer(root);
                pathSum.offer(root.value);
            }
            int value = 0;
            while (!queue.isEmpty()){
                root = queue.poll();
                assert !pathSum.isEmpty();
                value = pathSum.poll();
                if (root.left == null && root.right == null && value == target){
                    paths.add(getPathSum(root, map));
                }
                if (root.left != null){
                    queue.offer(root.left);
                    pathSum.offer(root.left.value + value);
                    map.put(root.left, root);
                }
                if (root.right != null){
                    queue.offer(root.right);
                    pathSum.offer(root.right.value + value);
                    map.put(root.right, root);
                }
            }
            return paths;
        }

        /**
         * <p>从叶子结点开始向上遍历到根结点, 算出路径</p>
         * @return 路径和
         */
        private static List<Integer> getPathSum(TreeNode node, Map<TreeNode, TreeNode> map){
            LinkedList<Integer> path = new LinkedList<>();
            while (node != null){
                path.push(node.value);
                // 获取该结点的父结点, 不断向上移动
                node = map.get(node);
            }
            return path;
        }

    }

    /**
     * 路径总和 III
     */
    public static class PathSumIII {

        /**
         * 路径总和 III: 单递归
         */
        public static int pathSumSingleDfs(TreeNode root, int target) {
            return pathSumSingleDfs(root, false, target);
        }

        public static int pathSumSingleDfs(TreeNode root, boolean flag, long target) {
            if (root == null) {
                return 0;
            }
            int paths = 0;
            if (target == root.value) {
                paths++;
            }
            if (!flag) {
                paths += pathSumSingleDfs(root.left, false, target);
                paths += pathSumSingleDfs(root.right, false, target);
            }
            return paths + pathSumSingleDfs(root.left, true, target - root.value)
                    + pathSumSingleDfs(root.right, true, target - root.value);
        }

        /**
         * 路径总和 III: 双重递归
         */
        public static int pathSumDoubleDfs(TreeNode root, int target) {
            if (root == null) {
                return 0;
            }
            return getPathSumDfs(root, target) + pathSumDoubleDfs(root.left, target) + pathSumDoubleDfs(root.right, target);
        }

        public static int getPathSumDfs(TreeNode root, int target) {
            if (root == null) {
                return 0;
            }
            int paths = 0;
            if (root.value == target) {
                paths += 1;
            }
            return paths + getPathSumDfs(root.left, target - root.value)
                    + getPathSumDfs(root.right, target - root.value);
        }

        /**
         * 路径总和 III: 前缀和
         * <p>1. 路径都是自顶向下的: root -> node-i -> node-i+1 -> ... -> node-j</p>
         * <p>2. 采用哈希表保存根结点到前面所有结点的路径和, root->node-i、root->node-i+1...的路径和都保存</p>
         * <p>3. 现在记 root-> node-j 的路径和为 current, 如果前面存在 node-i+1->node-j 的路径和满足条件</p>
         * <p>4. 那么必然存在 root->node-i 的路径和为 current - target</p>
         * <p>5. 所以每次只需要验证哈希表存放的前缀和中是否存在相应的 current - target 就可以</p>
         * <p>6. 如果哈希表中保存的前缀和中存在 current- target, 那么就证明存在一个或者多个结点到当前结点满足条件</p>
         * <p>7. 如果没有相应的前缀和, 那么就证明前面没有任何结点到当前结点是满足条件的</p>
         */
        public static int pathSumPrefix(TreeNode root, int target) {
            Map<Integer, Integer> prefix = new HashMap<>();
            // 注意: 初始化的目的是为了应对恰好 current - target = 0 的情况
            prefix.put(0, 1);
            return pathSumPrefix(root, 0, target, prefix);
        }

        public static int pathSumPrefix(TreeNode root, int current, int target, Map<Integer, Integer> prefix) {
            if (root == null) {
                return 0;
            }
            int paths = 0;
            // 增加前缀和
            current += root.value;
            // 判断是否 current - target 是否存在满足目标值的
            paths += prefix.getOrDefault(current - target, 0);
            // 将当前的前缀和放入哈希表中, 如果有相同的前缀和, 就直接 +1
            prefix.put(current, prefix.getOrDefault(current, 0) + 1);
            // 开始选择路径的下一个节点
            paths += pathSumPrefix(root.left, current, target, prefix);
            paths += pathSumPrefix(root.right, current, target, prefix);
            // 将前缀和移除
            prefix.put(current, prefix.get(current) - 1);
            return paths;
        }

    }

    /**
     * 二叉树的所有路径
     */
    public static class PathAll {
        /**
         * 前序遍历, 深度优先, 递归实现
         */
        public static List<String> binaryTreePathsDfs(TreeNode root) {
            List<String> paths = new ArrayList<>();
            binaryTreePathsDfs(root, "", paths);
            return paths;
        }

        public static void binaryTreePathsDfs(TreeNode root, String path, List<String> paths) {
            if (root == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(path);
            sb.append(root.value);
            if (root.left == null && root.right == null) {
                paths.add(sb.toString());
                return;
            }
            sb.append("->");
            binaryTreePathsDfs(root.left, sb.toString(), paths);
            binaryTreePathsDfs(root.right, sb.toString(), paths);
        }

        /**
         * 层序遍历, 广度优先, 迭代实现
         */
        public static List<String> binaryTreePathsBfs(TreeNode root) {

            return null;
        }
    }

    /**
     * 求根节点到叶子节点的数字之和
     */
    public static class PathSumAll {

        /**
         * 前序遍历, 深度优先, 递归实现
         */
        public int sumNumbersDfs(TreeNode root) {
            List<String> paths = new ArrayList<>();
            sumNumbersDfs(root, "", paths);
            return paths.stream().mapToInt(Integer::parseInt).sum();
        }

        public void sumNumbersDfs(TreeNode root, String path, List<String> paths) {
            if (root == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(path);
            sb.append(root.value);
            if (root.left == null && root.right == null) {
                paths.add(sb.toString());
            } else {
                sumNumbersDfs(root.left, sb.toString(), paths);
                sumNumbersDfs(root.right, sb.toString(), paths);
            }
        }

        /**
         * 层序遍历, 广度优先, 迭代实现
         */
        public int sumNumbersBfs(TreeNode root) {

            return 0;
        }
    }
}
