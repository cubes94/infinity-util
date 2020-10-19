package com.infinity.training.basis.algorithm.sort;

/**
 * 桶排序
 * <p>
 * 平均时间复杂度: O(n + k)  [ O(n + k) ~ O(n^2) ]
 * 空间复杂度: O(n + k)
 * 排序方式: Out-place (外部排序)
 * 稳定性: 稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:48
 */
public class BucketSort implements IArraySort {

    /**
     * 设定固定数量的桶，将元素放到对应的桶中，对不为空的桶进行排序，最后拼接桶得到结果。
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        return bucketSort(arr, 5);
    }

    /**
     * 桶排序
     *
     * @param arr        待排序数组
     * @param bucketSize 桶大小
     * @return 排序后的数组
     */
    private int[] bucketSort(int[] arr, int bucketSize) {
        int min = arr[0];
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            } else if (arr[i] > max) {
                max = arr[i];
            }
        }

        int bucketCount = (max - min) / bucketSize + 1;
        int[][] buckets = new int[bucketCount][0];

        // 通过映射行数分配到对应的桶
        for (int value : arr) {
            int bucketIndex = (value - min) / bucketSize;
            buckets[bucketIndex] = IArraySort.appendElement(buckets[bucketIndex], value);
        }

        int index = 0;
        for (int[] bucket : buckets) {
            if (bucket.length > 0) {
                // 对每个桶进行排序，这里使用插入排序
                bucket = new InsertSort().sort(bucket);
                for (int v : bucket) {
                    arr[index++] = v;
                }
            }
        }
        return arr;
    }
}
