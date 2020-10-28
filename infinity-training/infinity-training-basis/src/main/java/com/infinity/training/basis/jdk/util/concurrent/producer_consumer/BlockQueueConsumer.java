package com.infinity.training.basis.jdk.util.concurrent.producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 使用BlockingQueue实现
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 16:31
 */
public class BlockQueueConsumer implements Runnable {

    private BlockingQueue<Integer> queue;

    public BlockQueueConsumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // take方法，当阻塞队列为空时，获取队头数据的线程会被阻塞
                final Integer i = queue.take();
                System.out.println("消费者" + Thread.currentThread().getName() + "消费数据: " + i);
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
