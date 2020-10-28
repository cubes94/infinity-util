package com.infinity.training.basis.jdk.util.concurrent.basis;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 16:06
 */
public class DeadLock {

    private Object o1 = new Object();

    private Object o2 = new Object();

    private Thread thread(Object o1, Object o2) {
        return new Thread(() -> {
            synchronized (o1) {
                System.out.println("acquire " + o1.toString());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("acquire " + o2.toString());
                }
            }
        });
    }

    public static void main(String[] args) {
        final DeadLock deadLock = new DeadLock();
        deadLock.thread(deadLock.o1, deadLock.o2).start();
        deadLock.thread(deadLock.o2, deadLock.o1).start();
        // 通过jps&jstack查看dump日志
    }
}
