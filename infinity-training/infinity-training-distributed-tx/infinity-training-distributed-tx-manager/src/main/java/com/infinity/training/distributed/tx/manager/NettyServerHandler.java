package com.infinity.training.distributed.tx.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作为事务管理者，它需要：
 * 1. 创建并保存事务组
 * 2. 保存各个子事务在对应的事务组内
 * 3. 统计并判断事务组内的各个子事务状态，以算出当前事务组的状态（提交or回滚）
 * 4. 通知各个子事务提交或回滚
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 事务组中的事务状态列表<groupId, transactionType>
    private static final Map<String, List<String>> GROUP_ID_TRANSACTION_TYPE_MAP;

    // 事务组是否已经接收到结束的标记<groupId, isEnd>
    private static final Map<String, Boolean> GROUP_ID_IS_END_MAP;

    // 事务组中应该有的事务个数<groupId, transactionCount>
    private static final Map<String, Integer> GROUP_ID_TRANSACTION_COUNT_MAP;

    static {
        GROUP_ID_TRANSACTION_TYPE_MAP = new HashMap<>();
        GROUP_ID_IS_END_MAP = new HashMap<>();
        GROUP_ID_TRANSACTION_COUNT_MAP = new HashMap<>();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("添加远程连接: channelId: {}", ctx.channel().id());
        CHANNEL_GROUP.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("关闭远程连接: channelId: {}", ctx.channel().id());
        super.handlerRemoved(ctx);
    }

    /**
     * 创建事务组，并且添加保存事务
     * 并且需要判断，如果所有事务都已经执行了（有结果了，要么回滚，要么提交），且其中有一个事务需要回滚，那么通知所有客户端进行回滚
     * 否则，则通知所有客户端进行提交
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("接收数据: channelId: {}, msg: {}", ctx.channel().id(), msg.toString());

        JSONObject jsonObject = JSON.parseObject((String) msg);

        String command = jsonObject.getString("command"); // create-创建事务组，add-添加事务
        String groupId = jsonObject.getString("groupId");   // 事务组id
        String transactionType = jsonObject.getString("transactionType"); // 子事务类型，commit-待提交，rollback-待回滚
        Integer transactionCount = jsonObject.getInteger("transactionCount");   // 事务数量
        Boolean isEnd = jsonObject.getBoolean("isEnd"); // 是否是结束事务


        if ("create".equals(command)) {
            // 创建事务组
            GROUP_ID_TRANSACTION_TYPE_MAP.put(groupId, new ArrayList<>());
        } else if ("add".equals(command)) {
            // 加入事务组
            GROUP_ID_TRANSACTION_TYPE_MAP.get(groupId).add(transactionType);

            if (isEnd) {
                GROUP_ID_IS_END_MAP.put(groupId, true);
                GROUP_ID_TRANSACTION_COUNT_MAP.put(groupId, transactionCount);
            }

            JSONObject result = new JSONObject();
            result.put("groupId", groupId);
            log.info("GROUP_ID_TRANSACTION_TYPE_MAP: {}", JSON.toJSONString(GROUP_ID_TRANSACTION_TYPE_MAP));
            log.info("GROUP_ID_IS_END_MAP: {}", JSON.toJSONString(GROUP_ID_IS_END_MAP));
            log.info("GROUP_ID_TRANSACTION_COUNT_MAP: {}", JSON.toJSONString(GROUP_ID_TRANSACTION_COUNT_MAP));
            // 如果已经接收到结束事务的标记，比较事务是否已经全部到达，如果已经全部到达则看是否需要回滚
            if (GROUP_ID_IS_END_MAP.get(groupId) && GROUP_ID_TRANSACTION_COUNT_MAP.get(groupId).equals(GROUP_ID_TRANSACTION_TYPE_MAP.get(groupId).size())) {
                if (GROUP_ID_TRANSACTION_TYPE_MAP.get(groupId).contains("rollback")) {
                    result.put("command", "rollback");
                } else {
                    result.put("command", "commit");
                }
                sendResult(result);
            }

        }
    }

    private void sendResult(JSONObject result) {
        for (Channel channel : CHANNEL_GROUP) {
            final String msg = result.toJSONString();
            log.info("发送数据：channelId: {}, msg: {}", channel.id(), msg);
            channel.writeAndFlush(msg);
        }
    }
}
