package com.aias.polar.common.utils;

import com.google.common.collect.Maps;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author liuhy
 * @ClassName EnumMapUtils
 * @date 2020/2/12 0:29
 */
public class EnumMapUtils {

    public static Map<String, Object> toMap(EnumMap<?, Object> enumMap) {
        Map<String, Object> result = Maps.newHashMap();
        for (Enum<?> enumEntity : enumMap.keySet()) {
            result.put(enumEntity.name(), enumMap.get(enumEntity));
        }
        return result;
    }

//    enum Test{
//        success,msg,value
//    }
//    public static void main(String[] args) {
//        EnumMap<Test, Object> enumMap = Maps.newEnumMap(Test.class);
//        enumMap.put(Test.success,false);
//
//        enumMap.put(Test.msg,"error");
//
//        enumMap.put(Test.value,"null");
//
//        Map<String,Object> result = toMap(enumMap);
//
//
//    }

}
