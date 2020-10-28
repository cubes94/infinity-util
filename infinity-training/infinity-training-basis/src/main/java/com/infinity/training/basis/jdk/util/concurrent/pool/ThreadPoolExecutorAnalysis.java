package com.infinity.training.basis.jdk.util.concurrent.pool;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.*;

/**
 * 核心：核心线程池corePoolSize，阻塞队列workQueue和线程池maximumPoolSize
 *
 * 1. 如果当前运行的线程少于corePoolSize，则会创建新的线程来执行新的任务；
 * 2. 如果运行的线程个数等于或者大于corePoolSize，则会将提交的任务存放到阻塞队列workQueue中；
 * 3. 如果当前workQueue队列已满的话，则会创建新的线程来执行任务；
 * 4. 如果线程个数已经超过了maximumPoolSize，则会使用饱和策略RejectedExecutionHandler来进行处理。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 12:17
 */
public class ThreadPoolExecutorAnalysis implements Analysis {

    private ThreadPoolExecutor executor;

    /**
     * 通过参数最多的构造方法来理解创建线程池有哪些需要配置的参数
     *
     * @param corePoolSize    表示核心线程池的大小。当提交一个任务时，如果当前核心线程池的线程个数没有达到corePoolSize，则会创建新的线程来执行所提交的任务，即使当前核心线程池有空闲的线程。如果当前核心线程池的线程个数已经达到了corePoolSize，则不再重新创建线程。
     * @param maximumPoolSize 表示线程池能创建线程的最大个数。如果当阻塞队列已满时，并且当前线程池线程个数没有超过maximumPoolSize的话，就会创建新的线程来执行任务。
     * @param keepAliveTime   空闲线程存活时间。如果当前线程池的线程个数已经超过了corePoolSize，并且线程空闲时间超过了keepAliveTime的话，就会将这些空闲线程销毁，这样可以尽可能降低系统资源消耗。
     * @param unit            时间单位。为keepAliveTime指定时间单位。
     * @param workQueue       阻塞队列。用于保存任务的阻塞队列。
     * @param threadFactory   创建线程的工程类。可以通过指定线程工厂为每个创建出来的线程设置更有意义的名字，如果出现并发问题，也方便查找问题原因。
     * @param handler         饱和策略。当线程池的阻塞队列已满和指定的线程都已经开启，说明当前线程池已经处于饱和状态了，那么就需要采用一种策略来处理这种情况。采用的策略有这几种：
     *                        AbortPolicy： 直接拒绝所提交的任务，并抛出RejectedExecutionException异常；
     *                        CallerRunsPolicy：只用调用者所在的线程来执行任务；
     *                        DiscardPolicy：不处理直接丢弃掉任务；
     *                        DiscardOldestPolicy：丢弃掉阻塞队列中存放时间最久的任务，执行当前任务
     */
    public ThreadPoolExecutorAnalysis(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                      BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public ThreadPoolExecutorAnalysis() {
    }
}
