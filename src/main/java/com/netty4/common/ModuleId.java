package com.netty4.common;

/**
 * 模块id
 *
 * @author brandon
 * Created by brandon on 2018/7/8.
 */
public interface ModuleId {

    /**
     * chat模块号
     */
    byte CHAT = 1;

    /**
     * message模块号
     */
    byte MESSAGE = 2;

    /**
     * notice模块号
     */
    byte NOTICE = 3;

    /**
     * order模块号
     */
    byte ORDER = 4;

    /**
     * user模块号
     */
    byte USER = 5;

    /**
     * wallet模块号
     */
    byte WALLET = 6;

    /**
     * 管道心跳
     */
    byte HEART = 7;


}
