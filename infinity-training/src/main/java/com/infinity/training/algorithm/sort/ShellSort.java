package com.infinity.training.algorithm.sort;

/**
 * 希尔排序(递减增量排序算法，插入排序的改进版本)
 * <p>
 * 平均时间复杂度: O(n log n)  [ O(n log^2 n) ~ O(n log^2 n) ]
 * 空间复杂度: O(1)
 * 排序方式: In-place (内存中排序)
 * 稳定性: 不稳定
 * <p>
 * # 插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率。
 * # 希尔排序的基本思想是：先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，待整个序列中的记录基本有序时，再对全体记录进行依次直接插入排序。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:46
 */
public class ShellSort implements IArraySort {

    /**
     * 选择一个尽量大的gap去分割待排序序列成若干个子序列，对子序列进行插入排序，然后减小gap的值重复此操作，直到最后对整个序列进行插入排序。
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        int gap = 1;
        final int length = arr.length;
        while (gap < length) {
            gap = gap * 3 + 1;
        }
        while (gap > 0) {
            // 通过gap分割成若干个子数组，进行插入排序
            for (int i = gap; i < length; i++) {
                int temp = arr[i];
                int j = i;
                while (j > gap - 1 && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                if (j != i) {
                    arr[j] = temp;
                }
            }
            // 缩小gap的范围，重新进行排序
            gap = gap / 3;
        }
        return arr;
    }
}
