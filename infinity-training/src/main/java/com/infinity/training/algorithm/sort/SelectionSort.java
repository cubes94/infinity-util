package com.infinity.training.algorithm.sort;

/**
 * 选择排序
 * <p>
 * 平均时间复杂度: O(n^2)  [ O(n^2) ~ O(n^2) ]
 * 空间复杂度: O(1)
 * 排序方式: In-place (内存中排序)
 * 稳定性: 不稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:10
 */
public class SelectionSort implements IArraySort {

    /**
     * 在未排序的序列中，找到最小的元素，存放到排序序列的起始位置。
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        final int length = arr.length;
        for (int i = 0; i < length; i++) {
            int minIndex = i;
            // 在未排序数组中找到最小的元素
            for (int j = i + 1; j < length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 将最小的元素交换到前面
            if (minIndex != i) {
                swap(arr, i, minIndex);
            }
        }
        return arr;
    }
}
