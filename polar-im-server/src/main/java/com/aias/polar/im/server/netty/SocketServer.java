package com.aias.polar.im.server.netty;

import com.aias.polar.im.server.netty.handler.SocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author liuhy
 */
public class SocketServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

//    @Resource
//    private IMHandler imHandler;
//
//    @Resource
//    private ShakeHandHandler shakeHandHandler;

    @Resource
    private SocketHandler socketHandler;

    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                       .channel(NioServerSocketChannel.class)
                       .attr(AttributeKey.newInstance("serverName"), "socketServer")
                       .childHandler(new ChannelInitializer<NioSocketChannel>() {
                           @Override
                           protected void initChannel(NioSocketChannel ch) throws Exception {
                               // websocket 基于http协议，所以要有http编解码器
                               ch.pipeline().addLast(new HttpServerCodec());
                               // 对写大数据流的支持
                               ch.pipeline().addLast(new ChunkedWriteHandler());
                               // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
                               // 几乎在netty中的编程，都会使用到此hanler
                               ch.pipeline().addLast(new HttpObjectAggregator(1024 * 64));
                               /*
                                * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
                                * 本handler会帮你处理一些繁重的复杂的事
                                * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
                                * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
                                */
                               // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler的第三个参数需要设置成true
//                                    .addLast(new WebSocketServerCompressionHandler())
                               // 服务器端向外暴露的 web socket 端点，当客户端传递比较大的对象时，maxFrameSize参数的值需要调大
//                                    .addLast(new WebSocketServerProtocolHandler(Constants.DEFAULT_WEB_SOCKET_LINK,
//                                   null, true, 10485760))
//                               ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
//                               ch.pipeline().addLast(shakeHandHandler);
//                               ch.pipeline().addLast(imHandler);
                               ch.pipeline().addLast(socketHandler);

                           }
                       })
//                       .option(ChannelOption.SO_KEEPALIVE, true)
                       .option(ChannelOption.SO_BACKLOG, 1024);
//                       .option(ChannelOption.TCP_NODELAY, true);

        bind(serverBootstrap, 8000);
    }

    /**
     * 自动重试递增绑定端口
     *
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                logger.info("端口: {}绑定成功", port);
            } else {
                logger.info("端口: {}绑定失败,开始重试 ", port);

                bind(serverBootstrap, port + 1);
            }
        });
    }
}
