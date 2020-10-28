package com.infinity.training.basis.jdk.util.concurrent;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 利用了锁分段的思想提高了并发度。
 * <p>
 * JDK6,7中的ConcurrentHashmap主要使用Segment来实现减小锁粒度，分割成若干个Segment，在put的时候需要锁住Segment，get时候不加锁，
 * 使用volatile来保证可见性，当要统计全局时（比如size），首先会尝试多次计算modcount来确定，这几次尝试中，是否有其他线程进行了修改操作，
 * 如果没有，则直接返回size。如果有，则需要依次锁住所有的Segment来计算。
 * <p>
 * 1.8之前put定位节点时要先定位到具体的segment，然后再在segment中定位到具体的桶。
 * 而在1.8的时候摒弃了segment臃肿的设计，直接针对的是Node[] tale数组中的每一个桶，进一步减小了锁粒度。并且防止拉链过长导致性能下降，当链表长度大于8的时候采用红黑树的设计。
 * <p>
 * 主要设计上的变化有以下几点:
 * 不采用segment而采用node，锁住node来实现减小锁粒度。
 * 设计了MOVED状态 当resize的中过程中 线程2还在put数据，线程2会帮助resize。
 * 使用3个CAS操作来确保node的一些操作的原子性，这种方式代替了锁。
 * sizeCtl的不同值来代表不同含义，起到了控制的作用。
 * 采用synchronized而不是ReentrantLock
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 01:59
 */
public class ConcurrentHashMapAnalysis implements Analysis {

    private ConcurrentHashMap concurrentHashMap;
}
