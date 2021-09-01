package com.ncda.util;

import java.util.Calendar;
import java.util.Date;

public class AcBiUtil {

    /**
     * date 转 Calendar
     * @param date 时间对象
     * @return calendar对象
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String strReplace(String str, String delimiter) {
        if (!CommonUtil.strIsNull(str)) {
            return String.join("|", str.split("\\s+"));
        }
        return str;
    }
}
