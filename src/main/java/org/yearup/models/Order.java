package org.yearup.models;

import org.apache.tomcat.jni.Address;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Order {
    private int orderId;
    private int userId;
    private Date orderDate;
    private Address shippingAddress;
    private BigDecimal shippingAmount;

    public Order() {}

    public Order(int orderId, int userId, Date orderDate, Address shippingAddress, BigDecimal shippingAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.shippingAmount = shippingAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", shippingAddress=" + shippingAddress +
                ", shippingAmount=" + shippingAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && userId == order.userId && Objects.equals(orderDate, order.orderDate) && Objects.equals(shippingAddress, order.shippingAddress) && Objects.equals(shippingAmount, order.shippingAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, orderDate, shippingAddress, shippingAmount);
    }
}
