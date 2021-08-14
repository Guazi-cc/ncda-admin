package com.ncda.entity.result;

import java.util.Date;

public class ResultData {
    private int code;//状态码
    private String message;//信息
    private Object data;//数据
    private boolean success;
    private Date timestamp;
    private Long total;

    public static ResultData createSuccessResultData(String message,Object data ,Long total){
        ResultData instance=new ResultData();
        instance.setCode(200);
        instance.setData(data);
        instance.setTotal(total);
        instance.setMessage(message);
        instance.setSuccess(true);
        instance.setTimestamp(new Date());
        return instance;
    }
    public static ResultData createFailResultData(String message){
        ResultData instance=new ResultData();
        instance.setCode(500);
        instance.setData(null);
        instance.setTotal(0L);
        instance.setMessage(message);
        instance.setSuccess(false);
        instance.setTimestamp(new Date());
        return instance;
    }

    public static class ResultDataBuilder{
        private int code;//状态码
        private String message;//信息
        private Object data;//数据
        private boolean success;
        private Date timestamp;
        private Long total;
        public ResultDataBuilder code(int code){
            this.code=code;
            return this;
        }
        public ResultDataBuilder message(String message){
            this.message=message;
            return this;
        }
        public ResultDataBuilder data(Object data){
            this.data=data;
            return this;
        }
        public ResultDataBuilder success(boolean success){
            this.success=success;
            return this;
        }
        public ResultDataBuilder timestamp(Date timestamp){
            this.timestamp=timestamp;
            return this;
        }
        public ResultDataBuilder total(Long total){
            this.total=total;
            return this;
        }
        public ResultData build(){
            ResultData instance=new ResultData();
            instance.setCode(this.code);
            instance.setData(this.data);
            instance.setTotal(this.total);
            instance.setMessage(this.message);
            instance.setSuccess(this.success);
            instance.setTimestamp(this.timestamp);
            return instance;
        }
    }

    public static ResultDataBuilder builder(){
        return new ResultDataBuilder();
    }
    /**
     * 有参数构造
     * @param status
     * @param msg
     * @param data
     */
    public ResultData(boolean status, String msg, Object data) {
        this.success = status;
        this.message = msg;
        this.data = data;
    }

    /**
     * 无参数构造
     */
    public ResultData(){

    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 创建返回成功的实体
     * @param msg
     * @param data
     * @return
     */
    public static ResultData createSuccessResult(String msg, Object data){
        return new ResultData(ResultData.ResultStatus.STATUS_SUCCESS,msg,data);
    }

    /**
     * 创建返回失败的实体
     * @param msg
     * @param data
     * @return
     */
    public static ResultData createFailResult(String msg,Object data){
        return new ResultData(ResultData.ResultStatus.STATUS_FAIL,msg,data);
    }
    /**
     * @Description: 状态常量
     * @Param:
     * @return:
     * @Author:
     * @Date: 2020/12/23 8:58
     **/
    public class ResultStatus{
        public static final boolean STATUS_SUCCESS=true;
        public static final boolean STATUS_FAIL=false;
    }
}

