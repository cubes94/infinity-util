package com.infinity.training.basis.jdk.util.concurrent.atomic;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.atomic.*;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 14:43
 */
public class AtomicAnalysis implements Analysis {

    // ========================== 原子更新基本类型 ===================================
    // 以原子更新的方式更新boolean
    private AtomicBoolean atomicBoolean;

    // 以原子更新的方式更新Integer
    private AtomicInteger atomicInteger;

    // 以原子更新的方式更新Long
    private AtomicLong atomicLong;

    // ========================== 原子更新数组类型 ===================================
    // 原子更新整型数组中的元素
    private AtomicIntegerArray atomicIntegerArray;

    // 原子更新长整型数组中的元素
    private AtomicLongArray atomicLongArray;

    // 原子更新引用类型数组中的元素
    private AtomicReferenceArray<Object> atomicReferenceArray;

    // ========================== 原子更新引用类型 ===================================
    // 原子更新引用类型
    private AtomicReference<Object> atomicReference;

    // 原子更新引用类型里的字段
    private AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;

    // 原子更新带有标记位的引用类型
    private AtomicMarkableReference atomicMarkableReference;

    // ========================== 原子更新字段类型 ===================================
    // 原子更新整型字段类
    private AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;

    // 原子更新长整型字段类
    private AtomicLongFieldUpdater atomicLongFieldUpdater;

    // 原子更新引用类型，这种更新方式会带有版本号。而为什么在更新的时候会带有版本号，是为了解决CAS的ABA问题
    private AtomicStampedReference atomicStampedReference;
}
