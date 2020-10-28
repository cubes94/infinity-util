package com.infinity.training.basis.jdk.util.concurrent.locks;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * <p>
 * 针对这种读多写少的情况，java还提供了另外一个实现Lock接口的ReentrantReadWriteLock(读写锁)。
 * <p>
 * 读写锁允许同一时刻被多个读线程访问，但是在写线程访问时，所有的读线程和其他的写线程都会被阻塞。
 * <p>
 * 1. 公平性选择：支持非公平性（默认）和公平的锁获取方式，吞吐量还是非公平优于公平；
 * 2. 重入性：支持重入，读锁获取后能再次获取，写锁获取之后能够再次获取写锁，同时也能够获取读锁；
 * 3. 锁降级：遵循获取写锁，获取读锁再释放写锁的次序，写锁能够降级成为读锁。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 02:36
 */
public class ReentrantReadWriteLockAnalysis {

    // 默认非公平锁
    private ReentrantReadWriteLock lock;

    private ReentrantReadWriteLock.ReadLock readLock;

    private ReentrantReadWriteLock.WriteLock writeLock;

    private int[] data;

    public ReentrantReadWriteLockAnalysis() {
        this.lock = new ReentrantReadWriteLock();
        this.readLock = this.lock.readLock();
        this.writeLock = this.lock.writeLock();
        this.data = new int[1000];
    }

    public void add(int index, int ele) {
        System.out.println(Thread.currentThread().getName() + "写数据: " + ele);
        data[index] = ele;
    }

    public int get(int index) {
        final int datum = data[index];
        System.out.println(Thread.currentThread().getName() + "读数据: " + datum);
        return datum;
    }

    private void write() {
        this.writeLock.lock();
        try {
            for (int i = 0; i < 1000; i++) {
                this.add(i, i);
                this.readLock.lock();
                try {
                    this.get(this.get(i));
                } finally {
                    this.readLock.unlock();
                }
            }
        } finally {
            this.writeLock.unlock();
        }
    }

    private void read() {
        final Random random = new Random();
        this.readLock.lock();
        try {
            while (true) {
                this.get(random.nextInt(1000));
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ReentrantReadWriteLockAnalysis analysis = new ReentrantReadWriteLockAnalysis();
        final Thread writerAndRead = new Thread(() -> {
            analysis.write();
            analysis.read();
        });
        final Thread reader = new Thread(analysis::read);
        writerAndRead.start();
        TimeUnit.SECONDS.sleep(1);
        reader.start();
    }
}
