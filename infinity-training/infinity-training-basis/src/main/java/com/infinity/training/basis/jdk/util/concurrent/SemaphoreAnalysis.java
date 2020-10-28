package com.infinity.training.basis.jdk.util.concurrent;

import com.infinity.training.basis.jdk.Analysis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * {@link Semaphore} 控制资源并发访问
 * <p>
 * Semaphore可以理解为信号量，用于控制资源能够被并发访问的线程数量，以保证多个线程能够合理的使用特定资源。
 * Semaphore就相当于一个许可证，线程需要先通过acquire方法获取该许可证，该线程才能继续往下执行，否则只能在该方法处阻塞等待。
 * 执行完业务功能后，需要通过release()方法将许可证归还，以便其他线程能够获得许可证继续执行。
 * <p>
 * Semaphore可以用于做流量控制，特别是公共资源有限的应用场景，比如数据库连接。假如有多个线程读取数据后，需要将数据保存在数据库中，而可用的最大数据库连接只有10个，这时候就需要使用Semaphore来控制能够并发访问到数据库连接资源的线程个数最多只有10个。
 * 在限制资源使用的应用场景下，Semaphore是特别合适的。
 * <p>
 * 支持控制是否公平，默认非公平性，保证吞吐量。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/28 15:38
 */
public class SemaphoreAnalysis implements Analysis {

    public static void main(String[] args) {
        // 资源数
        int resource = 5;
        final Semaphore semaphore = new Semaphore(resource);
        int taskNum = resource * 3;
        final ExecutorService executorService = Executors.newFixedThreadPool(taskNum);
        for (int i = 0; i < taskNum; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " start");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " get resource");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " finish and release resource");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
