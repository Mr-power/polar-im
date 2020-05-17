package com.aias.polar;

import com.aias.polar.jwt.config.EnableJWT;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuhy
 */
@SpringBootApplication
@EnableJWT
@MapperScan({"com.aias.polar.*.*.dao"})
public class IMServer {
    public static void main(String[] args) {
        SpringApplication.run(IMServer.class, args);
    }
}
