package com.ncda.entity.ext;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ncda.entity.AccountBill;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenda
 * @title: ExtAccountBill
 * @description: 账单
 * @date 2021/8/13 9:18
 */
public class ExtAccountBill extends AccountBill implements Serializable {

    //分页
    private Integer pageSize;
    private Integer currentPage;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")    // 后台到前台的时间格式的转换
    @DateTimeFormat(pattern = "yyyy-MM-dd")     // 前后到后台的时间格式的转换
    private Date date;

    private String text;    //接收字符串用的

    private String typeOneName;

    private Double heatmapMax;      // 高级设置热力图最大值

    private Double moneyMax;       // 高级设置最大值

    private Double moneyMin;        // 高级设置最小值

    private String filterKeyword;   // 高级设置过滤关键词

    private String year;        // 日历热力图查询，年份

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTypeOneName() {
        return typeOneName;
    }

    public void setTypeOneName(String typeOneName) {
        this.typeOneName = typeOneName;
    }

    public Double getMoneyMax() {
        return moneyMax;
    }

    public void setMoneyMax(Double moneyMax) {
        this.moneyMax = moneyMax;
    }

    public Double getMoneyMin() {
        return moneyMin;
    }

    public void setMoneyMin(Double moneyMin) {
        this.moneyMin = moneyMin;
    }

    public String getFilterKeyword() {
        return filterKeyword;
    }

    public void setFilterKeyword(String filterKeyword) {
        this.filterKeyword = filterKeyword;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getHeatmapMax() {
        return heatmapMax;
    }

    public void setHeatmapMax(Double heatmapMax) {
        this.heatmapMax = heatmapMax;
    }
}
