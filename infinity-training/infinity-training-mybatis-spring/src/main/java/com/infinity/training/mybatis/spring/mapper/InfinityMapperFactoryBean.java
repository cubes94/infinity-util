package com.infinity.training.mybatis.spring.mapper;


import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.dao.support.DaoSupport;

/**
 * DaoSupport实现了InitializingBean接口，在属性注入完毕后会执行afterPropertiesSet方法(包括checkDaoConfig方法)。
 * <p>
 * FactoryBean是一个工厂bean，可以生成某一个类型Bean实例。(可以类似当作动态代理来使用)。
 * <p>
 * spring-mybatis中实现了SqlSessionTemplate类，对mybatis的SqlSession进行了包装，添加了事务等信息。此处简化版本，去掉了这个类。
 * 此类(包括SqlSessionTemplate类，非此处简化版)是和mybatis沟通的桥梁。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 20:21
 */
public class InfinityMapperFactoryBean<T> extends DaoSupport implements FactoryBean<T> {

    private Class<T> mapperInterface;

    private SqlSessionFactory sqlSessionFactory;

    public InfinityMapperFactoryBean() {

    }

    public InfinityMapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        return getSqlSession().getMapper(this.mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return this.mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        Configuration configuration = getSqlSession().getConfiguration();
        if (!configuration.hasMapper(this.mapperInterface)) {
            try {
                configuration.addMapper(this.mapperInterface);
            } catch (Exception e) {
                logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
                throw new IllegalArgumentException(e);
            } finally {
                ErrorContext.instance().reset();
            }
        }
    }

    private SqlSession getSqlSession() {
        return this.sqlSessionFactory.openSession();
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
