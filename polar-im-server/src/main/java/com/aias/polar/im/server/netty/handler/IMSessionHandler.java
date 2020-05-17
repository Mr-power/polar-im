package com.aias.polar.im.server.netty.handler;

import com.aias.polar.im.server.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author liuhy
 * @since 2020/3/28
 */
public class IMSessionHandler extends WebSocketServerProtocolHandler {
    public IMSessionHandler(String websocketPath) {
        super(websocketPath);
    }

    public IMSessionHandler(String websocketPath, boolean checkStartsWith) {
        super(websocketPath, checkStartsWith);
    }

    public IMSessionHandler(String websocketPath, String subprotocols) {
        super(websocketPath, subprotocols);
    }

    public IMSessionHandler(String websocketPath, String subprotocols, boolean allowExtensions) {
        super(websocketPath, subprotocols, allowExtensions);
    }

    public IMSessionHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize) {
        super(websocketPath, subprotocols, allowExtensions, maxFrameSize);
    }

    public IMSessionHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize,
                            boolean allowMaskMismatch) {
        super(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch);
    }

    public IMSessionHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize,
                            boolean allowMaskMismatch, boolean checkStartsWith) {
        super(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch, checkStartsWith);
    }

    public IMSessionHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize,
                            boolean allowMaskMismatch, boolean checkStartsWith, boolean dropPongFrames) {
        super(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch, checkStartsWith,
                dropPongFrames);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        super.handlerAdded(ctx);
        // 继承父类方法 添加自己的实现逻辑
        // 绑定 user 和 channel 关系
        System.out.println();

        LoginUtil.markAsLogin(ctx.channel());

    }
}
