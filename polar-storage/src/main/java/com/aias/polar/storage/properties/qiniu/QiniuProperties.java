package com.aias.polar.storage.properties.qiniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liuhy
 * @since 2020/3/21
 */
@Data
@ConfigurationProperties(prefix = QiniuProperties.QINIU_PROPERTIES_PREFIX)
public class QiniuProperties {

    public static final String QINIU_PROPERTIES_PREFIX = "file.qiniu";

    private String host;

    private String accessKey;

    private String secretKey;

    private String bucket;
}
