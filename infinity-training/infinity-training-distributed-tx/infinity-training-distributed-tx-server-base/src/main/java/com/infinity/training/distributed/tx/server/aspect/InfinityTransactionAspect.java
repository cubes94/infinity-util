package com.infinity.training.distributed.tx.server.aspect;

import com.alibaba.fastjson.JSON;
import com.infinity.training.distributed.tx.server.annotation.InfinityTransactional;
import com.infinity.training.distributed.tx.server.transactional.InfinityTransaction;
import com.infinity.training.distributed.tx.server.transactional.InfinityTransactionManager;
import com.infinity.training.distributed.tx.server.transactional.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 18:04
 */
@Slf4j
@Aspect
@Component
public class InfinityTransactionAspect implements Ordered {

    @Autowired
    private InfinityTransactionManager transactionManager;

    @Around("@annotation(com.infinity.training.distributed.tx.server.annotation.InfinityTransactional)")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        log.info("=================Transaction切面====================");
        // 打印出这个注解所对应的方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        final InfinityTransactional annotation = method.getAnnotation(InfinityTransactional.class);

        String groupId;
        if (annotation.isStart()) {
            groupId = transactionManager.createTransactionGroup();
        } else {
            groupId = transactionManager.getCurrentGroupId();
        }
        log.info("annotation isStart: {}, groupId: {}", annotation.isStart(), groupId);

        final InfinityTransaction transaction = transactionManager.createTransaction(groupId);
        try {
            // spring会开启mysql事务
            log.info("业务代码开始执行，当前事务: {}", JSON.toJSONString(transaction));
            Object result = point.proceed();
            log.info("业务代码结束执行，当前事务: {}", JSON.toJSONString(transaction));
            transactionManager.addTransaction(transaction, annotation.isEnd(), TransactionType.COMMIT);
            return result;
        } catch (Throwable e) {
            transactionManager.addTransaction(transaction, annotation.isEnd(), TransactionType.ROLLBACK);
            throw e;
        }
    }


    @Override
    public int getOrder() {
        return 10000;
    }
}
