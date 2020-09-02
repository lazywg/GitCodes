package com.lazywg.study.nowcoder.algorithm;

import java.util.ArrayList;

/**
 * @author gaowang
 * @createTim 2020/8/30 15:51
 */
public final class AlgorithmUtil {
    private AlgorithmUtil() {
    }

    /**
     * 牛妹有一个数组array，她想知道里面有多少个区间满足区间最大值大于等于区间最小值的两倍。
     * 输入：
     * 给定Array数组
     * <p>
     * <p>
     * 输出：
     * 返回满足条件的区间个数
     */
    public static int caclMaxHasDoubleMinRangeCount(int[] array) {
        int count = 0, min = 0, max = 0;
        for (int i = 2; i <= array.length; i++) {
            for (int j = 0; j < (array.length - i + 1); j++) {
                min = array[j];
                max = array[j];
                for (int k = j + 1; k < j + i; k++) {
                    if (min > array[k]) {
                        min = array[k];
                    } else if (max < array[k]) {
                        max = array[k];
                    }
                }
                if (max == 2 * min) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * 链接：https://www.nowcoder.com/questionTerminal/3dfb8d459a2e439bb041c2503d14e5c2?toCommentId=7071183
     *
     * @param n
     * @param b
     * @param c
     * @return
     */
    public static long foo(long n, long b, long c) {
        if (n == 0) return 0L;
        if (n == 1) return 1L;
        return b * foo(n - 1, b, c) + c * foo(n - 2, b, c);
    }

    /**
     * 链接：https://www.nowcoder.com/questionTerminal/3dfb8d459a2e439bb041c2503d14e5c2?toCommentId=7071183
     *
     * @param n
     * @param b
     * @param c
     * @return
     */
    public static long sumFoo(long n, long b, long c) {
        if (n == 0) return 0L;
        if (n == 1) return 1L;
        long fn_1 = 1L;
        long fn_2 = 0L;
        long tmp = 0L;
        for (long i = 2L; i < n; i++) {
            tmp = fn_1;
            fn_1 = (b * fn_1 + c * fn_2) % 1000000007;
            fn_2 = tmp;
        }
        long sum = b * fn_1 + c * fn_2;
        return sum % 1000000007;
    }

    /**
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     *
     * @param target
     * @param array
     * @return
     */
    public static boolean Find(int target, int[][] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        // 左下角寻找
        int rowIndex = array.length - 1;
        int cellIndex = 0;
        int cellSize = array[0].length;
        int val = 0;
        while (true) {
            val = array[rowIndex][cellIndex];
            if (val == target) {
                return true;
            } else if (val < target) {
                cellIndex++;
            } else {
                rowIndex--;
            }
            if (rowIndex < 0 || cellIndex + 1 > cellSize) {
                return false;
            }
        }
    }

    /**
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     *
     * @param str
     * @return
     */
    public static String replaceSpace(StringBuffer str) {
        if (str == null || str.length() < 1) {
            return "";
        }
        char empty = ' ';
        String emptyReplace = "%20";
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == empty) {
                str.deleteCharAt(i);
                str.insert(i, emptyReplace);
                i++;
            }
        }
        return str.toString();
    }

    /**
     * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
     *
     * @param listNode
     * @return
     */
    public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> nodeList = new ArrayList<Integer>();
        if (listNode == null) {
            return nodeList;
        }
        nodeList.add(listNode.val);
        ListNode next = listNode.next;
        while (next != null) {
            nodeList.add(next.val);
            next = next.next;
        }
        if (nodeList.size() == 1) {
            return nodeList;
        }
        Integer tmp = 0;
        int size = nodeList.size();
        for (int i = 0; i < (nodeList.size() + 1) / 2; i++) {
            tmp = nodeList.get(i);
            nodeList.set(i, nodeList.get(size - i - 1));
            nodeList.set(size - i - 1, tmp);
        }
        return nodeList;
    }
}
