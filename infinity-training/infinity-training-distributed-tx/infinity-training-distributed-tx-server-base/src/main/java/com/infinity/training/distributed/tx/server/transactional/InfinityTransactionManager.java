package com.infinity.training.distributed.tx.server.transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infinity.training.distributed.tx.server.netty.NettyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 16:43
 */
@Slf4j
@Component
public class InfinityTransactionManager {

    @Autowired
    private NettyClient nettyClient;

    /**
     * 当前线程的事务
     */
    private final ThreadLocal<InfinityTransaction> current = new ThreadLocal<>();

    /**
     * 当前线程的事务组ID
     */
    private final ThreadLocal<String> currentGroupId = new ThreadLocal<>();

    /**
     * 当前线程的事务组下事务个数
     */
    private final ThreadLocal<Integer> transactionCount = new ThreadLocal<>();

    /**
     * 事务组
     */
    private final Map<String, InfinityTransaction> transactionMap = new HashMap<>();

    /**
     * 创建事务组，返回groupId
     */
    public String createTransactionGroup() {
        final String groupId = UUID.randomUUID().toString();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", groupId);
        jsonObject.put("command", "create");
        nettyClient.send(jsonObject);
        log.info("创建事务组: {}", jsonObject.toJSONString());
        currentGroupId.set(groupId);
        return groupId;
    }

    /**
     * 创建分布式事务
     */
    public InfinityTransaction createTransaction(String groupId) {
        String transactionId = UUID.randomUUID().toString();
        InfinityTransaction transaction = new InfinityTransaction(groupId, transactionId);
        transactionMap.put(groupId, transaction);
        current.set(transaction);
        addTransactionCount();
        log.info("创建事务: {}", JSON.toJSONString(transaction));
        return transaction;
    }

    /**
     * 添加事务到事务组
     */
    public InfinityTransaction addTransaction(InfinityTransaction transaction, boolean isEnd,
                                              TransactionType transactionType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", transaction.getGroupId());
        jsonObject.put("transactionId", transaction.getTransactionId());
        jsonObject.put("transactionType", transactionType.getName());
        jsonObject.put("command", "add");
        jsonObject.put("isEnd", isEnd);
        jsonObject.put("transactionCount", getTransactionCount());
        log.info("向分布式事务管理中心发送事务：{}", jsonObject.toJSONString());
        nettyClient.send(jsonObject);
        return transaction;
    }

    public InfinityTransaction getTransaction(String groupId) {
        return transactionMap.get(groupId);
    }

    public InfinityTransaction getCurrent() {
        return current.get();
    }

    public String getCurrentGroupId() {
        return currentGroupId.get();
    }

    public void setCurrentGroupId(String groupId) {
        currentGroupId.set(groupId);
    }

    public int getTransactionCount() {
        final Integer count = transactionCount.get();
        return count == null ? 0 : count;
    }

    public void setTransactionCount(int i) {
        transactionCount.set(i);
    }

    private void addTransactionCount() {
        setTransactionCount(getTransactionCount() + 1);
    }

}
