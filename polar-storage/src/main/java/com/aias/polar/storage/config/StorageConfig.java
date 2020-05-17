package com.aias.polar.storage.config;

import com.aias.polar.storage.properties.ftp.FtpProperties;
import com.aias.polar.storage.properties.qiniu.QiniuProperties;
import com.aias.polar.storage.properties.smms.SmmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuhy
 * @ClassName FtpConfig
 * @date 2020/1/30 9:47
 */
@Configuration
@EnableConfigurationProperties({FtpProperties.class, QiniuProperties.class, SmmsProperties.class})
public class StorageConfig {

}
