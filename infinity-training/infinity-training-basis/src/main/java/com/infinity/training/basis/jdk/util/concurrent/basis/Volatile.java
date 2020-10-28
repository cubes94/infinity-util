package com.infinity.training.basis.jdk.util.concurrent.basis;

/**
 * 1. Lock前缀的指令会引起处理器缓存写回内存。
 * 2. 一个处理器的缓存回写到内存会导致其他处理器的缓存失效。
 * 3. 当处理器发现本地缓存失效后，就会从内存中重读该变量数据，即可以获取当前最新值。(缓存一致性协议: 在多处理器下，为了保证各个处理器的缓存是一致的，就会实现缓存一致性协议，每个处理器通过嗅探在总线上传播的数据来检查自己缓存的值是不是过期了)
 * <p>
 * 为了性能优化，JMM在不改变正确语义的前提下，会允许编译器和处理器对指令序列进行重排序，那如果想阻止重排序要怎么办了？答案是可以添加内存屏障。
 * 1. 在每个volatile写操作的前面插入一个StoreStore屏障；
 * 2. 在每个volatile写操作的后面插入一个StoreLoad屏障；
 * 3. 在每个volatile读操作的后面插入一个LoadLoad屏障；
 * 4. 在每个volatile读操作的后面插入一个LoadStore屏障。
 * 需要注意的是：volatile写是在前面和后面分别插入内存屏障，而volatile读操作是在后面插入两个内存屏障
 * StoreStore屏障：禁止上面的普通写和下面的volatile写重排序；
 * StoreLoad屏障：防止上面的volatile写与下面可能有的volatile读/写重排序
 * LoadLoad屏障：禁止下面所有的普通读操作和上面的volatile读重排序
 * LoadStore屏障：禁止下面所有的普通写操作和上面的volatile读重排序
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/27 22:33
 */
public class Volatile {
}
