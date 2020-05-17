package com.aias.polar.im.server.netty.handler;

import com.aias.polar.im.server.entity.ImUser;
import com.aias.polar.im.server.service.ImUserService;
import com.aias.polar.im.server.util.LoginUtil;
import com.aias.polar.im.server.util.UserChannelUtil;
import com.aias.polar.jwt.utils.JwtUtils;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;

/**
 * @author liuhy
 * @since 2020/3/28
 */
@Component
@ChannelHandler.Sharable
public class ShakeHandHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Resource
    private ImUserService userService;
    @Resource
    private JwtUtils jwtUtils;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        /**
         * HTTP接入，WebSocket第一次连接使用HTTP连接,用于握手
         */
        handleHttpRequest(ctx, msg);
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
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
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

    private static boolean isKeepAlive(FullHttpRequest req) {
        return false;
    }

//    private void handlerWebSocketFrame(ChannelHandlerContext ctx,
//                                       WebSocketFrame frame) {
//
//        /**
//         * 判断是否关闭链路的指令
//         */
//        if (frame instanceof CloseWebSocketFrame) {
//            handshaker.close(ctx.channel(),
//                    (CloseWebSocketFrame) frame.retain());
//            return;
//        }
//
//        /**
//         * 判断是否ping消息
//         */
//        if (frame instanceof PingWebSocketFrame) {
//            ctx.channel().write(
//                    new PongWebSocketFrame(frame.content().retain()));
//            return;
//        }
//
//        /**
//         * 本例程仅支持文本消息，不支持二进制消息
//         */
//        if (frame instanceof BinaryWebSocketFrame) {
//            throw new UnsupportedOperationException(String.format(
//                    "%s frame types not supported", frame.getClass().getName()));
//        }
//        if (frame instanceof TextWebSocketFrame) {
//            // 返回应答消息
//            String request = ((TextWebSocketFrame) frame).text();
//            System.out.println("服务端收到：" + request);
//            ctx.channel().write(new TextWebSocketFrame("服务器收到并返回：" + request));
//        }
//
//    }
}
