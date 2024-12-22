package chapter03.traverse;

import utils.TreeNode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * <p>N叉树的相关问题: 非常简单</p>
 * <p>1. N叉树的前序遍历、后序遍历</p>
 * <p>2. N叉树的层序遍历</p>
 * <p>3. N叉树的最大深度</p>
 */
@Slf4j
public class NTreeTraverse {

    public static final class NTreeNode{
        private int value;
        private List<NTreeNode> children;

        public NTreeNode() {
        }

        public NTreeNode(int value, List<NTreeNode> children) {
            this.value = value;
            this.children = children;
        }
    }

    /**
     * 前序遍历
     */
    public static class PreOrderTraverse {

        /**
         * 迭代实现
         */
        public static void traverseLoop(NTreeNode root) {
            LinkedList<NTreeNode> stack = new LinkedList<>();
            if (root != null) {
                stack.push(root);
            }
            while (!stack.isEmpty()) {
                root = stack.pop();
                // TODO 这里可以做任何操作
                log.info("node value: {}", root.value);
                for (int index = root.children.size() - 1; index >= 0; index--) {
                    stack.push(root.children.get(index));
                }
            }
        }

        /**
         * 递归实现
         */
        public static void traverseRecursive(NTreeNode root) {
            if (root == null) {
                return;
            }
            // TODO 这里可以做任何操作
            log.info("node value: {}", root.value);
            for (NTreeNode child : root.children) {
                traverseRecursive(child);
            }
        }

    }

    /**
     * 后序遍历
     */
    public static class PostOrderTraverse {

        /**
         * 迭代实现
         */
        public static void traverseLoop(NTreeNode root) {
            LinkedList<NTreeNode> first = new LinkedList<>();
            LinkedList<NTreeNode> second = new LinkedList<>();
            if (root != null) {
                first.push(root);
            }
            while (!first.isEmpty()) {
                second.push(root = first.pop());
                for (NTreeNode child : root.children) {
                    first.push(child);
                }
            }
            while (!second.isEmpty()) {
                // TODO 这里可以做任何操作
                log.info("node value: {}", second.pop().value);
            }
        }

        /**
         * 递归实现
         */
        public static void traverseRecursive(NTreeNode root) {
            if (root == null) {
                return;
            }
            for (NTreeNode child : root.children) {
                traverseRecursive(child);
            }
            // TODO 这里可以做任何操作
            log.info("node value: {}", root.value);
        }

    }

    /**
     * 层序遍历
     */
    public static class LevelOrderTraverse {

        /**
         * 迭代实现
         */
        public static void traverseLoop(NTreeNode root) {
            NTreeNode currentLevelEnd = root;
            NTreeNode nextLevelEnd = null;
            List<Integer> level = new ArrayList<>();
            List<List<Integer>> levels = new ArrayList<>();
            Queue<NTreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                level.add(root.value);
                for (NTreeNode child : root.children) {
                    queue.offer(child);
                    nextLevelEnd = child;
                }
                if (currentLevelEnd == root) {
                    levels.add(new ArrayList<>(level));
                    level.clear();
                    currentLevelEnd = nextLevelEnd;
                    nextLevelEnd = null;
                }
            }
            log.info("nodes: {}", levels);
        }

        /**
         * 递归实现
         */
        public static void traverseRecursive(NTreeNode root) {
            Map<Integer, List<Integer>> map = new HashMap<>();
            traverseRecursive(root, 0, map);
            log.info("nodes: {}", map);
        }

        public static void traverseRecursive(NTreeNode root, int level, Map<Integer, List<Integer>> map) {
            if (root == null) {
                return;
            }
            map.computeIfAbsent(level, key -> new ArrayList<>()).add(root.value);
            for (NTreeNode child : root.children) {
                traverseRecursive(child, level + 1, map);
            }
        }

    }

    /**
     * morris
     */
    public static class MorrisTravers {

        private static void morris(TreeNode root){
            TreeNode current = root;
            TreeNode mostRight = null;

            while (current != null){
                // 如果有左子树就可以到达两次
                if (current.left != null){
                    mostRight = current.left;
                    while (mostRight.right != null && mostRight.right != current)
                        mostRight = mostRight.right;
                    // 第一次到达
                    if (mostRight.right == null){
                        mostRight.right = current;
                        current = current.left;
                        continue;
                    }else{
                        // 第二次达到
                        mostRight.right = null;
                    }
                }
                // 如果没有左子树就只能够到达一次
                current = current.right;
            }
        }
    }

}
