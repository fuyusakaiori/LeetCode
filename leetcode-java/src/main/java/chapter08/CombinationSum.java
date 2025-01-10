package chapter08;

import java.util.*;

/**
 * <h2>组合总和问题</h2>
 * <p>1. 组合总和</p>
 * <p>2. 组合总和 II</p>
 * <p>3. 组合总和 III</p>
 * <p>4. 组合总和 IV</p>
 * <p>5. 组合</p>
 */
public class CombinationSum {

    /**
     * <p>组合总和: 无重复元素, 可以重复选择, 不允许组合重复</p>
     * <p>如果没有重复元素, 那么只要不回头选, 那么必然不可能存在重复的组合</p>
     */
    public static class CombinationSumI {

        /**
         * <p>回溯: 第一种写法, 不使用循环</p>
         */
        public static List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<Integer> combination = new ArrayList<>();
            List<List<Integer>> combinations = new ArrayList<>();
            combinationSum(0, target, candidates, combination, combinations);
            return combinations;
        }
        
        public static void combinationSum(int index, int target, int[] candidates, List<Integer> combination, List<List<Integer>> combinations) {
            // NOTE: 符合条件的终止条件最好放在最前面, 可能会有在数组末尾满足条件的情况
            if (target == 0) {
                combinations.add(new ArrayList<>(combination));
                return;
            }
            if (index >= candidates.length || target < 0) {
                return;
            }
            combinationSum(index + 1, target, candidates, combination, combinations);
            combination.add(candidates[index]);
            combinationSum(index , target - candidates[index], candidates, combination, combinations);
            combination.remove(combination.size() - 1);
        }

        /**
         * <p>回溯: 第二种写法, 使用循环</p>
         */
        public static List<List<Integer>> combinationSumInnerLoop(int[] candidates, int target) {
            List<Integer> combination = new ArrayList<>();
            List<List<Integer>> combinations = new ArrayList<>();
            combinationSumInnerLoop(0, target, candidates, combination, combinations);
            return combinations;
        }

        public static void combinationSumInnerLoop(int start, int target, int[] candidates, List<Integer> combination, List<List<Integer>> combinations) {
            if (target == 0) {
                combinations.add(new ArrayList<>(combination));
                return;
            }
            if (start >= candidates.length || target < 0) {
                return;
            }
            // NOTE: 循环的目的就是模拟不选择某个数字的情况 <=> dfs(index + 1, target, candidates, combination, combinations)
            for (int index = start; index < candidates.length; index++) {
                combination.add(candidates[index]);
                // NOTE: 因为可以重复选择, 所以指针不能向前移动
                combinationSumInnerLoop(index, target - candidates[index], candidates, combination, combinations);
                combination.remove(combination.size() - 1);
            }
        }

    }

    /**
     * <p>组合总和 II: 有重复元素, 不可以重复选择, 不允许组合重复</p>
     */
    public static class CombinationSumII {

        /**
         * <p>回溯: 第一种写法, 不使用循环</p>
         */
        public static List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<Integer> combination = new ArrayList<>();
            List<List<Integer>> combinations = new ArrayList<>();
            // NOTE: 如果需要去重, 那么不使用额外空间最好的做法就是排序, 然后比较前后两个值
            Arrays.sort(candidates);
            combinationSum(0, target, false, candidates, combination, combinations);
            return combinations;
        }

        public static void combinationSum(int index, int target, boolean flag, int[] candidates, List<Integer> combination, List<List<Integer>> combinations) {
            if (target == 0) {
                combinations.add(new ArrayList<>(combination));
                return;
            }
            if (index >= candidates.length || target < 0) {
                return;
            }
            combinationSum(index + 1, target, false, candidates, combination, combinations);
            // NOTE: 在选择数字之前, 需要判断前后数字是否相同; 如果还没有开始选择数字, 那么只要前后数字相同的话, 就不需要选择这个数字, 因为会和前一个重复
            if (!flag && index > 0 && candidates[index] == candidates[index - 1]) {
                return;
            }
            combinationSum(index + 1, target - candidates[index], true, candidates, combination, combinations);
        }

        /**
         * <p>回溯: 第二种写法</p>
         */
        public static List<List<Integer>> combinationSumInnerLoop(int[] candidates, int target) {
            List<Integer> combination = new ArrayList<>();
            List<List<Integer>> combinations = new ArrayList<>();
            // NOTE: 如果需要去重, 那么不使用额外空间最好的做法就是排序, 然后比较前后两个值
            Arrays.sort(candidates);
            combinationSumInnerLoop(0, target, candidates, combination, combinations);
            return combinations;
        }

        public static void combinationSumInnerLoop(int start, int target, int[] candidates, List<Integer> combination, List<List<Integer>> combinations) {
            if (target == 0) {
                combinations.add(new ArrayList<>(combination));
                return;
            }
            if (start >= candidates.length || target < 0) {
                return;
            }
            for (int index = start; index < candidates.length; index++) {
                // NOTE: 去除可能重复选择的组合
                if (index > start && candidates[index] == candidates[index - 1]) {
                    continue;
                }
                combination.add(candidates[index]);
                combinationSumInnerLoop(index + 1, target - candidates[index], candidates, combination, combinations);
                combination.remove(combination.size() - 1);
            }
        }

    }

    /**
     * <p>组合总和 III: 无重复元素, 不可以重复选择, 不允许组合重复</p>
     */
    public static class CombinationSumIII {

        /**
         * <p>回溯: 第一种写法, 不使用循环</p>
         */
        public static List<List<Integer>> combinationSum(int count, int target) {
            List<Integer> combination = new ArrayList<>();
            List<List<Integer>> combinations = new ArrayList<>();
            combinationSum(1, count, target, combination, combinations);
            return combinations;
        }

        public static void combinationSum(int value, int count, int target, List<Integer> combination, List<List<Integer>> combinations) {
            if (target == 0 && count == 0) {
                combinations.add(new ArrayList<>(combination));
                return;
            }
            // NOTE: 哪些条件下需要终止
            if (value > 9 || count < 0 || target < 0) {
                return;
            }
            combinationSum(value + 1, count, target, combination, combinations);
            combination.add(value);
            combinationSum(value + 1, count - 1, target - value, combination, combinations);
            combination.remove(combination.size() - 1);
        }

        /**
         * <p>回溯: 第二种写法, 使用循环</p>
         */
        public static List<List<Integer>> combinationSumInnerLoop(int count, int target) {
            List<Integer> combination = new ArrayList<>();
            List<List<Integer>> combinations = new ArrayList<>();
            combinationSumInnerLoop(1, count, target, combination, combinations);
            return combinations;
        }

        public static void combinationSumInnerLoop(int start, int count, int target, List<Integer> combination, List<List<Integer>> combinations) {
            if (target == 0 && count == 0) {
                combinations.add(new ArrayList<>(combination));
                return;
            }
            if (start > 9 || count < 0 || target < 0) {
                return;
            }
            // NOTE: 循环的目的就是模拟不选择某个数字的情况 <=> dfs(index + 1, target, candidates, combination, combinations)
            for (int value = start; value <= 9; value++) {
                combination.add(value);
                // NOTE: 因为不可以重复选择, 所以指针向前移动
                combinationSumInnerLoop(value + 1, count - 1, target - value, combination, combinations);
                combination.remove(combination.size() - 1);
            }
        }

    }

    /**
     * <p>组合总和 IV: 无重复元素, 可以重复选择, 允许组合重复</p>
     */
    public static class CombinationSumIV {

        /**
         * <p>回溯: 记忆化搜索</p>
         * <p>因为需要重新回过头选择之前的元素, 所以只能使用循环的方式处理</p>
         */
        public static int combinationSum(int target, int[] nums) {
            Integer[] dp = new Integer[target + 1];
            return combinationSum(target, nums, dp);
        }

        public static int combinationSum(int target, int[] nums, Integer[] dp) {
            if (target == 0) {
                return 1;
            }
            if (target < 0) {
                return 0;
            }
            if (dp[target] != null) {
                return dp[target];
            }
            int count = 0;
            for (int index = 0; index < nums.length; index++) {
                count += combinationSum(target - nums[index], nums, dp);
            }
            dp[target] = count;
            return count;
        }

    }

    /**
     * <p>组合: 无重复元素, 不可以重复选择, 不允许组合重复</p>
     */
    public static class Combination {

        /**
         * <p>回溯: 第一种写法, 不使用循环</p>
         */
        public static List<List<Integer>> combine(int number, int count) {
            List<Integer> combination = new ArrayList<>();
            List<List<Integer>> combinations = new ArrayList<>();
            combine(number, count, combination, combinations);
            return combinations;
        }

        public static void combine(int number, int count, List<Integer> combination, List<List<Integer>> combinations) {
            if (count == 0) {
                combinations.add(new ArrayList<>(combination));
                return;
            }
            if (number == 0) {
                return;
            }
            combine(number - 1, count, combination, combinations);
            combination.add(number);
            combine(number - 1, count - 1, combination, combinations);
            combination.remove(combination.size() - 1);
        }

        /**
         * <p>回溯: 第二种写法</p>
         */
        public static List<List<Integer>> combineInnerLoop(int number, int count) {
            List<Integer> combination = new ArrayList<>();
            List<List<Integer>> combinations = new ArrayList<>();
            combineInnerLoop(number, count, combination, combinations);
            return combinations;
        }


        public static void combineInnerLoop(int number, int count, List<Integer> combination, List<List<Integer>> combinations) {
            if (count == 0) {
                combinations.add(new ArrayList<>(combination));
                return;
            }
            if (number == 0) {
                return;
            }
            for (int value = number; value > 0; value--) {
                combination.add(value);
                combineInnerLoop(value - 1, count - 1, combination, combinations);
                combination.remove(combination.size() - 1);
            }
        }

    }

}
