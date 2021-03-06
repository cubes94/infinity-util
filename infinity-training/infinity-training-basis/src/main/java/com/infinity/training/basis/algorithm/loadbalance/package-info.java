/**
 * 负载均衡策略：
 * 随机算法(存在服务堆积问题)
 * 轮询算法(按照设定好的权重依次进行调度) => 升级版轮询算法--平滑加权轮询算法(避免短时间的所有请求打到权重高的服务器)
 * 一致性哈希算法(对相同参数的请求，路由到相同的提供者)
 * 最小活跃数算法(被调用次数越少，优先级越高)
 */
package com.infinity.training.basis.algorithm.loadbalance;