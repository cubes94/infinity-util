package com.infinity.training.mybatis.spring.boot.starter.test;

import com.infinity.training.mybatis.spring.boot.starter.test.dao.OrderMapper;
import com.infinity.training.mybatis.spring.boot.starter.test.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/22 02:10
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfinityMybatisSpringBootTestApp.class)
public class InfinityMybatisSpringBootTestAppTest implements ApplicationContextAware {

    @Autowired
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void testMybatisSpringBootStarter() {
        log.info("userMapper bean: {}", context.getBean(UserMapper.class));
        log.info("test userMapper: {}", String.join(";", context.getBean(UserMapper.class).listUserName()));
        log.info("orderMapper bean: {}", context.getBean(OrderMapper.class));
        log.info("test orderMapper: {}", context.getBean(OrderMapper.class).countOrder());
    }
}
