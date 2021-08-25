package com.ncda.util;

import com.ncda.entity.ext.ExtAccountBill;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AcBiUtil {

    /**
     * 分析类型
     *
     * @param accountBillList
     * @return
     */
    public static List<ExtAccountBill> analysisType(List<ExtAccountBill> accountBillList) {
        for (ExtAccountBill accountBill : accountBillList) {
            String itemName = accountBill.getItemName();
            if (itemName.contains("衣")
                    || itemName.contains("裤")) {
                accountBill.setType(1);
            } else if (itemName.contains("饭")
                    || itemName.contains("水")
                    || itemName.contains("吃")
                    || itemName.contains("买菜")
                    || itemName.contains("零食")) {
                accountBill.setType(2);
            } else if (itemName.contains("房租")
                    || itemName.contains("水费")
                    || itemName.contains("电费")
                    || itemName.contains("水电费")) {
                accountBill.setType(3);
            } else if (itemName.contains("车")
                    || itemName.contains("公交")
                    || itemName.contains("地铁")
                    || itemName.contains("火车")
                    || itemName.contains("加油")
                    || itemName.contains("停车")) {
                accountBill.setType(4);
            }
        }
        return accountBillList;
    }

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

    public static String strReplace(String str, char oldChar, char newChar) {
        if (!CommonUtil.strIsNull(str)) {
            return str.replace(oldChar, newChar);
        }
        return str;
    }
}
