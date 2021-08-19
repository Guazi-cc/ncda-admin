package com.ncda.entity.ext;

import com.ncda.entity.AccountBillType;

import java.util.List;

/**
 * @author chenda
 * @title: ExtAccountBillType
 * @description: 账单类型
 * @date 2021/8/19 13:57
 */
public class ExtAccountBillType extends AccountBillType {

    private String label;

    List<ExtAccountBillType> twoTypeList;   // 二级分类集合

    public List<ExtAccountBillType> getTwoTypeList() {
        return twoTypeList;
    }

    public void setTwoTypeList(List<ExtAccountBillType> twoTypeList) {
        this.twoTypeList = twoTypeList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
