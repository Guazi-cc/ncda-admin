package com.ncda.entity.ext;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ncda.entity.AccountBill;
import com.ncda.util.AcBiUtil;
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

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date yearMonth;         // 年月

    private String text;            //接收字符串用的

    private String typeOneName;     // 一级类型的名字 关联查询字段

    private String year;        // 日历热力图查询，年份

    private String xdataType;       // 柱状图搜索，横轴（0以时间为横轴，1以类型为横轴）

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getXdataType() {
        return xdataType;
    }

    public void setXdataType(String xdataType) {
        this.xdataType = xdataType;
    }
}
