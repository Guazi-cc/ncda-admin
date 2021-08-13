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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static List<ExtAccountBill> analysisAcBiFile(InputStream inputStream) {
        List<ExtAccountBill> billList = new ArrayList<>();
        try(InputStreamReader read = new InputStreamReader(inputStream, StandardCharsets.UTF_8.name())) {
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;         // 每行数据
            Integer lineCount = 0;      //记录行数
            String currYear = null;     // 当前年份
            while((lineTxt = bufferedReader.readLine()) != null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                lineCount++;
                if(lineCount == 1) {
                    // 第一行获取年份
                    currYear = getYear(lineTxt);
                    if (currYear == null) {
                        throw new Exception("第一行格式错误，没找到年份");
                    }
                } else {     // 跳过第一行
                    lineTxt = lineTxt.contains("=") ? lineTxt.substring(0, lineTxt.indexOf("=")).trim() : lineTxt;      // 若末尾有"=" 去掉
                    String[] aa = lineTxt.split("/");       // 提取出"/"分隔的每一项
                    for (int i = 1; i < aa.length; i++) {
                        ExtAccountBill accountBill = new ExtAccountBill();
                        String formatDate = dateFormatter(currYear, aa[0]);
                        System.out.println(formatDate);
                        sdf.setLenient(false);
                        accountBill.setDate(sdf.parse(formatDate));     // 第一项为时间
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

    /**
     * 判断字符是否是数字，本方法中小数点也算数字
     * @param c 字符
     * @return 是不是？
     */
    public static boolean isNumber(char c) {
        return c >= 48 && c <= 57 || c == 46;
    }

    /**
     * 判断字符串是否为空
     * @param str 字符串本串
     * @return 是不是？
     */
    public static boolean strIsNotNull(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * 从字符串中解析出年份
     * @param yearStr 一个包含年份的字符串，如 ddddsss2018dfd
     * @return  若包含年份返回长度为4的年份，否则返回null
     */
    public static String getYear(String yearStr) {
        for (int i = 0; i < yearStr.length(); i++) {
            if (yearStr.charAt(i) == '2' && yearStr.charAt(i+1) == '0') {
                String year =  yearStr.substring(i, i+4);
                if (strIsDigit(year)) {
                    return year;
                }
                return null;
            }
        }
        return null;
    }

    /**
     * 判断字符串是否由数字组成
     * @param strNum 字符串
     * @return boolean
     */
    public static boolean strIsDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }


    public static String dateFormatter(String year, String monDay) {
        StringBuilder sb = new StringBuilder();
        sb.append(year).append(".");
//        sb.append(monDay);
        String[] split = monDay.split(".");

        for (String s : split) {
            sb.append(s);
        }
        return sb.toString();
    }
}
