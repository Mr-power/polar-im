package com.aias.polar.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuhy
 * @since 2020/3/14
 */
public class ConvertUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    /**
     * 将实体转换为Dto List
     *
     * @param target
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> modelToDto(List<?> target, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        try {
            for (Object item : target) {
                T t = clazz.newInstance();
                BeanUtils.copyProperties(item, t);
                result.add(t);
            }
        } catch (Exception e) {
            logger.error("error in ConvertUtils.modelToDto:{}", e.getMessage());
        }
        return result;
    }

    /**
     * 将实体转换为Dto
     *
     * @param target
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T modelToDto(Object target, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(target, t);
        } catch (Exception e) {
            logger.error("error in ConvertUtils.modelToDto:{}", e.getMessage());
        }
        return t;
    }
}
