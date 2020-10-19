package com.infinity.training.basis.algorithm.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 排序测试
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/08/27 23:16
 */
@Slf4j
public class SortTest {

    /**
     * 测试冒泡排序
     */
    @Test
    public void testBubbleSort() {
        new BubbleSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

    /**
     * 测试选择排序
     */
    @Test
    public void testSelectionSort() {
        new SelectionSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

    /**
     * 测试插入排序
     */
    @Test
    public void testInsertSort() {
        new InsertSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

    /**
     * 测试希尔排序
     */
    @Test
    public void testShellSort() {
        new ShellSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

    /**
     * 测试归并排序
     */
    @Test
    public void testMergeSort() {
        new MergeSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

    /**
     * 测试快速排序
     */
    @Test
    public void testQuickSort() {
        new QuickSort().sortAndTiming(SortTestConstants.randomIntArray());
    }

    /**
     * 测试堆排序
     */
    @Test
    public void testHeapSort() {
        new HeapSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

    /**
     * 测试计数排序
     */
    @Test
    public void testCountingSort() {
        new CountingSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

    /**
     * 测试桶排序
     */
    @Test
    public void testBucketSort() {
        new BucketSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

    /**
     * 测试基数排序
     */
    @Test
    public void testRadixSort() {
        new RadixSort().sortAndTimingAndTest(SortTestConstants.randomIntArray());
    }

}