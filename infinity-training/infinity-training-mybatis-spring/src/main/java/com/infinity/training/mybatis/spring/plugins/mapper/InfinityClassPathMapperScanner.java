package com.infinity.training.mybatis.spring.plugins.mapper;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * {@link ClassPathBeanDefinitionScanner} 类扫描器，将符合过滤条件的类注册到IOC容器中
 * <p>
 * 此处由于扫描MyBatis的Mapper，需要自定义过滤器规则。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 20:04
 */
public class InfinityClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    public InfinityClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // 调用ClassPathBeanDefinitionScanner的扫描方法，该方法中调用了isCandidateComponent和checkCandidate两个方法，需要重写判断规则。
        final Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (!beanDefinitions.isEmpty()) {
            processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        // BeanDefinition是Spring中Bean的建模对象，即把一个Bean实例化出来的模型对象，可以描述Bean的scope、lazy、属性、方法等等信息。
        // BeanDefinitionHolder即是对BeanDefinition的持有，同时持有BeanDefinition的名称和别名。
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            logger.debug("Creating MapperFactoryBean with name '" + holder.getBeanName() + "' and '" + beanClassName
                    + "' mapperInterface");

            // 自动装配时，调用有参构造器，入参为Mapper接口类型
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            // 这个Mapper对应的接口是这个Bean的原始类型，这里改写成了InfinityMapperFactoryBean类
            // 需要添加到IOC容器中mapper对象的类型
            definition.setBeanClass(InfinityMapperFactoryBean.class);

            // 按类型自动装配，这里会调用setSqlSessionFactory方法注入sqlSessionFactory
            logger.debug("Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName() + "'.");
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }

    /**
     * 过滤条件
     */
    public void registerFilters() {
        // default include filter that accepts all classes
        addIncludeFilter((metadataReader, metadataReaderFactory) -> true);

        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    /**
     * 是否可以作为一个候选者
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    /**
     * 判断重复
     */
    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            logger.warn("Skipping MapperFactoryBean with name '" + beanName + "' and '"
                    + beanDefinition.getBeanClassName() + "' mapperInterface" + ". Bean already defined with the same name!");
            return false;
        }
    }
}
