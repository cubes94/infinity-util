package com.infinity.training.basis.jdk.util.concurrent;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时器
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 14:51
 */
public class CountDownLatchAnalysis implements Analysis {

    public static void main(String[] args) throws InterruptedException {
        // 初始计数
        int count = 6;
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        // 定义线程池，线程数为count，每个线程执行完毕之后调用java.util.concurrent.CountDownLatch.countDown减少计数
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " start");
                System.out.println(Thread.currentThread().getName() + " end");
                countDownLatch.countDown();
            });
        }
        // 主线程阻塞直到计数为0
        countDownLatch.await();
        System.out.println("all finished");
        executorService.shutdown();
    }
}
