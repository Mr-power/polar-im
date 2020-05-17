package com.aias.polar.im.server.config;

import com.aias.polar.im.server.netty.SocketServer;
import com.aias.polar.im.server.netty.handler.IMSessionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuhy
 */
@Configuration
public class SocketServerConfig {

    @Bean(initMethod = "start")
    public SocketServer socketServer() {
        return new SocketServer();
    }
//
//    @Bean
//    public IMSessionHandler websocketSessionHandler() {
//        return new IMSessionHandler("/ws");
//    }
}
