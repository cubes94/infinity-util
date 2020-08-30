package com.infinity.training.algorithm.sort;

/**
 * 插入排序
 * <p>
 * 平均时间复杂度: O(n^2)  [ O(n) ~ O(n^2) ]
 * 空间复杂度: O(1)
 * 排序方式: In-place (内存中排序)
 * 稳定性: 稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:46
 */
public class InsertSort implements IArraySort {

    /**
     * 将未排序序列依次扫描插入到排序序列(初始将第一个元素看成一个排序序列)的适当位置(相等的排到后面)。
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            // 待插入的元素
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] > temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            if (j != i) {
                arr[j] = temp;
            }
        }
        return arr;
    }
}
