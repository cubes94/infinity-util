package com.infinity.training.jdk.lang.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理-拦截方法逻辑类
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/23 12:55
 */
@Slf4j
public class MyInvocationHandler<T> implements InvocationHandler {

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
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("invoke from jdk proxy, proxy: {}, method: {}, args: {}", proxy, method, args);
        return method.invoke(target, args);
    }
}
