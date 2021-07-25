package com.lier.service.impl;

import com.lier.dao.OrderDao;
import com.lier.dao.OrderItemDao;
import com.lier.pojo.Cart;
import com.lier.pojo.CartItem;
import com.lier.pojo.Order;
import com.lier.pojo.OrderItem;
import com.lier.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author lier
 * @date 2021/4/19 - 21:36
 * @Decription
 * @since jdk1.8
 */

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    public String save(Cart cart, int userId) {
        String orderId = String.valueOf(System.currentTimeMillis() + userId);
        Order order = new Order(userId, orderId, new Date(), cart.getPriceTotal(), 0);

        orderDao.saveOrder(order);


        for(Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()){
            CartItem cartItem = entry.getValue();

            OrderItem orderItem = new OrderItem(null, orderId, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice());

            orderItemDao.saveOrderItem(orderItem);
        }
        //订单结算后清空购物车
        cart.clear();

        return orderId;
    }

    @Override
    public List<Order> queryOrders(int userId) {
        return orderDao.queryOrders(userId);
    }

    @Override
    public List<Order> query() {
        return orderDao.query();
    }

    @Override
    public void deliverGoods(Integer userId) {
        orderDao.updateStatus(1,userId);
    }

    @Override
    public void deliverGoodsByOrderID(String orderId) {
        orderDao.updateStatusByOrderId(1,orderId);
    }

    @Override
    public Order queryOrder(String orderId) {
        return orderDao.queryOrder(orderId);
    }

    @Override
    public void signFor(Integer userId) {
        orderDao.updateStatus(2,userId);
    }

    @Override
    public void signForOrderId(String orderId) {
        orderDao.updateStatusByOrderId(2,orderId);
    }

    @Override
    public List<OrderItem> queryOrderItems(String orderId) {
        List<OrderItem> orderItems = orderItemDao.queryOrderItems(orderId);
        return orderItems;
    }
}