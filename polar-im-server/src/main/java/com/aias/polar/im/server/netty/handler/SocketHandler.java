package com.aias.polar.im.server.netty.handler;

import com.aias.polar.im.server.entity.ImUser;
import com.aias.polar.im.server.entity.SocketMessage;
import com.aias.polar.im.server.service.ImMessageService;
import com.aias.polar.im.server.service.ImUserService;
import com.aias.polar.im.server.service.ImUserSessionService;
import com.aias.polar.im.server.util.LoginUtil;
import com.aias.polar.im.server.util.UserChannelUtil;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;

/**
 * @author liuhy
 * @since 2020/3/29
 */
@Component
@ChannelHandler.Sharable
public class SocketHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshaker;
    @Resource
    private ImUserService userService;

    @Resource
    private ImMessageService messageService;

    @Resource
    private ImUserSessionService userSessionService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) msg));
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        // 判断是否关闭链路的指令
        if (msg instanceof CloseWebSocketFrame) {
            UserChannelUtil.unBindUser(ctx.channel());
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg.retain());
            return;
        }// 判断是否ping消息
        if (msg instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(msg.content().retain()));
            return;
        }
        if (msg instanceof TextWebSocketFrame) {
            // 数据
            ByteBuf byteBuf = msg.content();
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
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
                // 因为目前用户登录会主动拉取消息 所以不加推送逻辑
            }
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws UnsupportedEncodingException {
        if (!req.decoderResult().isSuccess()
                || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(req.protocolVersion(), BAD_REQUEST,
                    ctx.alloc().buffer(0)));
            return;
        }
        // 获取 token 验证用户信息
        String token = getTokenFromReq(req);
        String[] strs = token.split("=");

        if (strs.length < 2 || !"token".equals(strs[0]) || strs[1].length() == 0) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(req.protocolVersion(), BAD_REQUEST,
                    ctx.alloc().buffer(0)));
            return;
        }
        // 根据 token 获取用户信息 将用户 id 和 channel 绑定起来
        ImUser user = userService.queryByToken(strs[1]);
        if (null == user) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(req.protocolVersion(), BAD_REQUEST,
                    ctx.alloc().buffer(0)));
            return;
        }
        UserChannelUtil.bindUser(user.getId(), ctx.channel());
        LoginUtil.markAsLogin(ctx.channel());
        // 建立 webSocket 连接
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:8888/ws", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private String getTokenFromReq(FullHttpRequest req) throws UnsupportedEncodingException {
        String token = req.uri();
//        token = URLDecoder.decode(token, StandardCharsets.UTF_8.name());
        token = token.replaceAll("/ws\\?", "");
        return token;
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
//        if (res.status().code() != 200) {
//            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
//                    CharsetUtil.UTF_8);
//            res.content().writeBytes(buf);
//            buf.release();
//        }
//        // 如果是非Keep-Alive，关闭连接
//        ChannelFuture f = ctx.channel().writeAndFlush(res);
//        if (!isKeepAlive(req) || res.status().code() != 200) {
//            f.addListener(ChannelFutureListener.CLOSE);
//        }
        // Generate an error page if response getStatus code is not OK (200).
        HttpResponseStatus responseStatus = res.status();
        if (responseStatus.code() != 200) {
            ByteBufUtil.writeUtf8(res.content(), responseStatus.toString());
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }
        // Send the response and close the connection if necessary.
        boolean keepAlive = HttpUtil.isKeepAlive(req) && responseStatus.code() == 200;
        HttpUtil.setKeepAlive(res, keepAlive);
        ChannelFuture future = ctx.writeAndFlush(res);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
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
