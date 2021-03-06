package com.infinity.training.basis.jdk.util.concurrent.producer_consumer;

import java.util.List;
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
public class ConditionConsumer implements Runnable {

    private List<Integer> data;

    private Lock lock;

    private Condition notEmpty;

    private Condition notFull;

    public ConditionConsumer(List<Integer> data, Lock lock, Condition notEmpty, Condition notFull) {
        this.data = data;
        this.lock = lock;
        this.notEmpty = notEmpty;
        this.notFull = notFull;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (data.isEmpty()) {
                    System.out.println("消费者" + Thread.currentThread().getName() + " data已空，进行wait");
                    notEmpty.await();
                    System.out.println("消费者" + Thread.currentThread().getName() + " 退出wait");
                }
                final Integer i = data.remove(0);
                System.out.println("消费者" + Thread.currentThread().getName() + "消费数据: " + i);
                notFull.signalAll();
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
