package com.aias.polar.storage.properties.smms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liuhy
 * @since 2020/5/5
 */
@Data
@ConfigurationProperties(prefix = SmmsProperties.SMMS_PROPERTIES_PREFIX)
public class SmmsProperties {

    public static final String SMMS_PROPERTIES_PREFIX = "file.smms";

    private String host;

    private String token;

}
