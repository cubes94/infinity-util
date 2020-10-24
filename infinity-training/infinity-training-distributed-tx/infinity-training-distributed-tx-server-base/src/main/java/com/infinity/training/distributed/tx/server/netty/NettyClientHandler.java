package com.infinity.training.distributed.tx.server.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infinity.training.distributed.tx.server.transactional.InfinityTransaction;
import com.infinity.training.distributed.tx.server.transactional.InfinityTransactionManager;
import com.infinity.training.distributed.tx.server.transactional.TransactionType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 17:00
 */
@Slf4j
@Component
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private InfinityTransactionManager transactionManager;

    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收分布式事务中心消息：{}", msg.toString());
        JSONObject jsonObject = JSON.parseObject(msg.toString());
        String groupId = jsonObject.getString("groupId");
        String command = jsonObject.getString("command");

        InfinityTransaction transaction = transactionManager.getTransaction(groupId);
        if (command.equals("rollback")) {
            transaction.setTransactionType(TransactionType.ROLLBACK);
        } else {
            transaction.setTransactionType(TransactionType.COMMIT);
        }
        transaction.getTask().signalTask();
    }

    public synchronized Object call(JSONObject data) {
        context.writeAndFlush(data.toJSONString());
        return null;
    }
}
