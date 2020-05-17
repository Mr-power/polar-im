package com.aias.polar.storage.api;

/**
 * @author liuhy
 * @ClassName ImageStorage
 * @date 2020/1/30 9:30
 */
public interface ImageStorage {
    /**
     * 存储图片 返回url
     *
     * @param fileBytes
     * @param fileName
     * @return
     */
    String save(byte[] fileBytes, String fileName);

}
