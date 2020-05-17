package com.aias.polar.im.server.controller;

import com.aias.polar.common.utils.ResultUtils;
import com.aias.polar.storage.api.ImageStorage;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author liuhy
 * @since 2020/3/21
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Resource(name = "imageSMStorage")
    private ImageStorage imageStorage;

    @RequestMapping("/uploadAvatar")
    public ResponseEntity<?> updateAvatar(@RequestParam("file") MultipartFile picFile) throws IOException {
        if (picFile == null || picFile.isEmpty()) {
            LOGGER.debug("uploadSingleFile upload file is empty, return failure status 400");
            return ResponseEntity.ok().body(ResultUtils.fail());
        }
        String appFileName = picFile.getOriginalFilename();
        if (StringUtils.isEmpty(appFileName)) {
            appFileName = picFile.getName();
        }
        String fileUrl = imageStorage.save(picFile.getBytes(), appFileName);
        Map<String, Object> map = Maps.newHashMap();
        map.put("avatar", fileUrl);
        return ResponseEntity.ok().body(map);
    }
}
