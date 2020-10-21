package com.infinity.training.mybatis.spring.test;

import com.infinity.training.mybatis.spring.annotation.InfinityMapperScan;
import com.infinity.training.mybatis.spring.test.dao.OrderMapper;
import com.infinity.training.mybatis.spring.test.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.stream.Collectors;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 16:42
 */
@Slf4j
@ComponentScan("com.infinity")
@InfinityMapperScan("com.infinity.**.dao")
public class InfinityMybatisSpringTestApp {

    /**
     * AnnotationConfigApplicationContext构造器会执行下面方法
     *
     * @param args args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InfinityMybatisSpringTestApp.class);
        // 通过上面代码构造AnnotationConfigApplicationContext对象时执行了：

        // 实例化AnnotatedBeanDefinitionReader和ClassPathBeanDefinitionScanner
        // this();

        // 注册Bean (这里注册的是构造参数传入的类以及注解等信息，即InfinityMybatisSpringApp.class)
        // Register one or more component classes to be processed.
        // register(componentClasses);

        // 加载并注册剩余的Bean，见AbstractApplicationContextAnalysis
        // to fully process the new classes
        // refresh();
        log.info("userMapper bean: {}", context.getBean(UserMapper.class));
        log.info("test userMapper: {}", String.join(";", context.getBean(UserMapper.class).listUserName()));
        log.info("orderMapper bean: {}", context.getBean(OrderMapper.class));
        log.info("test orderMapper: {}", context.getBean(OrderMapper.class).countOrder());
    }
}
