package com.lihao.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约信息
 */
public class Order implements Serializable{
    public static final String ORDERTYPE_TELEPHONE = "电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    public static final String ORDERSTATUS_YES = "已到诊";
    public static final String ORDERSTATUS_NO = "未到诊";
    private Integer id;
    private Integer member_id;//会员id
    private Date orderDate;//预约日期
    private String orderType;//预约类型 电话预约/微信预约
    private String orderStatus;//预约状态（是否到诊）
    private Integer setmeal_id;//体检套餐id

    public Order() {
    }

    public Order(Integer id) {
        this.id = id;
    }

    public Order(Integer memberId, Date orderDate, String orderType, String orderStatus, Integer setmealId) {
        this.member_id = memberId;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.setmeal_id = setmealId;
    }

    public Order(Integer id, Integer memberId, Date orderDate, String orderType, String orderStatus, Integer setmealId) {
        this.id = id;
        this.member_id = memberId;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.setmeal_id = setmealId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return member_id;
    }

    public void setMemberId(Integer memberId) {
        this.member_id = memberId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getSetmealId() {
        return setmeal_id;
    }

    public void setSetmealId(Integer setmealId) {
        this.setmeal_id = setmealId;
    }
}
