/**
 * 实现简单版spring-mybatis，类的执行顺序如下：
 * 1. 启动{@link com.infinity.training.mybatis.spring.InfinityMybatisSpringApp#main}。
 * <p>
 * 2. 通过InfinityMybatisSpringApp上的注解{@link com.infinity.training.mybatis.spring.plugins.annotation.InfinityMapperScan},
 * 该注解上存在{@link org.springframework.context.annotation.Import}注解，通过Import注解可以将实例加入spring的IOC容器中，可用于导入第三方包。
 * 此处注解内属性为{@link com.infinity.training.mybatis.spring.plugins.annotation.InfinityMapperScannerRegistrar}。
 * <p>
 * 3. InfinityMapperScannerRegistrar执行registerBeanDefinitions方法，
 * 创建类型为{@link com.infinity.training.mybatis.spring.plugins.mapper.InfinityMapperScannerConfigurer}的Bean，
 * 同时将注解InfinityMapperScan上的元数据取出，放到该Bean的成员变量中。
 * <p>
 * 4. 由于InfinityMapperScannerConfigurer实现了BeanDefinitionRegistryPostProcessor接口，所以InfinityMapperScannerConfigurer作为后置处理器，在Spring调用后置处理器时，
 * 该Bean上的postProcessBeanDefinitionRegistry和postProcessBeanFactory方法可以被执行，前者用来注册更多的bean到spring容器中，后者主要用来对bean定义做一些改变，我们这里实现前一个方法即可。
 * 创建{@link com.infinity.training.mybatis.spring.plugins.mapper.InfinityClassPathMapperScanner}类扫描器，同时自定义过滤条件，对mapper进行扫描。
 * <p>
 * 5. InfinityClassPathMapperScanner扫描器将过滤后符合条件的BeanDefinition取出，设置其beanClass为{@link com.infinity.training.mybatis.spring.plugins.mapper.InfinityMapperFactoryBean}，
 * 并将原始mapper接口类型作为构造器参数传入，最后设置按类型自动装配。
 * <p>
 * 6. 在实例化InfinityMapperFactoryBean时，会取出SqlSession并将mapper接口类型传入到配置中交给mybatis。至此，搭建了到mybatis的桥梁，同时也将mapper创建了Bean注册到Spring IOC中。
 */
package com.infinity.training.mybatis.spring;