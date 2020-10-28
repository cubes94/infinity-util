package com.infinity.training.basis.jdk.util.concurrent.basis;

/**
 * 写final域重排序规则
 * 写final域的重排序规则禁止对final域的写重排序到构造函数之外，这个规则的实现主要包含了两个方面：
 * 1. JMM禁止编译器把final域的写重排序到构造函数之外；
 * 2. 编译器会在final域写之后，构造函数return之前，插入一个storestore屏障。这个屏障可以禁止处理器把final域的写重排序到构造函数之外。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 22:51
 */
public class Final {
}
