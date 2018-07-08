package com.netty4.server;

import com.netty4.common.*;
import com.netty4.server.scanner.Invoker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 消息接受处理类
 *
 * @author -琴兽-
 */
public class ServerHandler extends SimpleChannelInboundHandler<Request> {

    /**
     * 接收消息
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {

        handlerMessage(new SessionImpl(ctx.channel()), request);
    }


    /**
     * 消息处理
     *
     * @param session
     * @param request
     */
    private void handlerMessage(Session session, Request request) {

    }

    /**
     * 断线移除会话
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = new SessionImpl(ctx.channel());
        if (session.isAlive()) {
            //TODO 移除会话
        }
    }
}
