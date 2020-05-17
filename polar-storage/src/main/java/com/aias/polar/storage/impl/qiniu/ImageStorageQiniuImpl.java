package com.aias.polar.storage.impl.qiniu;

import com.aias.polar.storage.api.ImageStorage;
import com.aias.polar.storage.properties.qiniu.QiniuProperties;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author liuhy
 * @since 2020/3/21
 */
@Component("imageQiniuStorage")
@ConditionalOnProperty(prefix = QiniuProperties.QINIU_PROPERTIES_PREFIX, name = "host")
public class ImageStorageQiniuImpl implements ImageStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageStorageQiniuImpl.class);

    @Resource
    private QiniuProperties qiniuProperties;

    private static Auth auth;
    private static UploadManager uploadManager;

    @PostConstruct
    private void init() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        uploadManager = new UploadManager(cfg);
        auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());

    }

    @Override
    public String save(byte[] fileBytes, String fileName) {
        try {
            String token = auth.uploadToken(qiniuProperties.getBucket());
            Response response = uploadManager.put(fileBytes, fileName, token);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return qiniuProperties.getHost() + (StringUtils.isEmpty(fileName) ? putRet.hash : fileName);
        } catch (QiniuException ex) {
            Response r = ex.response;
            LOGGER.error("七牛云上传异常:{}", r.toString());
            return "";
        }

    }
}
