package com.infinity.training.basis.jdk.util.concurrent.producer_consumer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 使用Object的wait/notify的消息通知机制
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 16:08
 */
public class ObjectConsumer implements Runnable {

    private List<Integer> data;

    public ObjectConsumer(List<Integer> data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (data) {
                try {
                    while (data.isEmpty()) {
                        System.out.println("消费者" + Thread.currentThread().getName() + " data已空，进行wait");
                        data.wait();
                        System.out.println("消费者" + Thread.currentThread().getName() + " 退出wait");
                    }
                    final Integer i = data.remove(0);
                    System.out.println("消费者" + Thread.currentThread().getName() + "消费数据: " + i);
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
