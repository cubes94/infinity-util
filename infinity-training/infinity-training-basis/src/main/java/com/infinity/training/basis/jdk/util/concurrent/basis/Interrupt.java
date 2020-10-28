package com.infinity.training.basis.jdk.util.concurrent.basis;

import java.util.concurrent.TimeUnit;

/**
 * 中断
 * <p>
 * 中断可以理解为线程的一个标志位，它表示了一个运行中的线程是否被其他线程进行了中断操作。
 * <p>
 * 1. 其他线程可以调用该线程的interrupt()方法对其进行中断操作，同时该线程可以调用isInterrupted()来感知其他线程对其自身的中断操作，从而做出响应。
 * 2. 另外，同样可以调用Thread的静态方法interrupted()对当前线程进行中断操作，该方法会清除中断标志位。
 * 3. 需要注意的是，当抛出InterruptedException时候，会清除中断标志位，也就是说在调用isInterrupted会返回false。
 * <p>
 * java.lang.Thread#interrupt(): 中断该线程。 (如果该线程被调用了Object.wait/Object.wait(long), 或者被调用了sleep(long), join/join(long)方法时会抛出InterruptedException, 并且中断标志位被清除)。
 * java.lang.Thread#isInterrupted(): 判断该线程是否被中断。 (不会清除中断标志位)
 * java.lang.Thread#interrupted(): 判断该线程是否被中断。 (会清除中断标志位)
 * <p>
 * 作用：一般在结束线程时通过中断标志位或者标志位的方式可以有机会去清理资源，相对于武断而直接的结束线程，这种方式要优雅和安全。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 16:40
 */
public class Interrupt {

    public static void main(String[] args) {
        final Thread sleepThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        final Thread busyThread = new Thread(() -> {
            while (true) ;
        });
        sleepThread.start();
        busyThread.start();
        sleepThread.interrupt();
        busyThread.interrupt();
        // 等待sleepThread抛出InterruptedException清除中断标志位
        while (sleepThread.isInterrupted()) ;
        // 由于调用了sleep(long), 被interrupt后抛出InterruptedException并清除中断标志位，所以返回false
        System.out.println("sleepThread isInterrupted: " + sleepThread.isInterrupted());
        // 返回true
        System.out.println("busyThread isInterrupted: " + busyThread.isInterrupted());
    }
}
