package com.ncda.util;

import com.ncda.entity.ext.ExtAccountBill;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取账单信息
 * @version 1.0
 * @description 第一个版本，想到哪写到哪，不太完善。以后打算改用正则表达式解析
 */
public class AcBiReadUtil {

    /**
     * 用于记录文本信息
     */
    private static String content;

    public static String getContent() {
        return content;
    }

    /**
     * 解析文件
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static List<ExtAccountBill> analysisAcBiFile(InputStream inputStream) throws Exception {
        setContent(inputStream);
        return readText();
    }

    /**
     * 解析文本
     * @param text
     * @return
     * @throws Exception
     */
    public static List<ExtAccountBill> analysisAcBiText(String text) throws Exception {
        setContent(text);
        return readText();
    }

    /**
     * 从流中获取文本内容
     * @param inputStream 输入流
     * @return 内容
     * @throws Exception
     */
    private static void setContent(InputStream inputStream) throws Exception {
        content = "";
        InputStreamReader read = new InputStreamReader(inputStream, StandardCharsets.UTF_8.name());
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt;         // 每行数据
        while ((lineTxt = bufferedReader.readLine()) != null) {
            if (strIsNull(content)) {
                content = content + lineTxt;
            } else  {
                content = content + "\n" + lineTxt;
            }
        }
    }

    /**
     * 从文本中获取文本内容
     * @param text
     * @return
     */
    private static void setContent(String text) {
        content = text;
    }

    /**
     * 读取文字中的账单信息
     * @return 账单数据集合
     * @throws Exception
     */
    private static List<ExtAccountBill> readText() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        List<ExtAccountBill> billList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();                // 保存所有的日期，用于检验日期是否有重复
        String[] lineTxts = content.split("\\\n");      // 保存每一行数据
        int lineNum = 0;      //记录行数
        String currYear = null;     // 当前年份
        for (String lineTxt : lineTxts) {
            lineNum++;
            if(lineNum == 1) {
                // 第一行获取年份
                currYear = getYear(lineTxt);
                if (currYear == null) {
                    throw new Exception("第一行格式错误，没找到年份");
                }
            } else {
                lineTxt = lineTxt.contains("=") ? lineTxt.substring(0, lineTxt.indexOf("=")).trim() : lineTxt;      // 若末尾有"=" 去掉
                String[] billItems = lineTxt.split("/");       // 提取出"/"分隔的每一项
                String dateStr = billItems[0];
                Date date = sdf.parse(dateFormatter(currYear, dateStr, lineNum));
                dateList.add(date);
                for (int i = 1; i < billItems.length; i++) {        // 这里从1开始，也就是跳过第一项
                    String billItem = billItems[i].trim();
                    ExtAccountBill accountBill = new ExtAccountBill();
                    accountBill.setDate(date);     // 第一项为时间
                    if(!strIsNull(billItem)) {
                        for (int j = billItem.length()-1; j >= 0; j--) {
                            if(!isNumber(billItem.charAt(j))) {
                                String substr = billItem.substring(j+1, billItem.length());
                                if(billItem.charAt(j)==43){     //若是 “+” 则为收入
                                    accountBill.setMoneyState("1");     // 状态1 收入
                                    accountBill.setItemName(billItem.substring(0, j));  // 设置名字
                                } else {                    //否则支出
                                    accountBill.setMoneyState("0");     // 状态0 支出
                                    accountBill.setItemName(billItem.substring(0, j+1));
                                }
                                accountBill.setMoney(new BigDecimal(substr));  // 设置金额
                                break;
                            }
                        }
                    }
                    billList.add(accountBill);
                }
            }
        }
        if (checkRepeatedDate(dateList)) {
            return billList;
        } else {
            throw new Exception("内容中有重复日期，请你检查检查");
        }
    }

    /**
     * 检查集合中的日期是否有重复的
     * @param list
     * @return
     */
    private static boolean checkRepeatedDate(List<Date> list) {
        return new HashSet<>(list).size() == list.size();
    }

    /**
     * 判断字符是否是数字，本方法中小数点也算数字
     * @param c 字符
     * @return 是数字 true， 不是数字 false
     */
    private static boolean isNumber(char c) {
        return c >= 48 && c <= 57 || c == 46;
    }

    /**
     * 判断字符串是否为空
     * @param str 字符串本串
     * @return 为空 true
     */
    private static boolean strIsNull(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 从字符串中解析出年份，但也仅限于2000~2099的年份，可以暂时坚持八十多年
     * @param yearStr 一个包含年份的字符串，如 ddddsss2018dfd
     * @return  若包含年份返回长度为4的年份，否则返回null
     */
    private static String getYear(String yearStr) {
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
     * @return boolean 是数字 true， 不是数字 false
     */
    private static boolean strIsDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }

    /**
     * 日期格式化
     * @param year 年份
     * @param monDay 月份和日期，格式：7.8 || 07.2 || 12.14
     * @param lineNum 记录行数，为了更好的反馈错误位置
     * @return 年月日组合并按照格式返回，格式：2021.07.22
     * @throws Exception
     */
    private static String dateFormatter(String year, String monDay, int lineNum) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(year).append(".");
        if (!monDay.contains(".")) {
            throw new Exception("第" + lineNum + "行 " + monDay + " 日期格式错误！");
        }
        String[] split = monDay.split("\\.");       // 用.分割记得加转义符号，困扰了我好几个小时的问题
        for (String s : split) {
            if (s.length() > 2 || !strIsDigit(s)) {
                throw new Exception("第" + lineNum + "行 " + monDay + " 日期格式错误！");
            }
            s = s.trim();
            String formatStr = s.length() == 1 ? "0" + s : s;
            sb.append(formatStr).append(".");
        }
        return sb.toString().substring(0, 10);
    }
}
