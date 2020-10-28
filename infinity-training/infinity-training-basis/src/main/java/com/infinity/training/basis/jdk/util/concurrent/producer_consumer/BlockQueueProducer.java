package com.infinity.training.basis.jdk.util.concurrent.producer_consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 使用BlockingQueue实现
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 16:32
 */
public class BlockQueueProducer implements Runnable {

    private BlockingQueue<Integer> queue;

    public BlockQueueProducer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Random random = new Random();
                final int i = random.nextInt();
                System.out.println("生产者" + Thread.currentThread().getName() + "生产数据: " + i);
                // 调用put方法，当阻塞队列容量已经满时，往阻塞队列插入数据的线程会被阻塞，直至阻塞队列已经有空余的容量可供使用
                queue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
