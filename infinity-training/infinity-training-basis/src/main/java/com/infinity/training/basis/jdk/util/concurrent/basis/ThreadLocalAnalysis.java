package com.infinity.training.basis.jdk.util.concurrent.basis;

import com.infinity.training.basis.jdk.Analysis;

/**
 * 从ThreadLocal这个类名可以顾名思义的进行理解，表示线程的"本地变量"，即每个线程都拥有该变量副本，达到人手一份的效果，各用各的这样就可以避免共享资源的竞争。
 * <p>
 * 1. 每次使用完ThreadLocal，都调用它的remove()方法，清除数据。
 * 2. 在使用线程池的情况下，没有及时清理ThreadLocal，不仅是内存泄漏的问题，更严重的是可能导致业务逻辑出现问题。所以，使用ThreadLocal就跟加锁完要解锁一样，用完就清理。
 * <p>
 * 存在了这样一条引用链：threadRef->currentThread->threadLocalMap->entry->valueRef->valueMemory,导致在垃圾回收的时候进行可达性分析的时候,value可达从而不会被回收掉，但是该value永远不能被访问到，这样就存在了内存泄漏。
 * 当然，如果线程执行结束后，threadLocal，threadRef会断掉，因此threadLocal,threadLocalMap，entry都会被回收掉。
 * 可是，在实际使用中我们都是会用线程池去维护我们的线程，比如在Executors.newFixedThreadPool()时创建线程的时候，为了复用线程是不会结束的。
 * 在threadLocal的生命周期里，针对threadLocal存在的内存泄漏的问题，都会通过expungeStaleEntry，cleanSomeSlots,replaceStaleEntry这三个方法清理掉key为null的脏entry。
 * <p>
 * 为什么使用弱引用
 * 假设threadLocal使用的是强引用，在业务代码中执行threadLocalInstance==null操作，以清理掉threadLocal实例的目的，但是因为threadLocalMap的Entry强引用threadLocal，因此在gc的时候进行可达性分析，threadLocal依然可达，对threadLocal并不会进行垃圾回收，这样就无法真正达到业务逻辑的目的，出现逻辑错误。
 * 假设Entry弱引用threadLocal，尽管会出现内存泄漏的问题，但是在threadLocal的生命周期里（set,getEntry,remove）里，都会针对key为null的脏entry进行处理。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 11:40
 */
public class ThreadLocalAnalysis implements Analysis {

    private ThreadLocal threadLocal;
}
