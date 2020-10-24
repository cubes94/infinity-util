package com.infinity.training.distributed.tx.server.aspect;

import com.infinity.training.distributed.tx.server.connection.InfinityConnection;
import com.infinity.training.distributed.tx.server.transactional.InfinityTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * DataSource切面
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 16:33
 */
@Slf4j
@Aspect
@Component
public class InfinityDataSourceAspect {

    @Autowired
    private InfinityTransactionManager transactionManager;

    /**
     * 定义切面，如果需要分布式事务，
     */
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint point) throws Throwable {
        log.info("=================DataSource切面====================");
        if (transactionManager.getCurrent() != null) {
            Connection connection = ((Connection) point.proceed());
            return new InfinityConnection(connection, transactionManager.getCurrent());
        } else {
            return (Connection) point.proceed();
        }
    }

}
