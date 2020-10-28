package com.infinity.training.basis.jdk.util.concurrent.basis;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程是一种特殊的线程，就和它的名字一样，它是系统的守护者，在后台默默地守护一些系统服务，比如垃圾回收线程，JIT线程就可以理解守护线程。
 * 与之对应的就是用户线程，用户线程就可以认为是系统的工作线程，它会完成整个系统的业务操作。用户线程完全结束后就意味着整个系统的业务任务全部结束了，因此系统就没有对象需要守护的了，守护线程自然而然就会退出。当一个Java应用，只有守护线程的时候，虚拟机就会自然退出。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 17:06
 */
public class Daemon {

    public static void main(String[] args) {
        final Thread daemon = new Thread(() -> {
            while (true) {
                System.out.println("i am alive");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 守护线程在退出的时候并不会执行finally块中的代码，所以将释放资源等操作不要放在finally块中执行，这种操作是不安全的
                    System.out.println("finally block");
                }
            }
        });
        daemon.setDaemon(true);
        daemon.start();
        // 确保main线程结束前能让daemonThread能够分到时间片
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
