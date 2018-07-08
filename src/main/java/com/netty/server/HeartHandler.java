package com.netty.server;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author brandon
 * Created by brandon on 2018/6/4.
 */
public class HeartHandler extends SimpleChannelHandler /*extends IdleStateAwareChannelHandler*/ {

//    @Override
//    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
//        System.out.println(e.getState() + "   " + new SimpleDateFormat("ss").format(new Date()));
//    }


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(e.getMessage());
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) e;
            System.out.println(event.getState() + "   " + new SimpleDateFormat("ss").format(new Date()));
        } else {
            super.handleUpstream(ctx, e);
        }
    }
}
