package com.lier.service;


import com.lier.pojo.Cart;
import com.lier.pojo.Order;
import com.lier.pojo.OrderItem;

import java.util.List;

/**
 * @Author lier
 * @date 2021/4/19 - 21:30
 * @Decription
 * @since jdk1.8
 */
public interface OrderService {

    public String save(Cart cart, int userId);

    public List<Order> queryOrders(int userId);

    List<Order> query();

    public void deliverGoods(Integer userId);

    public void deliverGoodsByOrderID(String orderId);

    public Order queryOrder(String orderId);

    public void signFor(Integer userId);

    public void signForOrderId(String orderId);

    public List<OrderItem> queryOrderItems(String orderId);
}
