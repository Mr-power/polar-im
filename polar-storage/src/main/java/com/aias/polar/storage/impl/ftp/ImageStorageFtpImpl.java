package com.aias.polar.storage.impl.ftp;

import com.aias.polar.storage.api.ImageStorage;
import com.aias.polar.storage.properties.ftp.FtpProperties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

/**
 * @author liuhy
 * @ClassName ImageStorageFtpImpl
 * @date 2020/1/30 9:32
 */
@Component("imageFtpStorage")
@ConditionalOnProperty(prefix = FtpProperties.FTP_PROPERTIES_PREFIX, name = "ip")
public class ImageStorageFtpImpl implements ImageStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageStorageFtpImpl.class);
    private static FTPClient ftpClient = new FTPClient();

    @Resource
    private FtpProperties ftpProperties;

    @PostConstruct
    public void init() {
        LOGGER.debug("initing ftp util...");
        try {
            // 建立连接
            LOGGER.debug("connecting ftp server...");
            if (!ftpClient.isConnected()) {
                ftpClient.connect(ftpProperties.getIp(), ftpProperties.getPort());
            }
            // 登录ftp服务器
            LOGGER.debug("logining ftp server...");
            ftpClient.login(ftpProperties.getUser(), ftpProperties.getPass());
            // 是否成功登录服务器
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                LOGGER.error("connect failed...ftp server:{},{}", ftpProperties.getIp(), ftpProperties.getPort());
                // TODO 抛出异常
            }
            LOGGER.info("connect success...ftp server:{},{}", ftpProperties.getIp(), ftpProperties.getPort());
        } catch (Exception e) {
            LOGGER.error("init ftp util error :{}", e.getMessage());
        }
    }

    /**
     * 上传文件
     *
     * @param fileBytes
     * @param fileName
     */
    @Override
    public String save(byte[] fileBytes, String fileName) {
        try {
            if (!ftpClient.changeWorkingDirectory(ftpProperties.getFilePath())) {
                LOGGER.error("进入目录失败:{}", ftpProperties.getFilePath());
                return "";
            }
            ftpClient.setBufferSize(1024);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
//            ftpClient.disconnect();
            // TODO 返回nginx代理之后的地址 暂时写死 配置Nginx之后放开
            return "https://cdn.v2ex.com/avatar/dd31/22da/168219_large.png";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
