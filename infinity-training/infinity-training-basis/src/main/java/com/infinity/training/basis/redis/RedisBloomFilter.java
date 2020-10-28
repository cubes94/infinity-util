package com.infinity.training.basis.redis;

/**
 * 1. 缓存穿透(缓存中没有，数据库中也没有的数据)：恶意用户频繁访问数据库中不存在的数据。
 * 方案：缓存中缓存空对象；ip拉黑；参数合法性校验；布隆过滤器。
 * <p>
 * 2. 缓存击穿(缓存中没有，数据库中有的数据)(并发场景)：热点非常高的key突然失效，大量用户的请求被直接打到数据库。
 * 方案：分布式锁。
 * <p>
 * 3. 缓存雪崩：大量的key，在同一时间大量失效，或者机器宕机。
 * 方案：搭建高可用集群(热点key平均分布到不同的节点)；随机过期时间；更暴力的办法是不设置过期时间，或者定时任务给缓存续过期时间；雪崩发生后需要进行服务熔断。
 * <p>
 * <p>
 * {@link com.google.common.hash.BloomFilter}
 * 用redis实现布隆过滤器
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/26 20:28
 */
public class RedisBloomFilter {

}
