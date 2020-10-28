package com.infinity.training.basis.jdk.util.concurrent.pool;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * ScheduledThreadPoolExecutor可以用来在给定延时后执行异步任务或者周期性执行任务，相对于任务调度的Timer来说，其功能更加强大，Timer只能使用一个后台线程执行任务，而ScheduledThreadPoolExecutor则可以通过构造函数来指定后台线程的个数。
 * <p>
 * 1. ScheduledThreadPoolExecutor继承了ThreadPoolExecutor，也就是说ScheduledThreadPoolExecutor拥有execute()和submit()提交异步任务的基础功能。
 * 2. ScheduledThreadPoolExecutor类实现了ScheduledExecutorService，该接口定义了ScheduledThreadPoolExecutor能够延时执行任务和周期执行任务的功能。
 * 3. ScheduledThreadPoolExecutor也两个重要的内部类：DelayedWorkQueue和ScheduledFutureTask。DelayedWorkQueue实现了BlockingQueue接口，也就是一个阻塞队列，ScheduledFutureTask则是继承了FutureTask类，也表示该类用于返回异步任务的 结果。
 * ---DelayedWorkQueue是基于堆的数据结构，按照时间顺序将每个任务进行排序，将待执行时间越近的任务放在在队列的队头位置，以便于最先进行执行。
 * ---ScheduledFutureTask最主要的功能是根据当前任务是否具有周期性，对异步任务进行进一步封装。如果不是周期性任务（调用schedule方法）则直接通过run()执行，若是周期性任务，则需要在每一次执行完后，重设下一次执行的时间，然后将下一次任务继续放入到阻塞队列中。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 12:26
 */
public class ScheduledThreadPoolExecutorAnalysis implements Analysis {

    private ScheduledThreadPoolExecutor executor;
}
