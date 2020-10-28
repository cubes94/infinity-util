package com.infinity.training.basis.jdk.util.concurrent.producer_consumer;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 使用Object的wait/notify的消息通知机制
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 16:07
 */
public class ObjectProducer implements Runnable {

    private List<Integer> data;

    private int capacity;

    public ObjectProducer(List<Integer> data, int capacity) {
        this.data = data;
        this.capacity = capacity;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (data) {
                try {
                    while (data.size() == capacity) {
                        System.out.println("生产者" + Thread.currentThread().getName() + " data容量已满，进行wait");
                        data.wait();
                        System.out.println("生产者" + Thread.currentThread().getName() + "退出wait");
                    }
                    final Random random = new Random();
                    final int i = random.nextInt();
                    System.out.println("生产者" + Thread.currentThread().getName() + "生产数据: " + i);
                    data.add(i);
                    // 这里调用notifyAll是因为可能存在多个生产者，如果唤醒生产者是没有用的
                    data.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
