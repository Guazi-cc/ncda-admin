package com.ncda.util;

import com.ncda.entity.ext.ExtAccountBill;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<ExtAccountBill> analysisAcBiFile(InputStream inputStream) {
        List<ExtAccountBill> billList = new ArrayList<>();
        try(InputStreamReader read = new InputStreamReader(inputStream, StandardCharsets.UTF_8.name())) {
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            Integer lineCount = 0;      //记录行数
            String year1 = null;
            while((lineTxt = bufferedReader.readLine()) != null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                lineCount += 1;
                if(lineCount == 1) {
                    year1 = lineTxt.substring(0, 4);
                } else {     // 跳过第一行
                    lineTxt = lineTxt.contains("=") ? lineTxt.substring(0, lineTxt.indexOf("=")).trim() : lineTxt;      // 若末尾有"=" 去掉
                    String[] aa = lineTxt.split("/");       // 提取出"/"分隔的每一项
                    for (int i = 1; i < aa.length; i++) {
                        ExtAccountBill accountBill = new ExtAccountBill();
                        accountBill.setDate(sdf.parse(year1 + "." + aa[0]));     // 第一项为时间
                        String str1 = aa[i].trim();
                        if(strIsNotNull(str1)) {
                            for (int j = str1.length()-1; j >= 0; j--) {
                                if(!isNumber(str1.charAt(j))) {
                                    String substr = str1.substring(j+1, str1.length());
                                    if(str1.charAt(j)==43){     //若是 “+” 则为收入
                                        accountBill.setMoneyState("1");     // 状态1 收入
                                        accountBill.setItemName(str1.substring(0, j));  // 设置名字
                                        accountBill.setMoney(new BigDecimal(substr));  // 设置金额
                                        break;
                                    } else {                    //否则支出
                                        accountBill.setMoneyState("0");     // 状态0 支出
                                        accountBill.setItemName(str1.substring(0, j+1));
                                        accountBill.setMoney(new BigDecimal(substr));
                                        break;
                                    }
                                }
                            }
                        }
                        billList.add(accountBill);
                    }
                }
            }
            billList.forEach(System.out::println);
            return billList;
        } catch (Exception e) {
            System.err.println("读取文件内容出错");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isNumber(char c) {
        return c >= 48 && c <= 57 || c == 46;
    }

    public static boolean strIsNotNull(String str) {
        return str != null && !"".equals(str);
    }
}
