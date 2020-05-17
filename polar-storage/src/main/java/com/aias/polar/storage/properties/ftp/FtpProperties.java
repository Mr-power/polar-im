package com.aias.polar.storage.properties.ftp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liuhy
 * @ClassName FtpProperties
 * @date 2020/1/30 9:42
 */
@Data
@ConfigurationProperties(prefix = FtpProperties.FTP_PROPERTIES_PREFIX)
public class FtpProperties {
    public static final String FTP_PROPERTIES_PREFIX = "file.ftp";
    private String ip;

    private int port = 21;

    private String user;

    private String pass;

    private String filePath;

}
