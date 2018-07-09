package com.netty4.common;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session会话管理器
 *
 * @author brandon
 * Created by brandon on 2018/7/8.
 */
public class SessionManager {

    /**
     * 存在在线的会话
     */
    private static Map<Long, Session> userChannels = new ConcurrentHashMap<Long, Session>();

    /**
     * 加入session
     *
     * @param userId
     * @param session
     * @return
     */
    public static boolean putSession(long userId, Session session) {

        if (!userChannels.containsKey(userId)) {
            Session put = userChannels.put(userId, session);
            if (put == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 移除session
     *
     * @param userId
     * @return
     */
    public static Session removeSession(long userId) {
        return userChannels.remove(userId);
    }

    /**
     * 判断是否在线
     *
     * @param userId
     * @return
     */
    public static boolean isOnline(long userId) {
        return userChannels.containsKey(userId);
    }

    /**
     * 获取所有在线用户
     *
     * @return
     */
    public static Set<Long> getOnlineUsers() {
        return Collections.unmodifiableSet(userChannels.keySet());
    }

}
