package com.lazywg.study.nowcoder.algorithm;

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
}
