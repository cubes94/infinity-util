package com.infinity.training.basis.algorithm.loadbalance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 常量
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 01:14
 */
public class LoadBalanceConstants {

    // 服务器列表
    public static final List<String> SERVER_LIST;

    // 服务器和权重
    public static final List<ServerWeight> SERVER_WEIGHT_LIST;

    static {
        String[] servers = new String[]{"192.168.0.1", "192.168.0.2", "192.168.0.3", "192.168.0.4",
                "192.168.0.5", "192.168.0.6", "192.168.0.7", "192.168.0.8", "192.168.0.9"};
        int[] weights = new int[]{30, 2, 5, 1, 3, 8, 9, 1, 3, 4};
        SERVER_LIST = Arrays.asList(servers);
        SERVER_WEIGHT_LIST = new ArrayList<>();
        for (int i = 0; i < servers.length; i++) {
            SERVER_WEIGHT_LIST.add(new ServerWeight().setServer(servers[i]).setWeight(weights[i]));
        }
    }

    @Data
    @Accessors(chain = true)
    static class ServerWeight {

        private String server;

        private int weight;
    }
}
