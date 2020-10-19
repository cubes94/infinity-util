package com.infinity.training.basis.algorithm.sort;

/**
 * 基数排序
 * <p>
 * 平均时间复杂度: O(n * k)  [ O(n * k) ~ O(n * k) ]
 * 空间复杂度: O(n + k)
 * 排序方式: Out-place (外部排序)
 * 稳定性: 稳定
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:49
 */
public class RadixSort implements IArraySort {

    /**
     * 所有元素统一为同样数位长度的值，数位短的前面补0，从最低位到最高位循环进行排序(LSD)。
     * # 同时还存在从最高位开始比较的，称为高位排序(MSD)
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    @Override
    public int[] sort(int[] arr) {
        int max = arr[0];
        int min = arr[0];
        for (int value : arr) {
            if (value > max) {
                max = value;
            } else if (value < min) {
                min = value;
            }
        }
        // 如果负数绝对值比正数大，则将所有元素加上这个负数的绝对值，保证绝对值最大的元素是正数
        int cursor = 0;
        if (min < 0 && -min > max) {
            cursor = -min;
            max += cursor;
            for (int i = 0; i < arr.length; i++) {
                arr[i] += cursor;
            }
        }
        arr = radixSort(arr, max);
        if (cursor != 0) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] -= cursor;
            }
        }
        return arr;
    }

    /**
     * 基数排序
     *
     * @param arr 数组
     * @param max 最大数值
     * @return 数组
     */
    private int[] radixSort(int[] arr, int max) {
        // mod取余
        for (int mod = 10; mod / 10 < max; mod *= 10) {
            // 考虑到负数的情况，这里将容量扩充一倍，[0-9]对应负数，[10-19]对应正数
            int[][] counter = new int[mod * 2][0];
            for (int value : arr) {
                int bucket = ((value % mod) / (mod / 10)) + mod;
                counter[bucket] = IArraySort.appendElement(counter[bucket], value);
            }

            int index = 0;
            for (int[] bucket : counter) {
                for (int value : bucket) {
                    arr[index++] = value;
                }
            }
        }
        return arr;
    }
}
