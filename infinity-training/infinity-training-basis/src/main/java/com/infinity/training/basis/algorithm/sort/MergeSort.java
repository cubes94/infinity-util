package com.infinity.training.basis.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 * <p>
 * 平均时间复杂度: O(n log n)  [ O(n log n) ~ O(n log n) ]
 * 空间复杂度: O(n)
 * 排序方式: Out-place (外部排序)
 * 稳定性: 稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:47
 */
public class MergeSort implements IArraySort {

    /**
     * 1. 将待排序序列拆分为最小到一个元素的子序列；
     * 2. 子序列合并为有序序列。
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }
        int middle = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr, middle, arr.length);
        return merge(sort(left), sort(right));
    }

    /**
     * 两个有序数组合并为一个有序数组
     *
     * @param left  left数组
     * @param right right数组
     * @return 合并后的数组
     */
    private int[] merge(int[] left, int[] right) {
        final int length = left.length + right.length;
        int[] result = new int[length];
        int l = 0;
        int r = 0;
        int i;
        for (i = 0; l < left.length && r < right.length; i++) {
            if (left[l] <= right[r]) {
                result[i] = left[l];
                l++;
            } else {
                result[i] = right[r];
                r++;
            }
        }
        for (int j = l; j < left.length; j++) {
            result[i] = left[j];
            i++;
        }
        for (int j = r; j < right.length; j++) {
            result[i] = right[j];
            i++;
        }
        return result;
    }
}
