package com.netty4.common;

/**
 * 会话抽象接口
 *
 * @author -brandon-
 */
public interface Session {


    /**
     * 向会话中写入消息
     *
     * @param message
     */
    void write(Object message);

    /**
     * 判断会话是否在连接中
     *
     * @return
     */
    boolean isConnected();

    /**
     * 关闭
     *
     * @return
     */
    void close();

    /**
     * 判断是否存活，根据心跳间隔
     *
     * @return
     */
    boolean isAlive();
}
