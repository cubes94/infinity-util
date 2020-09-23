package com.infinity.training.jdk.lang.reflect;

import com.infinity.training.example.enterprise.TaskService;
import com.infinity.training.example.enterprise.impl.TaskServiceImpl;
import com.infinity.training.jdk.Analysis;
import com.infinity.training.jdk.lang.reflect.cglib.MyMethodInterceptor;
import net.sf.cglib.core.DebuggingClassWriter;

/**
 * 动态代理
 * <p>
 * 适用场景：AOP、权限控制、服务监控、缓存、日志、限流、事务等。
 * <p>
 * 如果目标对象没有实现接口，必须采用cglib实现AOP；
 * 如果目标对象实现了接口，默认情况下Spring会采用JDK的动态代理实现AOP，也可以强制使用cglib实现AOP；
 * <p>
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/22 19:53
 */
public class DynamicProxyAnalysis implements Analysis {

    /**
     * jdk反射实现的动态代理
     * <p>
     * 此处在jvm内存中生成一个class--$Proxy0.class，继承自Proxy，实现了TaskService接口。
     * 在调用具体方法前调用InvocationHandler来处理，使用invoke方法进行面向切面的处理。
     */
    public void jdkReflect() {
        // 把动态代理生成的class文件输出到磁盘上(位于当前工程目录下)(main方法下有效)。
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        final TaskService taskService = new MyInvocationHandler<TaskService>().getProxy(new TaskServiceImpl());
        taskService.randomAssign();
    }

    /**
     * cglib代理
     * <p>
     * 此处生成了三个class:
     * TaskServiceImpl$$EnhancerByCGLIB$$4c18834$$FastClassByCGLIB$$4be18ad.class 代理类的FastClass类
     * TaskServiceImpl$$EnhancerByCGLIB$$4c18834.class  代理类(继承自被代理类)
     * TaskServiceImpl$$FastClassByCGLIB$$a7ab0f70.class 被代理类的FastClass类
     * <p>
     * FastClass类实现了字节码和执行方法的索引关系，主要有invoke和getIndex两个抽象方法：
     * net.sf.cglib.reflect.FastClass#getIndex(net.sf.cglib.core.Signature) 通过 方法签名字符串 获取方法的索引
     * net.sf.cglib.reflect.FastClass#invoke(int, java.lang.Object, java.lang.Object[]) 通过对象、方法索引、方法入参调用方法
     * cglib代理以这种方式避免了对被代理对象的反射调用，这也是cglib性能优于JDK代理的地方。
     * <p>
     * 利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。
     * 在调用具体方法前调用MethodInterceptor来处理，使用intercept方法进行面向切面的处理。
     */
    public void cglib() {
        // 把动态代理生成的class文件输出到磁盘上
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:/util-train/cglib");
        final TaskService taskService = new MyMethodInterceptor<TaskService>().getProxy(new TaskServiceImpl());
        taskService.randomAssign();
    }
}
