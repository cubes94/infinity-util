package com.infinity.training.basis.jdk.util.concurrent;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * COW vs 读写锁
 * 相同点：
 * 1. 两者都是通过读写分离的思想实现；
 * 2. 读线程间是互不阻塞的。
 * 不同点：
 * 1. 对读线程而言，为了实现数据实时性，在写锁被获取后，读线程会等待或者当读锁被获取后，写线程会等待，从而解决"脏读"等问题。也就是说如果使用读写锁依然会出现读线程阻塞等待的情况。
 * 2. 而COW则完全放开了牺牲数据实时性而保证数据最终一致性，即读线程对数据的更新是延时感知的，因此读线程不会存在等待的情况。
 * <p>
 * COW通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
 * 对CopyOnWrite容器进行并发的读的时候，不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，延时更新的策略是通过在写的时候针对的是不同的数据容器来实现的，放弃数据实时性达到数据的最终一致性。
 * <p>
 * 为什么需要复制呢？ 如果将array 数组设定为volatile的， 对volatile变量写happens-before读，读线程不是能够感知到volatile变量的变化。
 * 原因是，这里volatile的修饰的仅仅只是数组引用，数组中的元素的修改是不能保证可见性的。因此COW采用的是新旧两个数据容器，将数组引用指向新的数组。
 * <p>
 * COW的缺点
 * 1. 内存占用问题：因为CopyOnWrite的写时复制机制，所以在进行写操作的时候，内存里会同时驻扎两个对象的内存，旧的对象和新写入的对象
 * （注意:在复制的时候只是复制容器里的引用，只是在写的时候会创建新对 象添加到新容器里，而旧容器的对象还在使用，所以有两份对象内存）。
 * 如果这些对象占用的内存比较大，比 如说200M左右，那么再写入100M数据进去，内存就会占用300M，那么这个时候很有可能造成频繁的minor GC和major GC。
 * 2. 数据一致性问题：CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 02:46
 */
public class CopyOnWriteArrayListAnalysis implements Analysis {

    private CopyOnWriteArrayList copyOnWriteArrayList;
}
