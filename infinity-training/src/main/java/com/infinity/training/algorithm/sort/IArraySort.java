package com.infinity.training.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 排序接口
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:52
 */
public interface IArraySort {

    Logger LOG = LoggerFactory.getLogger(IArraySort.class);

    /**
     * 排序
     *
     * @param arr 待排序数组
     * @return 排序后的数组
     */
    int[] sort(int[] arr);

    /**
     * 数组元素交换
     *
     * @param arr 数组
     * @param i   i
     * @param j   j
     */
    default void swap(int[] arr, int i, int j) {
//        arr[i] = arr[i] ^ arr[j];
//        arr[j] = arr[i] ^ arr[j];
//        arr[i] = arr[i] ^ arr[j];
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 数组取最大元素
     *
     * @param arr 数组
     * @return 最大元素
     */
    default int maxElement(int[] arr) {
        int max = arr[0];
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 数组扩容保存数据
     *
     * @param arr 数组
     * @param ele 元素
     * @return 扩容后的数组
     */
    default int[] appendElement(int[] arr, int ele) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = ele;
        return arr;
    }

    /**
     * 排序(包括计时)
     *
     * @param sourceArray 待排序数组
     * @return 排序后的数组
     */
    default int[] sortAndTiming(int[] sourceArray) {
        final int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        long start = System.currentTimeMillis();
        final int[] result = this.sort(arr);
        long end = System.currentTimeMillis();
//        final Gson gson = new Gson();
//        LOG.info("param: {}, \n result: {}, \n time: {}", gson.toJson(sourceArray), gson.toJson(result), end - start);
        LOG.info("time: {}", end - start);
        return result;
    }

    /**
     * 排序(包括计时和测试)
     *
     * @param sourceArray 待排序数组
     * @return 排序后的数组
     */
    default int[] sortAndTimingAndTest(int[] sourceArray) {
        final int[] result = this.sortAndTiming(sourceArray);
        // 测试排序算法选择快速排序
        IArraySort sort = new QuickSort();
        final int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        long start = System.currentTimeMillis();
        final int[] testResult = sort.sort(arr);
        long end = System.currentTimeMillis();
        final boolean flag = Arrays.equals(result, testResult);
        LOG.info("test time: {}, flag: {}", end - start, flag);
        return result;
    }
}
