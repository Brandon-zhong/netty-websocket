package com.netty.server;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author brandon
 * Created by brandon on 2018/5/24.
 */
public class NettyServer {

    public static void main(String[] args) {

        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        //boss线程监听端口，worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置niosocket工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

        HashedWheelTimer timer = new HashedWheelTimer();

        //设置管道的工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
//                pipeline.addLast("decoder", new MyDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("hello", new HelloHandler());
//            pipeline.addLast("", new IdleStateHandler(timer, 5, 5, 10));
//            pipeline.addLast("heartHandler", new HeartHandler());
                return pipeline;
            }
        });

        bootstrap.bind(new InetSocketAddress(1996));
        System.out.println("netty nio start");
    }

}
