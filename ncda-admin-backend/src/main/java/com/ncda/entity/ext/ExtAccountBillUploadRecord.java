package com.ncda.entity.ext;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ncda.entity.AccountBillUploadRecord;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author chenda
 * @title: ExtAccountBillUploadRecord
 * @description: 账单文件记录
 * @date 2021/8/13 9:18
 */
public class ExtAccountBillUploadRecord extends AccountBillUploadRecord {

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadTime;

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Date getUploadTime() {
        return uploadTime;
    }

    @Override
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
