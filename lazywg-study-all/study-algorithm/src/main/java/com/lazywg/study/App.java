package com.lazywg.study;

import com.lazywg.study.nowcoder.algorithm.AlgorithmUtil;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//        int[] numArray = {1,2,4,6,7,8,1,2,4,6,9,3,7};
//        System.out.println(AlgorithmUtil.caclMaxHasDoubleMinRangeCount(numArray));
        long b = 1, c = 2;
        for (long i = 0; i < 6; i++) {
            //System.out.println(AlgorithmUtil.foo(i, b, c) % 1000000007);
            System.out.println(AlgorithmUtil.sumFoo(i, b, c));
            System.out.println();
        }

        System.out.println("Hello World!");
    }
}
