package com.infinity.training.distributed.tx.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式事务注解
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 16:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InfinityTransactional {

    boolean isStart() default false;

    boolean isEnd() default false;
}
