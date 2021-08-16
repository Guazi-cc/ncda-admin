package com.ncda.entity.ext;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ncda.entity.AccountBill;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author chenda
 * @title: ExtAccountBill
 * @description: 账单
 * @date 2021/8/13 9:18
 */
public class ExtAccountBill extends AccountBill {

    //分页
    private Integer pageSize;
    private Integer currentPage;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")    // 后台到前台的时间格式的转换
    @DateTimeFormat(pattern = "yyyy-MM-dd")     // 前后到后台的时间格式的转换
    private Date date;

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


}