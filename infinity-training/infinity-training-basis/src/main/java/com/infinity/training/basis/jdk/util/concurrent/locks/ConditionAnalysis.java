package com.infinity.training.basis.jdk.util.concurrent.locks;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 从整体上来看Object的wait和notify/notifyAll是与对象监视器配合完成线程间的等待/通知机制，而Condition与Lock配合完成等待通知机制，使用await和signal/signalAll方法。
 * 前者是java底层级别的，后者是语言级别的，具有更高的可控制性和扩展性。
 * <p>
 * 两者除了在使用方式上不同外，在功能特性上还是有很多的不同：
 * 1. Condition能够支持不响应中断，而通过使用Object方式不支持。
 * 2. Condition能够支持多个等待队列（new 多个Condition对象），而Object方式只能支持一个。
 * 3. Condition能够支持超时时间的设置，而Object不支持。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 23:44
 */
public class ConditionAnalysis implements Analysis {

    private final Lock lock;

    private final Condition condition;

    public ConditionAnalysis() {
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
    }

    public void waitTask() {
        System.out.println(Thread.currentThread().getName() + "阻塞");
        lock.lock();
        try {
            // 当调用condition.await()方法后会使得当前获取lock的线程进入到等待队列，如果该线程能够从await()方法返回的话一定是该线程获取了与condition相关联的lock。
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalTask() {
        System.out.println(Thread.currentThread().getName() + "发起唤醒");
        lock.lock();
        try {
            // 调用condition的signal的前提条件是当前线程已经获取了lock，该方法会使得等待队列中的头节点即等待时间最长的那个节点移入到同步队列，
            // 而移入到同步队列后才有机会使得等待线程被唤醒，即从await方法中的LockSupport.park(this)方法中返回，从而才有机会使得调用await方法的线程成功退出。
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ConditionAnalysis conditionTest = new ConditionAnalysis();
        final Thread waitTask = new Thread(() -> {
            conditionTest.waitTask();
            System.out.println(Thread.currentThread().getName() + "唤醒成功");
        });
        final Thread signalTask = new Thread(conditionTest::signalTask);
        waitTask.start();
        TimeUnit.SECONDS.sleep(2);
        signalTask.start();
    }
}
