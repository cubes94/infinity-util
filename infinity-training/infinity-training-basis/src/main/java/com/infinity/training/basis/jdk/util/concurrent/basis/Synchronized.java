package com.infinity.training.basis.jdk.util.concurrent.basis;

/**
 * 根据synchronized使用位置的划分:
 * 1. 实例方法，锁住的是该类的实例对象
 * private synchronized void method() {}
 * 2. 静态方法，锁住的是类对象
 * private static synchronized void method() {}
 * 3. 同步代码块，锁住的是该类的实例对象
 * synchronized (this) {}
 * 4. 同步代码块，锁住的是该类的类对象
 * synchronized (Synchronized.class) {}
 * 5. 同步代码块，锁住的是配置的实例对象
 * String lock = "";
 * synchronized (lock) {}
 * <p>
 * 执行同步代码块后首先要先执行monitorenter指令，退出的时候monitorexit指令。
 * 使用synchronized进行同步，其关键就是必须要对对象的监视器monitor进行获取，当线程获取monitor后才能继续往下执行，否则就只能等待。而这个获取的过程是互斥的，即同一时刻只有一个线程能够获取到monitor。
 * <p>
 * 每个对象拥有一个计数器，当线程获取该对象锁后，计数器就会加一，释放锁后就会将计数器减一。
 * synchronized先天具有重入性，即在同一锁程中，线程不需要再次获取同一把锁。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 21:19
 */
public class Synchronized {

    private static void method() {
    }

    public static void main(String[] args) {
        synchronized (Synchronized.class) {

        }
        method();
    }
}
