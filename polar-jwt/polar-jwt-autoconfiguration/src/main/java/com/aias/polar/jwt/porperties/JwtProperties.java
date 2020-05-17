package com.aias.polar.jwt.porperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liuhy
 * @ClassName JwtProperties
 * @date 2020/1/29 13:46
 */
@Data
@ConfigurationProperties(prefix = JwtProperties.JWT_PROPERTIES_PREFIX)
public class JwtProperties {
    public static final String JWT_PROPERTIES_PREFIX = "jwt";

    /**
     * 私钥
     */
    private String secret;
    /**
     * token过期时间
     */
    private long expiration;
    /**
     * 指定加密算法名称
     */
    private String signatureAlgorithmName;

    private String[] excludePathPatterns;
}
