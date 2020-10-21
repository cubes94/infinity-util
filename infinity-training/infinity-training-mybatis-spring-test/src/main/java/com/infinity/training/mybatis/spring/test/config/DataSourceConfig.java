package com.infinity.training.mybatis.spring.test.config;

import com.infinity.training.mybatis.spring.InfinitySqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * 数据库配置
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 16:49
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:mysql://localhost:3306/infinity_training_mybatis_spring?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=GMT%2B8");
        dataSource.setUsername("root");
        dataSource.setPassword("syskey");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        final InfinitySqlSessionFactoryBean sqlSessionFactoryBean = new InfinitySqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
