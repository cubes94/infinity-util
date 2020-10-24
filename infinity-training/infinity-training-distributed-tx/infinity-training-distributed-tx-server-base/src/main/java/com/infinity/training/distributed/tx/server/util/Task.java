package com.infinity.training.distributed.tx.server.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 17:05
 */
@Slf4j
public class Task {

    private final String taskId;

    private final Lock lock;

    private final Condition condition;

    public Task() {
        this.taskId = UUID.randomUUID().toString();
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
    }

    public void waitTask() {
        log.info("线程阻塞，taskId: {}", taskId);
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalTask() {
        log.info("线程唤醒，taskId: {}", taskId);
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
