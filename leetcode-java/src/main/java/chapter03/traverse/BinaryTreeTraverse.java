package chapter03.traverse;

import utils.TreeNode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * <p>采用递归 + 非递归的方式遍历二叉树结构</p>
 * <p>1. 二叉树的前序遍历</p>
 * <p>2. 二叉树的中序遍历</p>
 * <p>3. 二叉树的后序遍历</p>
 */
@Slf4j
public class BinaryTreeTraverse {

    /**
     * 前序遍历: 父 -> 左 -> 右
     */
    public static class PreOrderTraverse {

        /**
         * 递归实现
         */
        public static void traverseRecur(TreeNode root) {
            if (root == null) {
                return;
            }
            // TODO 这里可以做任何操作
            log.info("node value: {}", root.value);
            traverseRecur(root.left);
            traverseRecur(root.right);
        }

        /**
         * 迭代实现
         */
        public static void traverseIterate(TreeNode root) {
            LinkedList<TreeNode> stack = new LinkedList<>();
            if (root != null) {
                stack.push(root);
            }
            while (!stack.isEmpty()) {
                root = stack.pop();
                // TODO 这里可以做任何操作
                //noinspection LoggingSimilarMessage
                log.info("node value: {}", root.value);
                if (root.right != null) {
                    stack.push(root.right);
                }
                if (root.left != null) {
                    stack.push(root.left);
                }
            }
        }

        /**
         * 莫里斯实现
         */
        public static void traverseMorris(TreeNode root) {
            TreeNode current = root;
            TreeNode mostRight = null;

            while (current != null){
                if (current.left != null){
                    mostRight = current.left;
                    while (mostRight.right != null && mostRight.right != current)
                        mostRight = mostRight.right;
                    if (mostRight.right == null){
                        // 第一次到达就直接输出
                        System.out.println(current.value);
                        mostRight.right = current;
                        current = current.left;
                        continue;
                    }else{
                        mostRight.right = null;
                    }
                }else{
                    // 第一次达到输出
                    System.out.println(current.value);
                }
                current = current.right;
            }
        }
    }

    /**
     * 中序遍历: 左 -> 父 -> 右
     */
    public static class InfixOrderTraverse {

        /**
         * 递归实现
         */
        public static void traverseRecur(TreeNode root) {
            if (root == null) {
                return;
            }
            traverseRecur(root.left);
            // TODO 这里可以做任何操作
            log.info("node value: {}", root.value);
            traverseRecur(root.right);
        }

        /**
         * 迭代实现
         */
        public static void traverseIterate(TreeNode root) {
            LinkedList<TreeNode> stack = new LinkedList<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    // TODO 这里可以做任何操作
                    log.info("node value: {}", root.value);
                    root = root.right;
                }
            }
        }

        /**
         * 莫里斯实现
         */
        public static void traverseMorris(TreeNode root) {
            TreeNode current = root;
            TreeNode mostRight = null;

            while (current != null){
                if (current.left != null){
                    mostRight = current.left;
                    while (mostRight.right != null && mostRight.right != current)
                        mostRight = mostRight.right;
                    if (mostRight.right == null){
                        // 第一次到达不做任何输出
                        mostRight.right = current;
                        current = current.left;
                        continue;
                    }else{
                        mostRight.right = null;
                        System.out.println(current.value);
                    }
                }else{
                    // 仅达到一次的立刻输出
                    System.out.println(current.value);
                }
                current = current.right;
            }
        }

    }

    /**
     * 后序遍历: 右 -> 父 -> 左
     */
    public static class PostOrderTraverse {

        /**
         * 递归实现
         */
        public static void traverseRecur(TreeNode root) {
            if (root == null) {
                return;
            }
            traverseRecur(root.left);
            traverseRecur(root.right);
            // TODO 这里可以做任何操作
            log.info("node value: {}", root.value);
        }

        /**
         * 迭代实现: 双栈实现
         */
        public static void traverseIterate(TreeNode root) {
            LinkedList<TreeNode> first = new LinkedList<>();
            LinkedList<TreeNode> second = new LinkedList<>();
            if (root != null) {
                first.push(root);
            }
            while (!first.isEmpty()) {
                root = first.pop();
                second.push(root);
                if (root.left != null) {
                    first.push(root.left);
                }
                if (root.right != null) {
                    first.push(root.right);
                }
            }
            while (!second.isEmpty()) {
                log.info("node value: {}", second.pop().value);
            }
        }

        /**
         * 迭代实现: 单栈实现
         */
        public static void traverseIterateOptimize(TreeNode root) {
            TreeNode current = null;
            TreeNode last = root;
            LinkedList<TreeNode> stack = new LinkedList<>();
            if (root != null) {
                stack.push(root);
            }
            while (!stack.isEmpty()){
                current = stack.peek();
                if (current.left != null && last != current.left) {
                    stack.push(current.left);
                }
                else if (current.right != null && last != current.right) {
                    stack.push(current.right);
                } else {
                    log.info("node value: {}", (last = stack.pop()));
                }
            }
        }

        /**
         * 莫里斯实现
         */
        public static void traverseMorris(TreeNode root) {
            TreeNode current = root;
            TreeNode mostRight = null;

            while (current != null){
                if (current.left != null){
                    mostRight = current.left;
                    while (mostRight.right != null && mostRight.right != current)
                        mostRight = mostRight.right;
                    if (mostRight.right == null){
                        mostRight.right = current;
                        current = current.left;
                        continue;
                    }else{
                        mostRight.right = null;
                        printTree(current.left);
                    }
                }
                current = current.right;
            }
            printTree(root);
        }

        /**
         * <h3> 反序输出左子树 </h3>
         */
        private static TreeNode reverseTree(TreeNode root){
            TreeNode previous = null;
            TreeNode current = root;
            TreeNode next = null;
            while (current != null){
                next = current.right;
                current.right = previous;
                previous = current;
                current = next;
            }
            return previous;
        }

        /**
         * <h3>逆序输出</h3>
         */
        private static void printTree(TreeNode root){
            TreeNode tail = reverseTree(root);
            TreeNode current = tail;
            while (current != null){
                System.out.print(current.value + "->");
                current = current.right;
            }
            reverseTree(tail);
        }
    }

    /**
     * 层序遍历
     */
    public static class LevelOrderTraverse {

        /**
         * 深度优先, 递归实现
         */
        public static void traverseDfs(TreeNode root) {
            Map<Integer, List<Integer>> map = new HashMap<>();
            traverseDfs(root, 0, map);
            for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                log.info("level: {}, nodes: {}", entry.getKey(), entry.getValue());
            }
        }

        public static void traverseDfs(TreeNode root, int level, Map<Integer, List<Integer>> map) {
            if (root == null) {
                return;
            }
            // TODO computeIfAbsent 好像有点慢
            map.computeIfAbsent(level, k -> new LinkedList<>()).add(root.value);
            traverseDfs(root.left, level + 1, map);
            traverseDfs(root.right, level + 1, map);
        }

        /**
         * 广度优先, 迭代实现: 从上到下的层序遍历
         */
        public static void traverseBfsTop(TreeNode root) {
            // 记录当前层的节点
            TreeNode currentLevelEnd = root;
            // 记录下一层的节点
            TreeNode nextLevelEnd = null;
            // 每层存放的节点
            List<Integer> level = new LinkedList<>();
            // 所有层存放的节点
            List<List<Integer>> levels = new LinkedList<>();
            // 队列
            Queue<TreeNode> queue = new LinkedList<>();

            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                // TODO 这里可以做任何操作
                level.add(root.value);
                if (root.left != null) {
                    queue.offer(root.left);
                    nextLevelEnd = root.left;
                }
                if (root.right != null) {
                    queue.offer(root.right);
                    nextLevelEnd = root.right;
                }
                if (currentLevelEnd == root) {
                    levels.add(new LinkedList<>(level));
                    level.clear();
                    currentLevelEnd = nextLevelEnd;
                }
            }
            log.info("nodes: {}", levels);
        }

        /**
         * 广度优先, 迭代实现: 从下到上的层序遍历, 其实就是从上到下遍历, 然后反向插入
         */
        public static void traverseBfsDown(TreeNode root) {
            // 当前层的末尾节点
            TreeNode currentLevelEnd = root;
            // 下一层的末尾节点
            TreeNode nextLevelEnd = root;
            // 当前层存放的节点
            List<Integer> level = new LinkedList<>();
            // 所有层存放的节点
            List<List<Integer>> levels = new LinkedList<>();
            // 队列
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                level.add(root.value);
                if (root.left != null) {
                    queue.offer(root.left);
                    nextLevelEnd = root.left;
                }
                if (root.right != null) {
                    queue.offer(root.right);
                    nextLevelEnd = root.right;
                }
                if (currentLevelEnd == root) {
                    // 注意: 这里是每次都插入在头节点
                    levels.add(0, new LinkedList<>(level));
                    level.clear();
                    currentLevelEnd = nextLevelEnd;
                }
            }
            log.info("nodes: {}", levels);
        }


    }

    /**
     * 锯齿状层序遍历: 变式
     */
    public static class ZigZagLevelOrderTraverse {

        /**
         * 双向队列
         */
        public static void traverseDeque(TreeNode root) {
            boolean flag = true;
            TreeNode currentLevelEnd = root;
            TreeNode nextLevelEnd = null;
            List<Integer> level = new LinkedList<>();
            List<List<Integer>> levels = new LinkedList<>();
            Deque<TreeNode> deque = new LinkedList<>();
            if (root != null) {
                deque.offerFirst(root);
            }
            while (!deque.isEmpty()) {
                if (flag) {
                    root = deque.pollFirst();
                    level.add(root.value);
                    // 从尾部入队, 然后会从尾部出队
                    if (root.left != null) {
                        deque.offerLast(root.left);
                        // 注意: 因为每次入队和出队是相反的, 所以每层的结束节点就是刚开始遍历的节点
                        nextLevelEnd = nextLevelEnd == null ? root.left : nextLevelEnd;
                    }
                    if (root.right != null) {
                        deque.offerLast(root.right);
                        nextLevelEnd = nextLevelEnd == null ? root.right : nextLevelEnd;
                    }
                } else {
                    root = deque.pollLast();
                    level.add(root.value);
                    // 从头部入队, 然后从头部出队
                    if (root.right != null) {
                        deque.offerFirst(root.right);
                        nextLevelEnd = nextLevelEnd == null ? root.right : nextLevelEnd;
                    }
                    if (root.left != null) {
                        deque.offerFirst(root.left);
                        nextLevelEnd = nextLevelEnd == null ? root.left : nextLevelEnd;
                    }
                }
                if (currentLevelEnd == root) {
                    levels.add(new LinkedList<>(level));
                    level.clear();
                    currentLevelEnd = nextLevelEnd;
                    // 注意: 必须置空, 否则无法更新
                    nextLevelEnd = null;
                    flag = !flag;
                }
            }
            log.info("nodes: {}", levels);
        }

        /**
         * 栈
         */
        public static void traverseStack(TreeNode root) {
            boolean flag = true;
            TreeNode currentLevelEnd = root;
            TreeNode nextLevelEnd = null;
            LinkedList<Integer> level = new LinkedList<>();
            List<List<Integer>> levels = new LinkedList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (flag) {
                    level.add(root.value);
                } else {
                    level.push(root.value);
                }
                if (root.left != null) {
                    queue.offer(root.left);
                    nextLevelEnd = root.left;
                }
                if (root.right != null) {
                    queue.offer(root.right);
                    nextLevelEnd = root.right;
                }
                if (currentLevelEnd == root) {
                    levels.add(new LinkedList<>(level));
                    level.clear();
                    currentLevelEnd = nextLevelEnd;
                    flag = !flag;
                }
            }
            log.info("nodes: {}", levels);
        }

    }
}
