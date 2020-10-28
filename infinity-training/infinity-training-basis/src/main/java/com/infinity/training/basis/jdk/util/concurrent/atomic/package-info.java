/**
 * 1. 什么是CAS
 * 使用锁时，线程获取锁是一种悲观锁策略，即假设每一次执行临界区代码都会产生冲突，所以当前线程获取到锁的时候同时也会阻塞其他线程获取该锁。
 * 而CAS操作（又称为无锁操作）是一种乐观锁策略，它假设所有线程访问共享资源的时候不会出现冲突，既然不会出现冲突自然而然就不会阻塞其他线程的操作。
 *
 * 2. CAS的问题
 * ABA问题，解决方案可以沿袭数据库中常用的乐观锁方式，添加一个版本号可以解决。
 * 自旋时间过长。使用CAS时非阻塞同步，也就是说不会将线程挂起，会自旋（无非就是一个死循环）进行下一次尝试，如果这里自旋时间过长对性能是很大的消耗。如果JVM能支持处理器提供的pause指令，那么在效率上会有一定的提升。
 */
package com.infinity.training.basis.jdk.util.concurrent.atomic;