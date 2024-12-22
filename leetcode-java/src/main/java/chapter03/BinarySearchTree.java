package chapter03;


import utils.ListNode;
import utils.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉搜索树
 * 1. 二叉搜索树增删查
 * 2. 二叉搜索树的最小绝对差
 * 3. 二叉搜索树的范围和
 * 4. 二叉搜索树中的众数
 * 5. 不同的二叉搜索树
 * 6. 不同的二叉搜索树 II
 * 7. 将有序数组转换为二叉搜索树
 * 8. 将有序链表转换为二叉搜索树
 * 9. 二叉搜索树中的第 K 小的元素
 * 10. 恢复二叉搜索树
 * 注: 再次强调, 二叉搜索树一定要使用性质!
 * 注: 只要在树的题目中要求空间复杂度为 O(1), 那么肯定使用莫里斯遍历
 */
public class BinarySearchTree {

    /**
     * 二叉搜索树: 增加, 删除, 查询
     */
    public static class Operation {
        /**
         * 二叉搜索树的查询
         */
        public static TreeNode searchRecursive(TreeNode root, int target){
            if(root == null || root.value == target) return root;
            return root.value > target ? searchRecursive(root.left, target): searchRecursive(root.right, target);
        }

        public static TreeNode searchLoop(TreeNode root, int target){
            while (root != null){
                if (root.value == target)
                    return root;
                root = root.value > target ? root.left: root.right;
            }
            return null;
        }

        /**
         * 二叉搜索树的插入
         */
        public static TreeNode insertRecursive(TreeNode root, int value){
            if (root == null)
                return new TreeNode(value);
            if (root.value > value)
                root.left = insertRecursive(root.left, value);
            if (root.value < value)
                root.right = insertRecursive(root.right, value);
            return root;
        }

        public static TreeNode insertLoop(TreeNode root, int value){
            if (root == null)
                return new TreeNode(value);
            TreeNode current = root;
            while(current.left != null || current.right != null){
                if (current.left != null && current.value > value)
                    current = current.left;
                else if (current.right != null && current.value < value)
                    current = current.right;
                else
                    break;
            }
            if (current.value > value) current.left = new TreeNode(value);
            if (current.value < value) current.right = new TreeNode(value);
            return current;
        }

        /**
         * 二叉搜索树中的删除
         * <p>1. 如果删除的是叶子结点, 那么直接删除就行</p>
         * <p>2. 如果删除的是非叶子结点, 那么需要分为两种情况</p>
         * <p>2.1 非叶子结点具有一个叶子结点, 直接让父结点指向孙子结点</p>
         * <p>2.2 非叶子结点具有两个叶子结点, 找左子树的最右结点或者右子树的最左结点替换删除的结点</p>
         * <p>注: 这个应该是没有迭代的写法...</p>
         * <p>注: 这个是我自己的写法, 官方题解写得没这么直白...之后有空再看</p>
         */
        public static TreeNode delete(TreeNode root, int target){
            // 注: 主要目的是为了删除头部, 这里为了简化使用 next 指针
            TreeNode dummy = new TreeNode(root.value);
            dummy.next = root;
            deleteAdjust(root, dummy, target);
            return dummy.next;
        }

        private static void deleteAdjust(TreeNode root, TreeNode parent, int target){
            if (root.value > target){
                deleteAdjust(root.left, root, target);
            }else if (root.value < target){
                deleteAdjust(root.right, root, target);
            }else{
                if (root.left == null && root.right == null){
                    // 如果删除的叶子结点是父结点的左子结点, 那么父结点的左孩子置为空值即可, 右子结点同理
                    if (parent.left == root) parent.left = null;
                    if (parent.right == root) parent.right = null;
                }else if (root.left != null && root.right != null){
                    TreeNode upper = root;
                    TreeNode node = root.right;
                    while (node.left != null) {
                        upper = node;
                        node = node.left;
                    }
                    if (parent.next == root){
                        parent.next = node;
                    }else if (parent.left == root){
                        parent.left = node;
                    }else if (parent.right == root){
                        parent.right = node;
                    }
                    // 注: 无论当前结点的右子树是否为空, 当前结点的父结点都需要接手这个右子树
                    // 注: 不过如果要删除的结点的右子结点就是当前结点, 那么就不需要接手了
                    if (root.right != node) upper.left = node.right;
                    // 更改当前结点的引用, 以达到删除目标结点的目的
                    node.left = root.left;
                    // 如果要删除的结点的右子结点就是当前结点, 那么就不要连接了, 会出现环
                    if (root.right != node)
                        node.right = root.right;
                }else{
                    TreeNode node = root.left != null ? root.left: root.right;
                    if (parent.next == root)
                        parent.next = node;
                    else if (parent.left == root)
                        parent.left = node;
                    else if (parent.right == root)
                        parent.right = node;
                }
            }
        }
        
    }

    /**
     * 二叉搜索树的最小绝对差: 因为二叉搜索树的性质, 所以中序遍历只需要计算当前节点和前驱节点的差值就可以了, 不需要计算所有节点之间的差值
     */
    public static class BinarySearchTreeMinDiff {
        // 记录上一个节点的值
        private static int previous = -1;
        // 记录最小的差值
        private static int minDiff = Integer.MAX_VALUE;

        /**
         * 迭代实现
         */
        public static int getMinDiffLoop(TreeNode root) {
            int minDiff = Integer.MAX_VALUE;
            int previous = -1;
            LinkedList<TreeNode> stack = new LinkedList<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    if (previous != -1) {
                        minDiff = Math.min(minDiff, Math.abs(root.value - previous));
                    }
                    previous = root.value;
                    root = root.right;
                }
            }
            return minDiff;
        }

        /**
         * 递归实现
         */
        public static int getMinDiffRecursive(TreeNode root) {
            getMinDiffDFS(root);
            return minDiff;
        }

        public static void getMinDiffDFS(TreeNode root) {
            if (root == null) {
                return;
            }
            getMinDiffDFS(root.left);
            if (previous != -1) {
                minDiff = Math.min(minDiff, Math.abs(root.value - previous));
            }
            previous = root.value;
            getMinDiffDFS(root.right);
        }

    }

    /**
     * 二叉搜索树的范围和: 因为二叉搜索树的性质, 所以可以通过判断当前值是否在范围内来判断是否遍历左右子树, 也就是剪枝优化
     */
    public static class BinarySearchTreeRangeSum {

        /**
         * 递归实现
         */
        public static int rangeSumBSTRecursive(TreeNode root, int low, int high) {
            if (root == null) {
                return 0;
            }
            if (root.value > high) {
                return rangeSumBSTRecursive(root.left, low, high);
            }
            if (root.value < low) {
                return rangeSumBSTRecursive(root.right, low, high);
            }
            return root.value + rangeSumBSTRecursive(root.left, low, high) + rangeSumBSTRecursive(root.right, low, high);
        }

        /**
         * 迭代实现: 使用任何遍历方式其实都是可以的, 但是建议使用层序遍历, 可以剪枝优化
         */
        public static int rangeSumBSTLoop(TreeNode root, int low, int high) {
            int rangeSum = 0;
            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root == null) {
                    continue;
                }
                if (root.value > high) {
                    queue.offer(root.left);
                }
                if (root.value < low) {
                    queue.offer(root.right);
                }
                if (low <= root.value && root.value <= high) {
                    queue.offer(root.left);
                    queue.offer(root.right);
                    rangeSum += root.value;
                }
            }
            return rangeSum;
        }

    }

    /**
     * 二叉搜索树中的众数: 因为二叉搜索树的性质, 所以中序遍历可以直接和前驱节点比较, 很方便就能计算出每个数字的频率
     */
    public static class BinarySearchTreeMode {
        // 记录之前的值
        private static int previous = -1;
        // 记录当前的频率
        private static int frequency = 1;
        // 记录最大的频率
        private static int maxFrequency = 0;

        /**
         * 迭代实现: 中序遍历
         */
        public static int[] findModeLoop(TreeNode root) {
            int previous = -1;
            int frequency = 0;
            int maxFrequency = 0;
            List<Integer> modes = new ArrayList<>();
            LinkedList<TreeNode> stack = new LinkedList<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    if (root.value == previous) {
                        frequency++;
                    } else {
                        frequency = 1;
                        previous = root.value;
                    }
                    if (frequency == maxFrequency) {
                        modes.add(root.value);
                    }
                    if (frequency > maxFrequency) {
                        maxFrequency = frequency;
                        modes.clear();
                        modes.add(root.value);
                    }
                    root = root.right;
                }
            }
            int[] modesArray = new int[modes.size()];
            for (int index = 0; index < modesArray.length; index++) {
                modesArray[index] = modes.get(index);
            }
            return modesArray;
        }

        /**
         * 递归实现: 中序遍历
         */
        public static int[] findModeRecursive(TreeNode root) {
            List<Integer> modes = new ArrayList<>();
            findModeRecursive(root, modes);
            int[] modeArray = new int[modes.size()];
            for (int index = 0; index < modeArray.length; index++) {
                modeArray[index] = modes.get(index);
            }
            return modeArray;
        }

        public static void findModeRecursive(TreeNode root, List<Integer> modes) {
            if (root == null) {
                return;
            }
            findModeRecursive(root.left, modes);
            update(root.value, modes);
            findModeRecursive(root.right, modes);
        }

        /**
         * 莫里斯实现: 空间复杂度 O(1)
         */
        public static int[] findModeMorris(TreeNode root) {
            List<Integer> modes = new LinkedList<>();
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
                        update(current.value, modes);
                    }
                }else{
                    update(current.value, modes);
                }
                current = current.right;
            }
            int[] modeArray = new int[modes.size()];
            for (int index = 0; index < modeArray.length; index++) {
                modeArray[index] = modes.get(index);
            }
            return modeArray;
        }

        private static void update(int value, List<Integer> modes) {
            if (value == previous) {
                frequency++;
            }else{
                previous = value;
                frequency = 1;
            }
            // 注意: 这个必须放在前面, 如果放在后面, 会导致重复
            if (frequency == maxFrequency) {
                modes.add(value);
            }
            // 注意: 相当于每次增加频率, 都会清空集合然后重新放入元素
            if (frequency > maxFrequency){
                maxFrequency = frequency;
                modes.clear();
                modes.add(value);
            }
        }

    }

    /**
     * 不同的二叉搜索树的数量: 本质其实是动态规划/递归回溯, 和二叉搜索树的性质关系不是特别大
     */
    public static class BinarySearchDifferent {

        /**
         * 不同的二叉搜索树: 记忆化递归
         * 1. 本质是动态规划
         * 2. 每次都将 n 值分别作为根结点, 然后左边的数字就是左子树的结点, 右边的数字就是右子树的结点
         * 3. 此时左子树的结点数一定小于 n, 小于 n 的二叉搜索树的数量此前已经算过, 所以可以直接得到
         * 4. 右子树的种数也是同理的
         * 5. 然后以当前值为根结点的二叉搜索树的种数就是 左子树的数量 * 右子树的数量
         */
        public static int numTreesRecursive(int n) {
            return numTreesRecursive(n, new int[n + 1]);
        }

        public static int numTreesRecursive(int n, int[] dp) {
            if (n == 0 || n == 1) {
                return 1;
            }
            if (dp[n] != 0) {
                return dp[n];
            }
            int nums = 0;
            for (int index = 0; index < n; index++) {
                nums += numTreesRecursive(index, dp) * numTreesRecursive(n - index - 1, dp);
            }
            dp[n] = nums;
            return nums;
        }

        /**
         * 不同的二叉搜索树: 动态规划
         */
        public static int numTreesDp(int n) {
            // 注意: 因为需要保存从 0 ~ n 的结果, 所以长度 + 1
            int[] dp = new int[n + 1];
            dp[0] = 1;
            dp[1] = 1;
            for (int index = 2; index < dp.length; index++) {
                // 注意: 注意这个边界条件
                for (int left = 0, right = index - 1; left <= index && right >= 0; left++, right--) {
                    dp[index] = dp[left] * dp[right];
                }
            }
            return dp[dp.length - 1];
        }

        /**
         * 不同的二叉搜索树 II
         * 1. 依然需要选择 1~n 中的每个数字作为作为根结点
         * 2. 但是这个时候需要得到的是左边和右边的具体有哪些树, 不只是树的种类
         * 3. 所以这个题就可以翻译成构建树的题目, 只不过需要构建出所有可能的组合
         * 4. 所以最基本的想法就是要递归, 并且设置边界
         * 5. 不过区别于构建一棵树, 仅返回一个结点就好, 这里需要构建很多棵树, 所以需要返回树的集合
         * 注: 这个题比刚才的要麻烦, 但是其实思路也是很简单的
         */
        public static List<TreeNode> generateTrees(int n) {
            return generateTrees(1, n);
        }

        public static List<TreeNode> generateTrees(int left, int right) {
            List<TreeNode> trees = new ArrayList<>();
            if (left > right) {
                // 注意: 这里需要记录空节点, 因为如果子树的节点集合为空, 那么在排列组合的时候就会导致另一棵子树无法被设置为节点
                trees.add(null);
                return trees;
            }
            for (int index = left; index <= right; index++) {
                List<TreeNode> leftTrees = generateTrees(left, index - 1);
                List<TreeNode> rightTrees = generateTrees(index + 1, right);
                for (TreeNode leftTree : leftTrees) {
                    for (TreeNode rightTree : rightTrees) {
                        TreeNode root = new TreeNode(index);
                        root.left = leftTree;
                        root.right = rightTree;
                        trees.add(root);
                    }
                }
            }
            return trees;
        }

    }

    /**
     * <p>将有序数组转换为二叉搜索树</p>
     * <p>将有序链表转换为二叉搜索树</p>
     * <p>1. 每次选择数组的中间元素作为根结点构造二叉搜索树</p>
     * <p>2. 这样左右两侧的高度差的绝对值一定小于等于 1</p>
     * <p>3. 然后不断递归构造, 最后就能够得到一棵高度平衡的二叉搜索树</p>
     * <p>产生的问题?</p>
     * <p>1. 为什么选择中间元素作为根结点就一定是高度平衡的二叉搜索树?</p>
     * <p>解释: 有一点贪心的策略在里面, 或者说有一点凭感觉</p>
     * <p>2. 如果数组的长度为偶数, 那么选择靠右的中间结点, 还是选择靠左的中间结点</p>
     * <p>解释: 这里实际上选择哪个都是可以的, 可以一直选择靠左边或者靠右边的中间结点, 也可以随机选择, 类似于快排选择基准</p>
     */
    public static class ConvertArrayListToBinarySearchTree {

        private static ListNode midNode;
        
        /**
         * 将有序数组转换为二叉搜索树
         */
        public static TreeNode sortedArrayToBST(int[] nums) {
            return sortedArrayToBST(0, nums.length - 1, nums);
        }

        public static TreeNode sortedArrayToBST(int left, int right, int[] nums) {
            if (left > right) {
                return null;
            }
            int mid = left + ((right - left) >> 1);
            TreeNode root = new TreeNode(nums[mid]);
            root.left = sortedArrayToBST(left, mid - 1, nums);
            root.right = sortedArrayToBST(mid + 1, right, nums);
            return root;
        }

        /**
         * 将有序链表转换为二叉搜索树
         */
        public static TreeNode sortedListToBST(ListNode head) {
            return sortedListToBST(head, null);
        }

        public static TreeNode sortedListToBST(ListNode head, ListNode tail) {
            if (head == tail) {
                return null;
            }
            ListNode mid = findListMid(head, tail);
            TreeNode root = new TreeNode(mid.value);
            root.left = sortedListToBST(head, mid);
            root.left = sortedListToBST(mid.next, tail);
            return root;
        }

        public static ListNode findListMid(ListNode head, ListNode tail) {
            ListNode slow = head, fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next;
            }
            return slow;
        }

        /**
         * <p>这个优化思路是非常巧妙的, 目标是避免每次递归的时候循环查找中间结点</p>
         * <p>1. 分治的过程每次都是以中间结点为基准, 找中间结点的目的, 就是为了给新建的树结点赋值</p>
         * <p>2. 这个分治的过程非常类似于中序遍历, 走到最左边之后是会返回到中间结点的, 也就是父结点</p>
         * <p>3. 结合上述两条, 那么我们是否可以考虑第一次到达中间结点的时候, 不进行任何赋值</p>
         * <p>等到第二次回来的时候不就自然是中间结点了吗? 这样就避免每次采用快慢指针查找中间结点</p>
         */
        private static TreeNode sortedListBSTOptimize(ListNode head){
            int length = 0;
            midNode = head;
            while(head != null && ++length > 0)
                head = head.next;
            return sortedListBSTOptimize(0, length - 1);
        }
        
        private static TreeNode sortedListBSTOptimize(int left, int right) {
            if(left > right)
                return null;
            int mid = left + ((right - left) >> 1);
            // 暂时不填充根结点的值, 等到中序遍历回到父结点的时候再填充值
            TreeNode root = new TreeNode();
            // 向左侧递归
            root.left = sortedListBSTOptimize(left, mid - 1);
            // 回来的位置一定是中间结点
            root.value = midNode.value;
            // 将结点向后移动, 也是中间结点
            midNode = midNode.next;
            // 向右侧递归
            root.right = sortedListBSTOptimize(mid + 1, right);
            return root;
        }

    }

    /**
     * 二叉搜索树中第 K 小的元素
     */
    public static class BinarySearchTreeKthSmallest {

        private static int kth = 0;

        /**
         * 中序遍历, 深度优先, 迭代实现
         */
        public static int kthSmallestLoop(TreeNode root, int kth) {
            LinkedList<TreeNode> stack = new LinkedList<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    if (--kth == 0) {
                        return root.value;
                    }
                    root = root.right;
                }
            }
            return 0;
        }

        /**
         * 中序遍历, 深度优先, 递归实现, 不推荐
         */
        public static int kthSmallestRecursive(TreeNode root, int k) {
            kth = k;
            return kthSmallestRecursive(root);
        }

        public static int kthSmallestRecursive(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = kthSmallestRecursive(root.left);
            if (--kth == 0) {
                return root.value;
            }
            int right = kthSmallestRecursive(root.right);
            return left != 0 ? left : right;
        }

        /**
         * 莫里斯实现
         */
        public static int kthSmallestMorris(TreeNode root, int k){
            int index = 1;
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
                        if (index++ == k) return current.value;
                    }
                }else{
                    if(index++ == k) return current.value;
                }
                current = current.right;
            }
            return -1;
        }

    }

    /**
     * 恢复二叉搜索树: 实在想不起来可以直接暴力解法, 就是将所有节点放到集合里做排序
     * <p>1. 中序遍历二叉搜索树, 正常情况下得到的结果一定是完全升序的, 错误的情况就会出现降序</p>
     * <p>2. 两个错误的结点可能是相邻的, 也可能是不相邻的, 这就导致降序的次数是不同的</p>
     * <p>2.1 如果两个错误结点相邻, 那么整个中序序列中只会出现一次降序</p>
     * <p>2.2 那么直接交换两个相邻结点就可以了</p>
     * <p>2.3 如果两个错误结点不相邻, 那么就会出现两次降序</p>
     * <p>2.4 那么就需要记录第一次降序时较大的那个数, 然后和第二次降序时较小的那个数交换</p>
     * <p>3. 总结得到就是, 只要出现降序, 那么大的那个结点被赋值后不再改变, 小的那个结点为最后一次降序的值</p>
     */
    public static class BinarySearchTreeRecover {

        private static TreeNode previous;
        private static TreeNode first;
        private static TreeNode second;

        /**
         * 中序遍历, 深度优先, 迭代实现
         */
        public static void recoverTreeLoop(TreeNode root) {
            TreeNode previous = null;
            TreeNode first = null, second = null;
            LinkedList<TreeNode> stack = new LinkedList<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    if (previous != null && previous.value > root.value) {
                        // 注意: 出现降序是保留前驱节点, 不是当前节点
                        first = first == null ? previous: first;
                        second = root;
                    }
                    previous = root;
                }
            }
            if (first != null) {
                swap(first, second);
            }
        }

        private static void swap(TreeNode first, TreeNode second) {
            int temp = first.value;
            first.value = second.value;
            second.value = temp;
        }

        /**
         * 中序遍历, 深度优先, 递归实现
         */
        public static void recoverTreeRecursive(TreeNode root) {
            recoverTree(root);
            swap(first, second);
        }

        public static void recoverTree(TreeNode root) {
            if (root == null) {
                return;
            }
            recoverTree(root.left);
            if (previous != null && previous.value > root.value) {
                // 注意: 出现降序是保留前驱节点, 不是当前节点
                first = first == null ? previous : first;
                second = root;
            }
            previous = root;
            recoverTree(root.right);
        }


        /**
         * 莫里斯实现: O(1) 空间复杂度
         */
        private static void recoverTreeMorris(TreeNode root){
            TreeNode current = root;
            TreeNode mostRight = null;
            TreeNode first = null;
            TreeNode second = null;
            TreeNode previous = null;
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
                        if (previous != null && previous.value > current.value){
                            first = first == null ? previous: first;
                            second = current;
                        }
                    }
                }else{
                    if (previous != null && previous.value > current.value){
                        first = first == null ? previous: first;
                        second = current;
                    }
                }
                previous = current;
                current = current.right;
            }
            assert first != null;
            swap(first, second);
        }

    }

}
