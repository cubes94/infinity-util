package com.infinity.training.mybatis.spring.boot.autoconfigure;

import com.infinity.training.mybatis.spring.InfinitySqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/21 20:16
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, InfinitySqlSessionFactoryBean.class})
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
//@ConditionalOnBean(MybatisAutoConfigureMarker.class)    // 扩展：用作动态插拔
public class InfinityMybatisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
        InfinitySqlSessionFactoryBean sqlSessionFactoryBean = new InfinitySqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }
}
