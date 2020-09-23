package com.infinity.training.jdk.lang.reflect.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib代理-拦截方法逻辑类
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/22 22:03
 */
@Slf4j
public class MyMethodInterceptor<T> implements MethodInterceptor {

    /**
     * 被代理对象
     */
    private T target;

    /**
     * 获取代理对象
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public T getProxy(T target) {
        this.target = target;
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    /**
     * 拦截方法
     *
     * @param obj         代理后的子类
     * @param method      调用方法
     * @param args        方法入参
     * @param methodProxy 代理对象
     * @return 返回
     * @throws Throwable e
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("intercept from cglib, obj: {}, method: {}, args: {}, proxy: {}", obj, method, args, methodProxy);

        // invoke方法传入的是被代理对象，invokeSuper方法传入的是代理对象。
        // 如果method存在调用该类的另一个方法，invokeSuper执行时，另一个方法也会走一遍拦截方法。

        // 调用fci.f1.invoke(fci.i1, obj, args)
        // fci.f1 --被代理类的FastClass类
        // fci.i1 --方法索引
//        return methodProxy.invoke(target, args);

        // 调用fci.f2.invoke(fci.i2, obj, args)
        // fci.f2 --代理类的FastClass类
        // fci.i2 --方法索引(以被代理对象的randomAssign方法举例，代理对象存在randomAssign和CGLIB$randomAssign$0两个方法，其中randomAssign是增强后的方法，CGLIB$randomAssign$0则只调用了被代理对象的randomAssign方法，此处fci.i2指的是CGLIB$randomAssign$0方法)
        return methodProxy.invokeSuper(obj, args);
    }
}
