package com.aias.polar.im.server.netty.handler;

import com.aias.polar.im.server.entity.SocketMessage;
import com.aias.polar.im.server.service.ImMessageService;
import com.aias.polar.im.server.service.ImUserSessionService;
import com.aias.polar.im.server.util.LoginUtil;
import com.aias.polar.im.server.util.UserChannelUtil;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liuhy
 */
@Component
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Resource
    private ImMessageService messageService;

    @Resource
    private ImUserSessionService userSessionService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            // 数据
            ByteBuf byteBuf = msg.content();
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            System.out.println(new String(bytes));
        /*
            暂时先用 json 格式 实现功能
            后面加上协议相关
         */

            // 将二进制数组转成 json 字符串
            // 将 json 转成对象
            // 存储 session user message 相关信息
            SocketMessage socketMessage = JSON.parseObject(new String(bytes), SocketMessage.class);
            Integer userId = UserChannelUtil.getUser(ctx.channel());
            messageService.addTextMessage(userId, socketMessage);

            // 根据 session id 查询 toUser
            Integer toUserId = userSessionService.queryToUserBySessionIdAndUserId(socketMessage.getSessionId(), userId);

            // 根据 toUserId 获取 toUserChannel
            Channel toUserChannel = UserChannelUtil.getChannel(toUserId);

            // 判断toUserChannel是否为空

            if (null != toUserChannel && LoginUtil.hasLogin(toUserChannel)) {
                toUserChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(socketMessage))).addListener(future -> {
                    if (future.isDone()) {
                        System.out.println("发送成功");
                    }
                });
            } else {
                // 如果为空 需要记录下来 等toUser登录了再推送

            }
        } else if (msg instanceof CloseWebSocketFrame) {
//            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
        } else {
            String message = "unsupported frame type: " + msg.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
        // 返回成功

//        System.out.println(new String(bytes));
//        ImUser user = new ImUser();
//        user.setPassword("123");
//        user.setUsername("zhangsan");
//        ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(user)));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开连接");
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("报错触发");
        super.exceptionCaught(ctx, cause);
    }
}
