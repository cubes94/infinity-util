package com.infinity.training.basis.jdk.util.concurrent;

import com.infinity.training.basis.jdk.AnalysisTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe类使Java拥有了像C语言的指针一样操作内存空间的能力
 * <p>
 * （1）初始化操作
 * （2）操作对象属性
 * （3）操作数组元素
 * （4）线程挂起和回复
 * （5）CAS机制
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/12 16:12
 */
@Slf4j
public class UnsafeTest implements AnalysisTest {

    /**
     * unsafe实例
     */
    private static Unsafe U;

    private int num;

    /**
     * num属性的偏移量
     */
    private static long NUM_OFFSET;

    /**
     * 测试CAS，在两个线程中保证num递增且不重复
     */
    @Test
    public void testCas() {
        Runnable runnable = () -> {
            while (true) {
                final boolean b = U.compareAndSwapInt(this, NUM_OFFSET, num, num + 1);
                System.out.println("num: " + U.getIntVolatile(this, NUM_OFFSET) + ", success: " + b);
                this.sleep(500);
            }
        };
        new Thread(runnable).start();
        this.sleep(200);
        new Thread(runnable).start();
        this.sleep(10000);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    static {
        // 获取Unsafe实例，下面代码会报错，如果类加载器是Bootstrap(启动类)类加载器才可以获取到实例，此处是App类加载器
//        final Unsafe unsafe = Unsafe.getUnsafe();
        // 可以通过反射获取到Unsafe实例
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            U = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            log.error("", e);
        }
        // num属性的偏移量
        try {
            NUM_OFFSET = U.objectFieldOffset(UnsafeTest.class.getDeclaredField("num"));
        } catch (NoSuchFieldException e) {
            log.error("", e);
        }
    }
}
