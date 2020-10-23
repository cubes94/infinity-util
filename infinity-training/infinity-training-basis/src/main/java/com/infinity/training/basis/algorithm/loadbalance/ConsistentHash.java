package com.infinity.training.basis.algorithm.loadbalance;

import java.util.SortedMap;
import java.util.TreeMap;

import static com.infinity.training.basis.algorithm.loadbalance.LoadBalanceConstants.SERVER_LIST;

/**
 * 一致性hash算法
 * <p>
 * 假设有四个结点，如果初始算法为hash%4,则如果其中一台机器宕机的话，就需要改成hash%3算法，则所有的数据都要重新计算存储的位置，所以开销比较大。
 * 一致性hash：首先对四个结点求hash值，然后分布在一个圆环上，然后对关键字也用同样hash算法求出hash值，并也分布在圆环上，其中数据存储在距离最近的结点，这样当其中一个结点宕机的时候只需要移动这个结点的部分数据到下个结点就行。
 * <p>
 * 解决方案：
 * 通过TreeMap来实现，TreeMap底层是红黑树，tailMap(Integer key)方法得到大于等于该hash值的子红黑树，firstKey()方法返回最小的key。
 * 每个服务器ip初始化n个虚拟节点，通过hash算法，将hash值作为key放到，值为该服务器ip。
 * 通过客户端的参数，计算hash值，找到最靠近的那个节点，并返回这个节点上的服务器ip。
 * 如果某个服务器挂了，可以将该map上值为此服务器ip的节点的ip进行修改或删除。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 01:41
 */
public class ConsistentHash {

    private static final TreeMap<Integer, String> VIRTUAL_NODE_MAP;

    /**
     * 虚拟节点个数
     */
    private static final int VIRTUAL_NODES = 160;

    public static String getServer(String clientData) {
        int hash = hash(clientData);
        // 得到大于等于该hash值的子红黑树
        final SortedMap<Integer, String> subMap = VIRTUAL_NODE_MAP.tailMap(hash);
        // 获取该树的第一个元素，也就是最小的元素
        Integer minNode = subMap.firstKey();
        // 如果不存在最小的元素，即该子树为空，则取整棵树的第一个元素
        if (minNode == null) {
            minNode = VIRTUAL_NODE_MAP.firstKey();
        }
        // 返回虚拟节点名称，即服务器
        return VIRTUAL_NODE_MAP.get(minNode);
    }


    static {
        VIRTUAL_NODE_MAP = new TreeMap<>();
        for (String server : SERVER_LIST) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                int hash = hash(server + "VN" + i);
                VIRTUAL_NODE_MAP.put(hash, server);
            }
        }
    }

    private static int hash(String string) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < string.length(); i++) {
            hash = (hash ^ string.charAt(i)) * p;
            hash += hash << 13;
            hash ^= hash >> 7;
            hash += hash << 3;
            hash ^= hash >> 17;
            hash += hash << 5;
        }
        return hash < 0 ? Math.abs(hash) : hash;
    }
}
