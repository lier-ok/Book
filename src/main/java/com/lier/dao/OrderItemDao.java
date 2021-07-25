package com.lier.dao;



import com.lier.pojo.OrderItem;

import java.util.List;

/**
 * @Author lier
 * @date 2021/4/19 - 21:07
 * @Decription
 * @since jdk1.8
 */

public interface OrderItemDao {

    public void saveOrderItem(OrderItem orderItem);


    public List<OrderItem> queryOrderItems(String orderId);
}
