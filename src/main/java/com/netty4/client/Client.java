package com.netty4.client;

import com.netty4.common.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.net.InetSocketAddress;

/**
 * @author brandon
 * Created by brandon on 2018/7/9.
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 设置循环线程组事例
        bootstrap.group(workerGroup);

        // 设置channel工厂
        bootstrap.channel(NioSocketChannel.class);

        // 设置管道
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpServerCodec());
            }
        });

        // 连接服务端
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8090));
        System.out.println(connect.channel().isOpen() + "   " + connect.channel().isActive());
        while (true) {
            Thread.sleep(3000);
            String toJson = JsonUtil.toJson(Request.valueOf(ModuleId.CHAT, CommandId.CONNECT, JsonUtil.toJson(new User())));
            System.out.println(toJson);
            connect.channel().writeAndFlush(new TextWebSocketFrame(toJson));
        }
    }

}
