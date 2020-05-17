package com.aias.polar.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.List;
import java.util.Set;

/**
 * @author liuhy
 * @since 2020/5/10
 */
public class ExcelUtils {

    public static <T> void write(String fileName, String sheetName,
                                 Class<T> clazz, List<T> data) {
        ExcelWriter excelWriter = EasyExcel.write(fileName, clazz).build();
        WriteSheet writeSheet =
                EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(data, writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    public static <T> void write(String fileName, String sheetName, Set<String> includeColumnFiledNames,
                                 Class<T> clazz, List<T> data) {

        ExcelWriter excelWriter = EasyExcel.write(fileName, clazz).build();
        WriteSheet writeSheet =
                EasyExcel.writerSheet(sheetName).includeColumnFiledNames(includeColumnFiledNames).build();
        excelWriter.write(data, writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

}
