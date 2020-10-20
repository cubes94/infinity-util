package com.infinity.training.mybatis.spring.analysis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 代码解析(无用处)
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 19:06
 */
public abstract class AbstractApplicationContextAnalysis extends AbstractApplicationContext {

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        // Prepare this context for refreshing.
        prepareRefresh();

        // Tell the subclass to refresh the internal bean factory.
        // 在子类中启动refreshBeanFactory()的地方
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // Prepare the bean factory for use in this context.
        prepareBeanFactory(beanFactory);

        try {
            // Allows post-processing of the bean factory in context subclasses.
            // 设置bean的后置处理
            postProcessBeanFactory(beanFactory);

            // Invoke factory processors registered as beans in the context.
            // 调用bean的后置处理器，这些后置处理器是可以改变Bean的定义
            invokeBeanFactoryPostProcessors(beanFactory);

            // Register bean processors that intercept bean creation.
            // 注册bean的后置处理器，在bean创建过程中调用
            registerBeanPostProcessors(beanFactory);

            // Initialize message source for this context.
            // 对上下文中的消息源进行初始化
            initMessageSource();

            // Initialize event multicaster for this context.
            // 初始化上下文中的事件机制
            initApplicationEventMulticaster();

            // Initialize other special beans in specific context subclasses.
            // 初始化其他特殊的Bean
            onRefresh();

            // Check for listener beans and register them.
            // 检查监听Bean并将这些Bean向容器注册
            registerListeners();

            // Instantiate all remaining (non-lazy-init) singletons.
            // 实例化所有非懒加载的单例
            finishBeanFactoryInitialization(beanFactory);

            // Last step: publish corresponding event.
            // 发布容器时间，结束refresh过程
            finishRefresh();
        } catch (BeansException ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("Exception encountered during context initialization - " +
                        "cancelling refresh attempt: " + ex);
            }

            // Destroy already created singletons to avoid dangling resources.
            // 为防止Bean资源占用，在异常处理中，销毁已经在前面过程中生成的单例
            destroyBeans();

            // Reset 'active' flag.
            // 重置active标志
            cancelRefresh(ex);

            // Propagate exception to caller.
            throw ex;
        } finally {
            // Reset common introspection caches in Spring's core, since we
            // might not ever need metadata for singleton beans anymore...
            resetCommonCaches();
        }
    }

}
