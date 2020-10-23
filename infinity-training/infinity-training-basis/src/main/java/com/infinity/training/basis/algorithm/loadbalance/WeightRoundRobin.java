package com.infinity.training.basis.algorithm.loadbalance;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 升级版轮询算法--平滑加权轮询算法(避免短时间的所有请求打到权重高的服务器)
 * <p>
 * A--5
 * B--1
 * C--1
 * 普通加权轮询算法: AAAAABC
 * <p>
 * 平滑加权轮询算法：(避免短时间的所有请求打到权重高的服务器)
 * 静态权重(w): 5, 1, 1
 * 动态权重(c): 0, 0, 0
 * 权重和(s): 7
 * 最大权重(m): 5
 * <p>
 * c+=w	    m   ans  m-=s      c
 * 5,1,1	5	 A	  -2	-2,1,1
 * 3,2,2	3	 A	  -4	-4,2,2
 * 1,3,3	3	 B	  -4	1,-4,3
 * 6,-3,4	6	 A	  -1	-1,-3,4
 * 4,-2,5	5	 C	  -2	4,-2,-2
 * 9,-1,-1	9	 A	   2	2,-1,-1
 * 7,0,0	7	 A	   0	0,0,0
 * <p>
 * AABACAA
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 01:10
 */
public class WeightRoundRobin {

    private static final List<ServerWeight> SERVER_WEIGHT_LIST;

    private static int WEIGHT_SUM;

    public static String getServer() {
        ServerWeight maxServerWeight = SERVER_WEIGHT_LIST.get(0);
        for (ServerWeight serverWeight : SERVER_WEIGHT_LIST) {
            serverWeight.setDynamicWeight(serverWeight.getDynamicWeight() + serverWeight.getWeight());
            if (serverWeight.getDynamicWeight() > maxServerWeight.getDynamicWeight()) {
                maxServerWeight = serverWeight;
            }
        }
        maxServerWeight.setDynamicWeight(maxServerWeight.getDynamicWeight() - WEIGHT_SUM);
        return maxServerWeight.getServer();
    }

    static {
        SERVER_WEIGHT_LIST = new ArrayList<>();
        for (LoadBalanceConstants.ServerWeight serverWeight : LoadBalanceConstants.SERVER_WEIGHT_LIST) {
            SERVER_WEIGHT_LIST.add(new ServerWeight(serverWeight));
            WEIGHT_SUM += serverWeight.getWeight();
        }
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    static class ServerWeight extends LoadBalanceConstants.ServerWeight {

        public ServerWeight(LoadBalanceConstants.ServerWeight serverWeight) {
            super.setServer(serverWeight.getServer());
            super.setWeight(serverWeight.getWeight());
        }

        private int dynamicWeight;
    }
}
