package com.infinity.training.basis.jdk.util.concurrent;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.Exchanger;

/**
 * {@link Exchanger} 线程间交换数据的工具。
 * <p>
 * Exchanger是一个用于线程间协作的工具类，用于两个线程间能够交换。它提供了一个交换的同步点，在这个同步点两个线程能够交换数据。
 * 具体交换数据是通过exchange方法来实现的，如果一个线程先执行exchange方法，那么它会同步等待另一个线程也执行exchange方法，这个时候两个线程就都达到了同步点，两个线程就可以交换数据。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 15:46
 */
public class ExchangerAnalysis implements Analysis {

    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            try {
                final String exchange = exchanger.exchange("i am " + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " receive: " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                final String exchange = exchanger.exchange("i am " + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " receive: " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
