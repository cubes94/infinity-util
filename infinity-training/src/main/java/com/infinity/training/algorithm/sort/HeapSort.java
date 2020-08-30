package com.infinity.training.algorithm.sort;

/**
 * 堆排序
 * <p>
 * 平均时间复杂度: O(n log n)  [ O(n log n) ~ O(n log n) ]
 * 空间复杂度: O(1)
 * 排序方式: In-place (内存中排序)
 * 稳定性: 不稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:47
 */
public class  HeapSort implements IArraySort {

    /**
     * 构建大顶堆，替换顶点元素和最后一个元素，剩余的元素重新保持大顶堆，重复此操作。
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        // 构建大顶堆
        this.buildMaxHeap(arr);

        // 替换顶点元素和最后一位元素，数组长度减一，剩余元素重新转换大顶堆
        int length = arr.length;
        while (length > 1) {
            swap(arr, 0, length - 1);
            length--;
            heapify(arr, 0, length);
        }
        return arr;
    }

    /**
     * 构建大顶堆
     * <p>
     * 堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
     *
     * @param arr 数组
     */
    private void buildMaxHeap(int[] arr) {
        // 选取从最后一个存在子结点的元素开始，进行堆化
        for (int i = arr.length / 2; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
    }

    /**
     * 堆化
     *
     * @param arr 数组
     * @param i   需要转成大顶堆的顶点位置
     * @param len 需要保持大顶堆的数组长度
     */
    private void heapify(int[] arr, int i, int len) {
        // 父结点索引
        int largest = i;
        // 左子结点索引
        int left = i * 2 + 1;
        // 右子结点索引
        int right = left + 1;

        // 找出元素值最大的索引
        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            // 替换最大值元素和父元素
            swap(arr, i, largest);
            // 由于父元素被替换到了子结点，需要保证该子结点为顶点的树为最大堆，所以要重新堆化这个树
            heapify(arr, largest, len);
        }
    }
}
