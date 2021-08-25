package com.ncda.util;

public class CommonUtil {

    /**
     * 判断字符串是否为空
     * @param str 字符串本串
     * @return 为空 true
     */
    public static boolean strIsNull(String str) {
        return str == null || "".equals(str);
    }
}
