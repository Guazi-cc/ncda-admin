package com.ncda.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AccountBill {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.ID
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.DATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private Date date;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.MONEY
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private BigDecimal money;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.MONEY_STATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private String moneyState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.TYPE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.COMMENT
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private String comment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.DEL_STATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private String delState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.ITEM_NAME
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private String itemName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_bill.USER_ID
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    private Integer userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.ID
     *
     * @return the value of account_bill.ID
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.ID
     *
     * @param id the value for account_bill.ID
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.DATE
     *
     * @return the value of account_bill.DATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.DATE
     *
     * @param date the value for account_bill.DATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.MONEY
     *
     * @return the value of account_bill.MONEY
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.MONEY
     *
     * @param money the value for account_bill.MONEY
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.MONEY_STATE
     *
     * @return the value of account_bill.MONEY_STATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public String getMoneyState() {
        return moneyState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.MONEY_STATE
     *
     * @param moneyState the value for account_bill.MONEY_STATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setMoneyState(String moneyState) {
        this.moneyState = moneyState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.TYPE
     *
     * @return the value of account_bill.TYPE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.TYPE
     *
     * @param type the value for account_bill.TYPE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.COMMENT
     *
     * @return the value of account_bill.COMMENT
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.COMMENT
     *
     * @param comment the value for account_bill.COMMENT
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.DEL_STATE
     *
     * @return the value of account_bill.DEL_STATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public String getDelState() {
        return delState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.DEL_STATE
     *
     * @param delState the value for account_bill.DEL_STATE
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setDelState(String delState) {
        this.delState = delState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.ITEM_NAME
     *
     * @return the value of account_bill.ITEM_NAME
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.ITEM_NAME
     *
     * @param itemName the value for account_bill.ITEM_NAME
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_bill.USER_ID
     *
     * @return the value of account_bill.USER_ID
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_bill.USER_ID
     *
     * @param userId the value for account_bill.USER_ID
     *
     * @mbggenerated Fri Aug 20 17:06:38 CST 2021
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}