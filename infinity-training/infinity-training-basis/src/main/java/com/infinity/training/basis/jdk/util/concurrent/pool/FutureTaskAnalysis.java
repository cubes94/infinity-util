package com.infinity.training.basis.jdk.util.concurrent.pool;

import com.infinity.training.basis.jdk.Analysis;

/**
 * 在Executors框架体系中，FutureTask用来表示可获取结果的异步任务。FutureTask实现了Future接口，FutureTask提供了启动和取消异步任务，查询异步任务是否计算结束以及获取最终的异步任务的结果的一些常用的方法。
 *
 * 根据FutureTask.run()方法的执行的时机，FutureTask分为了3种状态：
 * 1. 未启动: 当创建一个FutureTask，还没有执行FutureTask.run()方法之前，FutureTask处于未启动状态。
 * 2. 已启动: FutureTask.run()方法被执行的过程中，FutureTask处于已启动状态。
 * 3. 已完成: FutureTask.run()方法执行结束，或者调用FutureTask.cancel(...)方法取消任务，或者在执行任务期间抛出异常，这些情况都称之为FutureTask的已完成状态。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 12:36
 */
public class FutureTaskAnalysis implements Analysis {
}
