package com.infinity.training.basis.jdk.util.concurrent.producer_consumer;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 使用Lock的Condition的await/signal的消息通知机制
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 16:17
 */
public class ConditionProducer implements Runnable {


    private List<Integer> data;

    private int capacity;

    private Lock lock;

    private Condition notEmpty;

    private Condition notFull;

    public ConditionProducer(List<Integer> data, int capacity, Lock lock, Condition notEmpty, Condition notFull) {
        this.data = data;
        this.capacity = capacity;
        this.lock = lock;
        this.notEmpty = notEmpty;
        this.notFull = notFull;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (data.size() == capacity) {
                    System.out.println("生产者" + Thread.currentThread().getName() + " data容量已满，进行wait");
                    notFull.await();
                    System.out.println("生产者" + Thread.currentThread().getName() + "退出wait");
                }
                final Random random = new Random();
                final int i = random.nextInt();
                System.out.println("生产者" + Thread.currentThread().getName() + "生产数据: " + i);
                data.add(i);
                // 这里调用signalAll是因为可能存在多个生产者，如果唤醒生产者是没有用的
                notEmpty.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
