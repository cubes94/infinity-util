package com.infinity.training.spring.mybatis.plugins;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;

/**
 * 实现InitializingBean的bean在属性注入完毕后会执行afterPropertiesSet方法
 * <p>
 * FactoryBean是一个工厂bean，可以生成某一个类型Bean实例。(可以类似当作动态代理来使用)
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 17:24
 */
public class InfinitySqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean {

    private DataSource dataSource;

    private SqlSessionFactory sqlSessionFactory;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取实际的bean
     *
     * @return 实际的bean
     */
    @Override
    public SqlSessionFactory getObject() {
        if (this.sqlSessionFactory == null) {
            afterPropertiesSet();
        }

        return this.sqlSessionFactory;
    }

    /**
     * 获取实际的bean的类型
     *
     * @return 实际的bean的类型
     */
    @Override
    public Class<? extends SqlSessionFactory> getObjectType() {
        return this.sqlSessionFactory == null ? SqlSessionFactory.class : this.sqlSessionFactory.getClass();
    }

    @Override
    public void afterPropertiesSet() {
        this.sqlSessionFactory = buildSqlSessionFactory();
    }

    private SqlSessionFactory buildSqlSessionFactory() {

        final Configuration targetConfiguration = new Configuration();

        targetConfiguration.setEnvironment(new Environment(InfinitySqlSessionFactoryBean.class.getSimpleName(), new ManagedTransactionFactory(),
                this.dataSource));

        return new SqlSessionFactoryBuilder().build(targetConfiguration);
    }
}
