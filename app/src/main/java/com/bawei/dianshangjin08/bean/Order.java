package com.bawei.dianshangjin08.bean;

import java.util.List;

/**
 * 订单列表
 */
public class Order {
    private List<OrderGoodsDetail> detailList;
    private String expressCompName;
    private String expressSn;
    private String orderId;
    private int orderStatus;
    private long orderTime;
    private double payAmount;
    private int payMethod;
    private long userId;

    public List<OrderGoodsDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderGoodsDetail> detailList) {
        this.detailList = detailList;
    }

    public String getExpressCompName() {
        return expressCompName;
    }

    public void setExpressCompName(String expressCompName) {
        this.expressCompName = expressCompName;
    }

    public String getExpressSn() {
        return expressSn;
    }

    public void setExpressSn(String expressSn) {
        this.expressSn = expressSn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
