package com.infinity.training.distributed.tx.server.intercept;

import com.infinity.training.distributed.tx.server.transactional.InfinityTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 17:55
 */
@Slf4j
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private InfinityTransactionManager transactionManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerDistributedTx());
    }

    /**
     * 拦截外部请求，处理分布式事务组信息
     */
    @Bean
    public HandlerInterceptor handlerDistributedTx() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String groupId = request.getHeader("groupId");
                String transactionCount = request.getHeader("transactionCount");
                if (Strings.isNotBlank(groupId)) {
                    log.info("外部请求，存在分布式事务组信息，groupId: {}, transactionCount: {}", groupId, transactionCount);
                    transactionManager.setCurrentGroupId(groupId);
                    transactionManager.setTransactionCount(Integer.parseInt(transactionCount));
                }
                return true;
            }
        };
    }
}
