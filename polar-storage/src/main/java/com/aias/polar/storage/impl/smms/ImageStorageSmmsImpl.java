package com.aias.polar.storage.impl.smms;

import com.aias.polar.storage.api.ImageStorage;
import com.aias.polar.storage.properties.smms.SmmsProperties;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author liuhy
 * @since 2020/5/5
 * smms图床上传api
 */
@Component("imageSMStorage")
@ConditionalOnProperty(prefix = SmmsProperties.SMMS_PROPERTIES_PREFIX, name = "token")
public class ImageStorageSmmsImpl implements ImageStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageStorageSmmsImpl.class);

    @Resource
    private SmmsProperties smmsProperties;

    @Override
    public String save(byte[] fileBytes, String fileName) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> bodyParams = new LinkedMultiValueMap<>();
        org.springframework.core.io.Resource resource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() throws IllegalStateException {
                return fileName;
            }
        };
        bodyParams.add("smfile", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth(smmsProperties.getToken());
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99" +
                        " Safari/537.36");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyParams, headers);
        String result = restTemplate.postForObject(smmsProperties.getHost(), requestEntity, String.class);
        if (StringUtils.isEmpty(result)) {
            LOGGER.error("smmms api return null");
            return null;
        }
        JsonObject returnData = new JsonParser().parse(result).getAsJsonObject();
        if ("true".equalsIgnoreCase(returnData.get("success").toString())) {
            JsonObject data = returnData.get("data").getAsJsonObject();
            return data.get("url").getAsString();
        }
        if ("false".equalsIgnoreCase(returnData.get("success").toString())) {
            return returnData.get("images").getAsString();
        }
        return null;
    }
}
