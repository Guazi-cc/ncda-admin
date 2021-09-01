package com.ncda.entity.ext;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Chart 图数据
 */
public class ChartEntiey {

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
