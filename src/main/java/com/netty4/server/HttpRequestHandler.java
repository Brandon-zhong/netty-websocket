package com.netty4.server;

import com.netty4.common.RestResp;
import com.netty4.common.Session;
import com.netty4.common.SessionImpl;
import com.netty4.common.User;
import com.netty4.server.scanner.Invoker;
import com.netty4.server.scanner.InvokerManager;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by huohuo
 *
 * @author huohuo
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String wsUri;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest httpRequest) throws Exception {
        String requestUri = httpRequest.getUri().toString();
        System.out.println(requestUri);
        if (requestUri.contains(wsUri)) {
            //连接参数
            String message = requestUri.substring(requestUri.lastIndexOf("?") + 1);
            String[] split = message.split("_");
            if (split != null && split.length == 4) {
                short module = Short.valueOf(split[0]);
                short cmd = Short.valueOf(split[1]);
                User user = new User(Long.valueOf(split[2]), split[3], 1);
                //根据模块号和命令号获取执行器
                Invoker invoker = InvokerManager.getInvoker(module, cmd);
                SessionImpl session = new SessionImpl(ctx.channel());
                RestResp result = (RestResp) invoker.invoker(session, user);
                ctx.fireChannelRead(httpRequest.retain());
            }

        } else {
            HttpResponse response = new DefaultHttpResponse(httpRequest.getProtocolVersion(), HttpResponseStatus.OK);
            boolean keepAlive = HttpHeaders.isKeepAlive(httpRequest);
            if (keepAlive) {
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void getIdAndReceiverId(String id, String receiverid, String requestUri) {


    }
}
