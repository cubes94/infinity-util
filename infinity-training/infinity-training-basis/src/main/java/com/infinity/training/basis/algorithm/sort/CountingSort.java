package com.infinity.training.basis.algorithm.sort;

/**
 * 计数排序
 * <p>
 * 平均时间复杂度: O(n + k)  [ O(n + k) ~ O(n + k) ]
 * 空间复杂度: O(k)
 * 排序方式: Out-place (外部排序)
 * 稳定性: 稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:48
 */
public class CountingSort implements IArraySort {

    /**
     * 根据序列A的最小值min和最大值max构建一个长度为max-min+1新的序列B，B中的元素对应为A中某元素出现的次数，最后按顺序输出B中的元素。
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        int[] bucket = new int[IArraySort.maxElement(arr) + 1];
        for (int value : arr) {
            bucket[value] += 1;
        }
        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i] > 0) {
                arr[index] = i;
                bucket[i]--;
                index++;
            }
        }
        return arr;
    }
}
