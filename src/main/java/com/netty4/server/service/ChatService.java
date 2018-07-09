package com.netty4.server.service;

import com.netty4.common.*;
import org.springframework.stereotype.Service;

/**
 * 聊天业务的接口
 *
 * @author brandon
 * Created by brandon on 2018/7/9.
 */
@Service
@SocketModule(module = ModuleId.CHAT)
public class ChatService {


    /**
     * 连接管道
     *
     * @param session
     * @param user
     * @return
     */
    @SocketCommand(cmd = CommandId.CONNECT)
    public RestResp connect(Session session, User user) {
//        System.out.println("com.netty4.server.service.ChatService.connect --> " + user.toString());
        //TODO 验证获取到的用户参数，用户信息错误的不允许连接


        //判断是否登录过
        boolean online = SessionManager.isOnline(user.getId());
        if (online) {
            //从会话管理器移除会话并解除会话绑定
            Session oldSession = SessionManager.removeSession(user.getId());
            oldSession.removeAttachment();
            oldSession.close();
        }
        //绑定新会话
        if (SessionManager.putSession(user.getId(), session)) {
            session.setAttachment(user);
        } else {
            return RestResp.fail("login failure");
        }
        return RestResp.success();
    }


    /**
     * 聊天接口
     *
     * @param date
     */
    @SocketCommand(cmd = CommandId.PRIVATE_CHAT)
    public void privateChat(String date) {

    }


}
