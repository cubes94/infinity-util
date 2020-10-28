package com.infinity.training.basis.jdk.util.concurrent.basis;

/**
 * 如果一个线程实例A执行了threadB.join(),其含义是：当前线程A会等待threadB线程终止后threadA才会继续执行。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 16:54
 */
public class Join {

    private static Thread thread(Thread prev) {
        return new Thread(() -> {
            try {
                prev.join();
                System.out.println(prev.getName() + " terminated.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        Thread prev = Thread.currentThread();
        for (int i = 0; i < 100; i++) {
            prev = thread(prev);
            prev.start();
        }
    }
}
