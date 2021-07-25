package com.lier.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lier
 * @date 2021/4/19 - 19:53
 * @Decription 订单类
 * @since jdk1.8
 */
public class Order {
    private String orderId;
    private Date createTime;
    private BigDecimal price;
    // 0 未发货，1 已发货，2 表示已签收
    private Integer status = 0;
    private Integer userId;
    public Order() {
    }

    public Order(Integer userId, String orderId, Date createTime, BigDecimal price, int status) {
        this.userId = userId;
        this.orderId = orderId;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}