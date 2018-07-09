package com.netty.client;

import com.netty4.common.*;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author brandon
 * Created by brandon on 2018/5/28.
 */
public class NettyClient {

    public static void main(String[] args) {

        //服务类
        ClientBootstrap bootstrap = new ClientBootstrap();

        //线程池
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        //socket工厂
        bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

        //管道工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("hiHandler", new HiHandler());
                return pipeline;
            }
        });

        //连接服务端
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 1996));

        Channel channel = connect.getChannel();
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("请输入");
            String line = scanner.nextLine();
            if ("exit".equals(line.trim())) {
                flag = false;
                channel.close();
                System.out.println("连接关闭");
                break;
            }
            channel.write(line);
        }
    }

}
