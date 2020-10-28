package com.infinity.training.basis.jdk.util.concurrent.locks;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * {@link LockSupport#park()}: 阻塞当前线程，如果调用unpark方法或者当前线程被中断，从能从park()方法中返回。
 * {@link LockSupport#unpark(java.lang.Thread)}: 唤醒处于阻塞状态的指定线程。
 * <p>
 * synchronized使线程阻塞，线程会进入到BLOCKED状态，而调用LockSupport方法阻塞线程会使线程进入到WAITING状态。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 00:11
 */
public class LockSupportAnalysis implements Analysis {

    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(() -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        });
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        LockSupport.unpark(thread);
    }
}
