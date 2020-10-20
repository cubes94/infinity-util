package com.infinity.training.spring.mybatis.plugins.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Import}注解只能用在类上，通过快速导入的方式将实例加入spring的IOC容器中，可用于导入第三方包，有以下三种导入方式：
 * 1. 参数直接定义为class数组；
 * 2. {@link org.springframework.context.annotation.ImportSelector}方式
 * 3. {@link org.springframework.context.annotation.ImportBeanDefinitionRegistrar}方式
 * 方式1和方式2都是以全限定名的方式注册，方式3可自定义。
 * <p>
 * 以下采用的是方式3：{@link InfinityMapperScannerRegistrar}
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 16:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(InfinityMapperScannerRegistrar.class)
public @interface InfinityMapperScan {

    String[] value() default {};

    String[] basePackages() default {};
}
