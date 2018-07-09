package com.netty4.server.scanner;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行器管理类
 *
 * @author brandon
 * Created by brandon on 2018/7/8.
 */
public class InvokerManager {

    /**
     * 命令调用器
     */
    private static Map<Short, Map<Short, Invoker>> invokers = new HashMap<Short, Map<Short, Invoker>>();

    /**
     * 增加命令调用器
     *
     * @param module
     * @param cmd
     * @param invoker
     */
    public static void addInvoker(short module, short cmd, Invoker invoker) {
        Map<Short, Invoker> map = invokers.get(module);
        if (null == map) {
            map = new HashMap<Short, Invoker>();
            invokers.put(module, map);
        }
        map.put(cmd, invoker);
    }


    /**
     * 获取命令调度器
     *
     * @param module
     * @param cmd
     * @return invoker
     */
    public static Invoker getInvoker(short module, short cmd) {
        Invoker invoker = null;
        Map<Short, Invoker> map = invokers.get(module);
        if (null != map) {
            invoker = map.get(cmd);
        }
        return invoker;
    }

}
