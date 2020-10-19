package com.infinity.training.basis.jdk.lang.classloader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 类加载器
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/13 11:52
 */
public class MyClassLoader extends ClassLoader {

    public MyClassLoader() {
        super();
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * 重写loadClass可以打破双亲委派模型
     *
     * @param name class name
     * @return clazz
     * @throws ClassNotFoundException e
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            // getSystemClassLoader().getParent()即为扩展类加载器，此处只能加载jdk核心类
            final Class<?> clazz = getSystemClassLoader().getParent().loadClass(name);
            if (clazz != null) {
                return clazz;
            }
        } catch (ClassNotFoundException e) {
        }
        // 本项目的其他类由此方法加载
        return this.findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        final String dir = System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes";
        File file = new File(dir + File.separator + name.replace(".", File.separator) + ".class");
        if (file.exists()) {
            try {
                final byte[] bytes = FileUtils.readFileToByteArray(file);
                return this.defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
            }
        }
        throw new ClassNotFoundException();
    }
}
