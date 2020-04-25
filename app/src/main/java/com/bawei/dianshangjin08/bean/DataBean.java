package com.bawei.dianshangjin08.bean;

/**
 * 请求回调数据Bean类
 */
public class DataBean<T> {
    //定义
    private T result;
    private String message;
    private String status = "-1";
    //封装
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    //构造
    public DataBean(String message) {
        this.message = message;
    }
    public DataBean() {
    }
}
