package com.netty4.common;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 管道封装类
 *
 * @author brandon
 * Created by brandon on 2018/7/8.
 */
public class SessionImpl implements Session {

    /**
     * 绑定对象key
     */
    public static AttributeKey<Object> ATTACHMENT_KEY = AttributeKey.valueOf("ATTACHMENT_KEY");

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
    public Object getAttachment() {
        return channel.attr(ATTACHMENT_KEY).get();
    }

    @Override
    public void setAttachment(Object attachment) {
        channel.attr(ATTACHMENT_KEY).set(attachment);
    }

    @Override
    public void removeAttachment() {
        channel.attr(ATTACHMENT_KEY).remove();
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
