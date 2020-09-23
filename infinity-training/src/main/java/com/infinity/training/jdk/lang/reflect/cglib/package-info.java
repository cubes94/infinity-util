/**
 * cglib: 字节码生成库--生成和转换java字节码，可以在运行期扩展java类与实现java接口。可在AOP、测试、数据访问框架中生成动态代理对象和拦截对象访问。
 * cglib相比于使用java反射{@link java.lang.reflect.Proxy}的JDK动态代理不仅可以接管接口类的方法，还可以接管普通类的方法。
 * cglib的底层是java字节码操作框架--ASM，来转换字节码并生成新的类。
 * 如果委托类被final修饰，那么它不可被继承，即不可被代理，运行时会抛异常。同样final修饰的方法也不能被代理，执行的时候走的是原对象的方法。
 * cglib创建的动态代理对象性能比JDK创建的动态代理对象的性能高不少，但是cglib在创建代理对象时所花费的时间要长，所以对于单例的对象，用cglib更合适。
 */
package com.infinity.training.jdk.lang.reflect.cglib;