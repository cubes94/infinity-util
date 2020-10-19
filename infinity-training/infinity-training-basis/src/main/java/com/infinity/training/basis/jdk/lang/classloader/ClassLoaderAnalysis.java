package com.infinity.training.basis.jdk.lang.classloader;

import com.infinity.training.basis.jdk.Analysis;

/**
 * 类加载器
 * <p>
 * 启动类加载器(Bootstrap ClassLoader): 加载对象是Java核心库，把一些核心的Java类加载进JVM中，这个加载器使用原生代码(C/C++)实现，
 * 并不是继承java.lang.ClassLoader，它是所有其他类加载器的最终父加载器，负责加载<JAVA_HOME>/jre/lib目录下JVM指定的类库。
 * 其实它属于JVM整体的一部分，JVM一启动就将这些指定的类加载到内存中，避免以后过多I/O操作，提高系统的运行效率。启动类加载器无法被Java程序直接使用。
 * <p>
 * 扩展类加载器(Extension ClassLoader): 加载的对象为Java的扩展库，即加载<JAVA_HOME>/jre/lib/ext目录里面的类。这个类由启动类加载器加载，
 * 但因为启动类加载器并非用Java实现，已经脱离了Java体系，所以如果尝试调用扩展类加载器的getParent()方法获取父加载器会得到null。
 * 然而它的父类加载器是启动类加载器。
 * <p>
 * 应用程序加载器(Application ClassLoader): 也叫系统加载器(System ClassLoader)，它负责加载用户类路径(CLASSPATH，即编译后target目录下)指定的类库，
 * 如果程序没有自己定义类加载器，就默认使用应用程序类加载器。它也由启动类加载器加载，但它的父加载器被设置成了扩展类加载器。
 * 如果要使用这个加载器，通过ClassLoader.getSystemClassLoader()获取。
 * <p>
 * <p>
 * 双亲委派
 * 双亲委派模型会在类加载器加载类时首先委托给父类加载器加载，除非父类加载器不能加载才自己加载。这个机制保证了安全性，避免对核心类库进行破坏。
 * 这个模型要求除了顶层的启动类加载器外，其他的类加载器都要有自己的父类加载器。
 * <p>
 * 打破双亲委派模型原则
 * 1. 为了让父类加载器加载的类想要调用子类加载器加载的类，Java引入了一个不太优雅的设计：线程上下文类加载器(Thread Context ClassLoader)。
 * 这个类加载器可以通过java.lang.Thread类的setContextClassLoader方法进行设置。如果创建线程时还未设置，它将会从父线程中继承一个，
 * 如果在应用程序的全局范围内都没有设置过的话，那这个类加载器默认使用应用程序类加载器。
 * 有了线程上下文加载器，JNDI服务可以使用这个线程上下文加载器去加载所需要的SPI代码，也就是父类加载器请求子类加载器去完成类加载的动作，
 * 这种行为实际上就是打通了双亲委派模型的层次结构来逆向使用类加载器，也违背了其一般性原则。但是这也无可奈何，Java中所有涉及SPI加载动作基本都采用这种方式，例如JNDI、JDBC、JCE、JAXB等。
 * 2. 为了实现热插拔、热部署、模块化，即添加一个功能或减去一个功能不用重启，只需要把这模块连同类加载器一起换掉就实现了代码的热替换。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/13 11:51
 */
public class ClassLoaderAnalysis implements Analysis {

    /**
     * 打破双亲委派模型
     *
     * @throws Exception e
     */
    public void myClassLoader() throws Exception {
        String className = "com.infinity.training.basis.example.enterprise.Employee";
        final MyClassLoader myClassLoader1 = new MyClassLoader();
        final Class<?> clazz1 = myClassLoader1.loadClass(className);
        final MyClassLoader myClassLoader2 = new MyClassLoader();
        final Class<?> clazz2 = myClassLoader2.loadClass(className);
        // 由不同类加载器加载的相同类生成的Class不相同
        assert clazz1 != clazz2;
    }
}
