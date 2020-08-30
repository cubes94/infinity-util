package com.infinity.training.algorithm.sort;

/**
 * 冒泡排序
 * <p>
 * 平均时间复杂度: O(n^2)  [ O(n) ~ O(n^2) ]
 * 空间复杂度: O(1 )
 * 排序方式: In-place (内存中排序)
 * 稳定性: 稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 20:59
 */
public class BubbleSort implements IArraySort {

    /**
     * 在未排序的序列中，依次比较相邻元素，值大的元素替换到后面。
     * #. 如果在一次遍历中没有元素需要替换，则说明序列已经排序完毕，可以提前结束。
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        final int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            // 标记本次循环是否进行元素交换
            boolean flag = true;
            // 依次将最大的元素放到未排序数组的最后面
            for (int j = 1; j < length - i; j++) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j - 1, j);
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        return arr;
    }
}
