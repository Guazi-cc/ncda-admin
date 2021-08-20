package com.ncda.util;

import com.ncda.entity.ext.ExtAccountBill;

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
            if (itemName.contains("衣") || itemName.contains("裤")) {
                accountBill.setType(1);
            } else if (itemName.contains("饭") || itemName.contains("水") || itemName.contains("吃")) {
                accountBill.setType(2);
            } else if (itemName.contains("房租")
                    || itemName.contains("水费")
                    || itemName.contains("电费")
                    || itemName.contains("水电费")) {
                accountBill.setType(3);
            } else if (itemName.contains("车") || itemName.contains("公交")) {
                accountBill.setType(4);
            }
        }
        return accountBillList;
    }
}
