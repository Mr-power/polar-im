package com.aias.polar.storage.api;

import java.io.RandomAccessFile;

/**
 * @author liuhy
 * @ClassName IFileStorage
 * @date 2020/1/29 22:54
 */
public interface IFileStorage {
    /**
     * 保存文件
     *
     * @param fileBytes
     * @param fileName
     */
    public void save(byte[] fileBytes, String fileName);

    /**
     * 保存文件
     *
     * @param fileBytes
     * @param fileName
     * @param extensionName
     */
    public void save(byte[] fileBytes, String fileName, String extensionName);

    /**
     * 读取文件
     *
     * @param fileName
     * @return
     */
    public RandomAccessFile read(String fileName);
}
