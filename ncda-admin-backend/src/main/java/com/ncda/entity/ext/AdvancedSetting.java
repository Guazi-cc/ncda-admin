package com.ncda.entity.ext;

public class AdvancedSetting {

    private Double heatmapMax;      // 高级设置热力图最大值

    private Double moneyMax;       // 高级设置最大值

    private Double moneyMin;        // 高级设置最小值

    private String filterKeyword;   // 高级设置过滤关键词

    public Double getHeatmapMax() {
        return heatmapMax;
    }

    public void setHeatmapMax(Double heatmapMax) {
        this.heatmapMax = heatmapMax;
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
}
