package com.infinity.training.basis.algorithm.loadbalance;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 负载均衡测试
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 01:35
 */
@Slf4j
public class LoadBalanceTest {

    /**
     * 升级版轮询算法--平滑加权轮询算法
     */
    @Test
    public void testWeightRoundRobin() {
        for (int i = 0; i < 100; i++) {
            String server = WeightRoundRobin.getServer();
            log.info("server ip: {}", server);
        }
    }

    /**
     * 一致性hash算法
     */
    @Test
    public void testConsistentHash() {
        for (int i = 0; i < 100; i++) {
            String clientData = "clientData" + i;
            String server = ConsistentHash.getServer(clientData);
            log.info("server ip: {}", server);

            server = ConsistentHash.getServer(clientData);
            log.info("server ip: {}", server);
        }
    }
}
