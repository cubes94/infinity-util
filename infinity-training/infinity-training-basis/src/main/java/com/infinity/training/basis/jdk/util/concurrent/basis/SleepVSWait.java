package com.infinity.training.basis.jdk.util.concurrent.basis;

/**
 * 1. sleep()方法是Thread的静态方法，而wait()是Object实例方法。
 * 2. wait()方法必须要在同步方法或者同步块中调用，也就是必须已经获得对象锁。而sleep()方法没有这个限制可以在任何地方种使用。
 * 另外，wait()方法会释放占有的对象锁，使得该线程进入等待池中，等待下一次获取资源。而sleep()方法只是会让出CPU并不会释放掉对象锁；
 * 3. sleep()方法在休眠时间达到后如果再次获得CPU时间片就会继续执行，而wait()方法必须等待Object.notify/Object.notifyAll通知后，才会离开等待池，并且再次获得CPU时间片才会继续执行。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 16:59
 */
public class SleepVSWait {
}
