package com.infinity.training.basis.jdk.util.concurrent.producer_consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 16:11
 */
public class ProducerConsumer {

    /**
     * 测试使用Object的wait/notify的消息通知机制
     */
    public static void testObject() {
        List<Integer> data = new ArrayList<>();
        final ExecutorService executorService = Executors.newFixedThreadPool(15);
        int capacity = 10;
        for (int i = 0; i < 10; i++) {
            executorService.submit(new ObjectConsumer(data));
        }
        for (int i = 0; i < 5; i++) {
            executorService.submit(new ObjectProducer(data, capacity));
        }
    }

    /**
     * 测试使用Lock的Condition的await/signal的消息通知机制
     */
    public static void testCondition() {
        List<Integer> data = new ArrayList<>();
        Lock lock = new ReentrantLock();
        Condition notEmpty = lock.newCondition();
        Condition notFull = lock.newCondition();
        final ExecutorService executorService = Executors.newFixedThreadPool(15);
        int capacity = 10;
        for (int i = 0; i < 10; i++) {
            executorService.submit(new ConditionConsumer(data, lock, notEmpty, notFull));
        }
        for (int i = 0; i < 5; i++) {
            executorService.submit(new ConditionProducer(data, capacity, lock, notEmpty, notFull));
        }
    }

    /**
     * 测试使用BlockingQueue实现
     */
    public static void testBlockQueue() {
        final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        final ExecutorService executorService = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new BlockQueueConsumer(queue));
        }
        for (int i = 0; i < 5; i++) {
            executorService.submit(new BlockQueueProducer(queue));
        }
    }

    public static void main(String[] args) {
        testObject();
//        testCondition();
//        testBlockQueue();
    }
}
