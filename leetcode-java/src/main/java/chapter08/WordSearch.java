package chapter08;

import java.util.*;

/**
 * <p>单词搜索问题</p>
 * <p>1. 单词搜索</p>
 * <p>2. 单词搜索 II</p>
 */
public class WordSearch {

    /**
     * <p>单词搜索</p>
     */
    public static class WordSearchI {

        /**
         * <p>回溯: 需要遍历配合回溯, 无法单次回溯完成</p>
         */
        public static boolean exist(char[][] board, String word) {
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (exist(row, col, board, 0, word)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public static boolean exist(int row, int col, char[][] board, int index, String word) {
            if (index == word.length()) {
                return true;
            }
            if (row < 0 || col < 0 || row >= board.length || col >= board[0].length) {
                return false;
            }
            if (board[row][col] < 'a' || board[row][col] > 'z') {
                return false;
            }
            if (board[row][col] != word.charAt(index)) {
                return false;
            }
            board[row][col] += 128;
            boolean isExist = exist(row + 1, col, board, index + 1, word)
                    || exist(row - 1, col, board, index + 1, word)
                    || exist(row, col + 1, board, index + 1, word)
                    || exist(row, col - 1, board, index + 1, word);
            board[row][col] -= 128;
            return isExist;
        }

    }

    /**
     * <p>单词搜索 II</p>
     */
    public static class WordSearchII {

        public static class TrieNode {
            // NOTE: 节点携带的信息
            private String word;
            // NOTE: 节点重复的次数
            private int pass;
            // NOTE: 节点是否为终点
            private int end;
            // NOTE: 节点的子节点
            private TrieNode[] nexts;
        }

        private final static TrieNode root = new TrieNode();

        /**
         * <p>回溯: 需要配合前缀树剪枝, 暴力搜索会超时</p>
         * <p>需要配合前缀树剪枝, 暴力搜索会超时, 优化核心思路: </p>
         * <p>1. 如果对每个单词都在矩阵中采用回溯的方法进行判断, 那么就会导致矩阵无论如何都会被遍历 n 次</p>
         * <p>2. 那么能否仅遍历一次矩阵就可以判断到所有的单词呢? 实际上是可以的, 只需要反过来考虑就行</p>
         * <p>3. 暴力回溯本质是将矩阵当做集合, 而前缀树优化本质是将所有的单词当做集合, 在前缀树中进行搜索</p>
         * <p>4. 对矩阵进行遍历, 只要这个字符满足前缀树的起点, 那么就可以深度遍历, 不满足就直接返回</p>
         */
        public static List<String> findWords(char[][] board, String[] words) {
            Set<String> set = new HashSet<>();
            // NOTE: 构建前缀树
            for (String word : words) {
                insert(word);
            }
            // NOTE: 遍历前缀树
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    findWords(row, col, board, root, set);
                }
            }
            return new ArrayList<>(set);
        }

        /**
         * <p>构建前缀树</p>
         */
        private static void insert(String word) {
            TrieNode node = root;
            node.pass++;
            for (int index = 0; index < word.length(); index++) {
                int location = word.charAt(index) - 'a';
                if (node.nexts[location] == null) {
                    node.nexts[location] = new TrieNode();
                }
                // NOTE: 移动到下一个节点
                node = node.nexts[location];
                // NOTE: 给节点增加信息
                node.pass++;
            }
            // NOTE: 增加叶子节点的标记
            node.end++;
            node.word = word;
        }

        private static void findWords(int row, int col, char[][] board, TrieNode node, Set<String> set) {
            if (row < 0 || col < 0 || row >= board.length || col >= board[0].length) {
                return;
            }
            if (board[row][col] < 'a' || board[row][col] > 'z') {
                return;
            }
            // NOTE: 判断矩阵当前的字符是否在前缀树中存在
            node = node.nexts[board[row][col] - 'a'];
            if (node == null) {
                return;
            }
            if (node.end != 0) {
                // NOTE: 不能立刻返回, 节点是终点不代表是路径的终点
                set.add(node.word);
            }
            board[row][col] += 128;
            findWords(row + 1, col, board, node, set);
            findWords(row - 1, col, board, node, set);
            findWords(row, col + 1, board, node, set);
            findWords(row, col - 1, board, node, set);
            board[row][col] -= 128;
        }

    }


}
