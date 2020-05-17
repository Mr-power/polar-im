package com.aias.polar.im.server.controller;

import com.google.common.collect.Maps;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author liuhy
 */
@RequestMapping("/socket")
@RestController
public class SocketController {

    @GetMapping("/getSocketUrl")
    public ResponseEntity<?> getSocketUrl() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        Map<String, Object> map = Maps.newHashMap();

        map.put("url", "ws://" + addr.getHostAddress() + ":8000/ws");

        return ResponseEntity.ok(map);
    }

}
