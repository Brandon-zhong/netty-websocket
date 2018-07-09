package com.netty4.server;

import com.netty4.common.*;
import com.netty4.server.scanner.Invoker;
import com.netty4.server.scanner.InvokerManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 消息接受处理类
 *
 * @author -琴兽-
 */
public class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 接收消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("ServerHandler.channelRead0");
    }


    /**
     * 消息处理
     *
     * @param session
     * @param request
     */
    private void handlerMessage(Session session, Request request) {

        //获取模块号和命令号
        short module = request.getModule();
        short cmd = request.getCmd();

        Response response = new Response(request);

        System.out.println("module --> " + module + "   cmd --> " + cmd);

        //根据模块号和命令号获取执行器
        Invoker invoker = InvokerManager.getInvoker(module, cmd);
        if (null != invoker) {
            Object object = invoker.invoker(session, request.getData());
        }
    }

    /**
     * 断线移除会话
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerHandler.channelInactive");
        Session session = new SessionImpl(ctx.channel());
        Object attachment = session.getAttachment();
        if (null != attachment) {
            User user = (User) attachment;
            //TODO 移除会话
            SessionManager.removeSession(user.getId());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            channels.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        incoming.closeFuture();
        channels.remove(incoming);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        cause.printStackTrace();
        ctx.close();
        channels.remove(incoming);
    }
}
