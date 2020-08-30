package com.infinity.training.algorithm.sort;

/**
 * 排序测试-常量
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 23:20
 */
public interface SortTestConstants {

    /**
     * 随机int数组
     *
     * @return 随机int数组
     */
    static int[] randomIntArray() {
//        int max = 999;
//        int minSize = 8;
//        return randomIntArray(max, minSize + (int) (Math.random() * 88));
        int max = 99999;
        int minSize = 88;
        return randomIntArray(max, minSize + (int) (Math.random() * 88888));
    }

    /**
     * 随机int数组
     *
     * @param max  最大值
     * @param size 数组大小
     * @return 随机int数组
     */
    static int[] randomIntArray(int max, int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * max);
        }
        return arr;
    }
}
