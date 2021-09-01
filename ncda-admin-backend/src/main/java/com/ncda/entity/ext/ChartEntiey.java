package com.ncda.entity.ext;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Chart 图数据
 */
public class ChartEntiey {

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private BigDecimal money;

    private String typeOneName;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getTypeOneName() {
        return typeOneName;
    }

    public void setTypeOneName(String typeOneName) {
        this.typeOneName = typeOneName;
    }
}
