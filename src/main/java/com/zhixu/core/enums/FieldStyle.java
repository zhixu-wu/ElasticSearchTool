package com.zhixu.core.enums;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
@Getter
public enum FieldStyle {
    DEFAULT,
    CAMEL_CASE,
    UNDER_LINE;


    public static String getStyle(@NonNull FieldStyle fieldStyle, @NonNull String field) {

        if (FieldStyle.UNDER_LINE == fieldStyle) {

            return field.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
        } else if (FieldStyle.CAMEL_CASE == fieldStyle) {

            return toCamelCase(field);
        } else {

            return field;
        }
    }


    public static String toCamelCase(@NonNull String input) {
        String[] words = input.split("[ _]"); // 分隔单词，支持空格和下划线
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i == 0) {
                // 首个单词，转为小写
                result.append(word.toLowerCase());
            } else {
                // 非首个单词，首字母转为大写
                String capitalizedWord = Character.toUpperCase(word.charAt(0)) + word.substring(1);
                result.append(capitalizedWord);
            }
        }

        return result.toString();
    }

}