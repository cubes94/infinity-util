package com.infinity.training.basis.algorithm.sort;

/**
 * 快速排序
 * <p>
 * 平均时间复杂度: O(n log n)  [ O(n log n) ~ O(n^2) ]
 * 空间复杂度: O(log n)
 * 排序方式: In-place (内存中排序)
 * 稳定性: 不稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:47
 */
public class QuickSort implements IArraySort {

    /**
     * 1. 从序列中挑出一个元素，称为基准(pivot)；
     * 2. 重新排序序列，所有元素比基准值小的摆放在基准的前面，大的放后面，相同的任意一边。在这个分区退出之后，该基准就处于数组的中间位置，这个称为分区(partition)操作；
     * 3. 递归地(recursive)把小于基准值元素的子序列和大于基准值的子序列排序；
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * 快排
     *
     * @param arr   数组
     * @param left  left
     * @param right right
     */
    private void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            final int partition = partition(arr, left, right);
            quickSort(arr, left, partition - 1);
            quickSort(arr, partition + 1, right);
        }
    }

    /**
     * 分区
     *
     * @param arr   数组
     * @param left  left
     * @param right right
     * @return 基准值的位置
     */
    private int partition(int[] arr, int left, int right) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                IArraySort.swap(arr, index, i);
                index++;
            }
        }
        IArraySort.swap(arr, pivot, index - 1);
        return index - 1;
    }
}
