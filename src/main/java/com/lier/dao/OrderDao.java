package com.lier.dao;


import com.lier.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lier
 * @date 2021/4/19 - 20:20
 * @Decription
 * @since jdk1.8
 */


public interface OrderDao {
    public void saveOrder(Order order);

    public List<Order> queryOrders(int userId);

    List<Order> query();

    public void updateStatus(@Param("status") int status, @Param("userId") Integer userId);

    public Order queryOrder(String orderId);

    void updateStatusByOrderId(@Param("status") int status,@Param("orderId") String orderId);
}
