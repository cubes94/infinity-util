package com.infinity.training.basis.jdk.util.concurrent;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.*;

/**
 * 阻塞队列（BlockingQueue）被广泛使用在"生产者-消费者"问题中，其原因是BlockingQueue提供了可阻塞的插入和移除的方法。
 * 当队列容器已满，生产者线程会被阻塞，直到队列未满；当队列容器为空时，消费者线程会被阻塞，直至队列非空时为止。
 * <p>
 * 重要方法
 * 1. put：当阻塞队列容量已经满时，往阻塞队列插入数据的线程会被阻塞，直至阻塞队列已经有空余的容量可供使用；
 * 2. take()：当阻塞队列为空时，获取队头数据的线程会被阻塞；
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 11:58
 */
public class BlockingQueueAnalysis implements Analysis {

    private BlockingQueue<Integer> blockingQueue;

    private int capacity = 100;

    public BlockingQueueAnalysis() {
        // ArrayBlockingQueue是由数组实现的有界阻塞队列。该队列命令元素FIFO（先进先出）。
        // 默认非公平，提高吞吐量
        this.blockingQueue = new ArrayBlockingQueue<>(capacity);

        // LinkedBlockingQueue是用链表实现的有界阻塞队列，同样满足FIFO的特性，与ArrayBlockingQueue相比起来具有更高的吞吐量，
        // 为了防止LinkedBlockingQueue容量迅速增，损耗大量内存。通常在创建LinkedBlockingQueue对象时，会指定其大小，如果未指定，容量等于Integer.MAX_VALUE
        this.blockingQueue = new LinkedBlockingQueue<>(capacity);

        // ArrayBlockingQueue与LinkedBlockingQueue的比较
        // 相同点：ArrayBlockingQueue和LinkedBlockingQueue都是通过condition通知机制来实现可阻塞式插入和删除元素，并满足线程安全的特性；
        // 不同点：1. ArrayBlockingQueue底层是采用的数组进行实现，而LinkedBlockingQueue则是采用链表数据结构；
        // 不同点：2. ArrayBlockingQueue插入和删除数据，只采用了一个lock，而LinkedBlockingQueue则是在插入和删除分别采用了putLock和takeLock，这样可以降低线程由于线程无法获取到lock而进入WAITING状态的可能性，从而提高了线程并发执行的效率。

        // ==================================================================

        // PriorityBlockingQueue是一个支持优先级的无界阻塞队列。
        this.blockingQueue = new PriorityBlockingQueue<>(capacity);

        // SynchronousQueue每个插入操作必须等待另一个线程进行相应的删除操作，因此，SynchronousQueue实际上没有存储任何数据元素，
        // 因为只有线程在删除数据时，其他线程才能插入数据，同样的，如果当前有线程在插入数据时，线程才能删除数据。
        this.blockingQueue = new SynchronousQueue<>();

        // LinkedTransferQueue是一个由链表数据结构构成的无界阻塞队列，该队列实现了TransferQueue接口
        this.blockingQueue = new LinkedTransferQueue<>();

        // LinkedBlockingDeque是基于链表数据结构的有界阻塞双端队列，如果在创建对象时为指定大小时，其默认大小为Integer.MAX_VALUE。
        this.blockingQueue = new LinkedBlockingDeque<>(capacity);

        // DelayQueue是一个存放实现Delayed接口的数据的无界阻塞队列，只有当数据对象的延时时间达到时才能插入到队列进行存储。
        // 如果当前所有的数据都还没有达到创建时所指定的延时期，则队列没有队头，并且线程通过poll等方法获取数据元素则返回null。
        // 所谓数据延时期满时，则是通过Delayed接口的getDelay(TimeUnit.NANOSECONDS)来进行判定，如果该方法返回的是小于等于0则说明该数据元素的延时期已满。
        BlockingQueue<Delayed> blockingQueue = new DelayQueue<>();
    }
}
