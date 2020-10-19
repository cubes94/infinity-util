package com.infinity.training.basis.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/**
 * 排序接口
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 21:52
 */
public interface IArraySort {

    StopWatch STOP_WATCH = new StopWatch();

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
    static void swap(int[] arr, int i, int j) {
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
    static int maxElement(int[] arr) {
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
    static int[] appendElement(int[] arr, int ele) {
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
        STOP_WATCH.start("sortAndTiming");
        final int[] result = this.sort(arr);
        STOP_WATCH.stop();
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
        STOP_WATCH.start("quickSort sortAndTiming");
        final int[] testResult = sort.sort(arr);
        STOP_WATCH.stop();
        final boolean flag = Arrays.equals(result, testResult);
        LOG.info("flag: {}, \r\n {}", flag, STOP_WATCH.prettyPrint());
        return result;
    }
}
