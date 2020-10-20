package com.infinity.training.mybatis.spring.plugins.mapper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 实现ApplicationContextAware接口后Spring会自动调用setApplicationContext方法
 * <p>
 * BeanFactoryPostProcessor->BeanDefinitionRegistryPostProcessor，Bean的后置处理器，后者继承自前者。
 * {@link BeanFactoryPostProcessor#postProcessBeanFactory} 此方法可以用来修改Bean的定义
 * {@link BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry} 此方法可以注册更多的Bean
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 19:20
 */
public class InfinityMapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private String basePackage;

    private ApplicationContext applicationContext;

    /**
     * 该方法用来注册更多的bean到spring容器中
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.basePackage = Optional.ofNullable(this.basePackage).map(this.applicationContext.getEnvironment()::resolvePlaceholders).orElse(null);

        // 对扫描路径进行调整，开始扫描路径下的mapper
        InfinityClassPathMapperScanner scanner = new InfinityClassPathMapperScanner(registry);
        // 过滤条件
        scanner.registerFilters();
        // 扫描
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    /**
     * 该方法的实现中，主要用来对bean定义做一些改变
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // left intentionally blank
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
