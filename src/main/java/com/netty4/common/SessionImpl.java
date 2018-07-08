package com.netty4.common;

import io.netty.channel.Channel;

/**
 * 管道封装类
 *
 * @author brandon
 * Created by brandon on 2018/7/8.
 */
public class SessionImpl implements Session {

    /**
     * 管道对象
     */
    private Channel channel;
    /**
     * 上次使用的时间
     */
    private long lastUseTime;
    /**
     * 管道心跳的间隔时间，默认3秒
     */
    private byte intervalTime = 3;

    public SessionImpl(Channel channel) {
        this.channel = channel;
        this.lastUseTime = System.currentTimeMillis();
    }

    @Override
    public void write(Object message) {
        channel.writeAndFlush(message);
    }

    @Override
    public boolean isConnected() {
        return channel.isActive();
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public boolean isAlive() {
        return System.currentTimeMillis() - lastUseTime < intervalTime * 1000;
    }
}
