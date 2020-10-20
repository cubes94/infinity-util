package com.infinity.training.spring.mybatis.plugins.annotation;

import com.infinity.training.spring.mybatis.plugins.mapper.InfinityMapperScannerConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 往spring容器中注册Bean，通过@Import(InfinityMapperScannerRegistrar.class)来使用
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 16:40
 */
public class InfinityMapperScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取注解InfinityMapperScan中的数据
        AnnotationAttributes mapperScanAttrs =
                AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(InfinityMapperScan.class.getName()));
        if (mapperScanAttrs != null) {
            registerBeanDefinitions(mapperScanAttrs, registry, generateBaseBeanName(importingClassMetadata, 0));
        }
    }

    /**
     * 创建名称为beanName、类型为InfinityMapperScannerConfigurer的Bean。
     * 而InfinityMapperScannerConfigurer是后置处理器，则Spring在调用Bean的后置处理器时会执行InfinityMapperScannerConfigurer中的postProcessBeanDefinitionRegistry方法。
     */
    private void registerBeanDefinitions(AnnotationAttributes annoAttrs,
                                         BeanDefinitionRegistry registry, String beanName) {
        // 创建beanClass为InfinityMapperScannerConfigurer的BeanDefinition
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(InfinityMapperScannerConfigurer.class);

        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));
        basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText).collect(Collectors.toList()));

        // 将basePackages属性传到BeanDefinition中
        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));

        // 创建Bean
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }

    /**
     * 自定义bean名称
     */
    private String generateBaseBeanName(AnnotationMetadata importingClassMetaData, int index) {
        return importingClassMetaData.getClassName() + "#" + InfinityMapperScannerRegistrar.class.getSimpleName() + "#" + index;
    }
}
