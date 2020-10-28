package com.infinity.training.basis.jdk.util.concurrent;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 循环栅栏
 * 当多个线程都达到了指定点后，才能继续往下继续执行
 * <p>
 * CountDownLatch与CyclicBarrier都是用于控制并发的工具类，都可以理解成维护的就是一个计数器。
 * CountDownLatch与CyclicBarrier的比较:
 * 1. CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；CountDownLatch强调一个线程等多个线程完成某件事情。CyclicBarrier是多个线程互等，等大家都完成，再携手共进。
 * 2. 调用CountDownLatch的countDown方法后，当前线程并不会阻塞，会继续往下执行；而调用CyclicBarrier的await方法，会阻塞当前线程，直到CyclicBarrier指定的线程全部都到达了指定点的时候，才能继续往下执行；
 * 3. CountDownLatch方法比较少，操作比较简单，而CyclicBarrier提供的方法更多，比如能够通过getNumberWaiting()，isBroken()这些方法获取当前多个线程的状态，并且CyclicBarrier的构造方法可以传入barrierAction，指定当所有线程都到达时执行的业务功能；
 * 4. CountDownLatch是不能复用的，而CyclicLatch是可以复用的。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 15:28
 */
public class CyclicBarrierAnalysis implements Analysis {

    public static void main(String[] args) {
        int count = 6;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(count, () -> System.out.println("all free"));
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " start");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " finished first period");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " finished second period");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " finished all");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
