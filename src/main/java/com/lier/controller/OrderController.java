package com.lier.controller;



import com.lier.pojo.Cart;
import com.lier.pojo.Order;
import com.lier.pojo.OrderItem;
import com.lier.pojo.User;
import com.lier.service.OrderService;
import com.lier.service.UserService;
import com.lier.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author lier
 * @date 2021/4/19 - 20:01
 * @Decription
 * @since jdk1.8
 */
@Controller
@RequestMapping("/orderController")
public class OrderController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping("createOrder")
    public String createOrder(HttpServletRequest request){
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        User user = (User) request.getSession().getAttribute("loginUser");

        if(user == null){
            return "user/login";
        }

        Integer id = userService.queryIdByName(user.getUsername());

        String orderId = orderService.save(cart, id);
        request.getSession().setAttribute("orderId",orderId);

        cart.clear();

        return "cart/checkout";
    }

    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,ModelAndView modelAndView){
        User user = (User) request.getSession().getAttribute("loginUser");
        if(user == null){
            modelAndView.setViewName("user/login");
            return modelAndView;
        }
        Integer userId = userService.queryIdByName(user.getUsername());

        List<Order> orders = orderService.queryOrders(userId);

        request.getSession().setAttribute("orders",orders);

        modelAndView.setViewName("/order/order");

        return modelAndView;

    }
    @RequestMapping("listItem")
    public ModelAndView listItem(HttpServletRequest req, ModelAndView modelAndView,
                                 @RequestParam("orderId") String orderId){


        List<OrderItem> orderItems = orderService.queryOrderItems(orderId);

        req.getSession().setAttribute("orderItems",orderItems);

        modelAndView.setViewName("orderitem/order_item");

        return modelAndView;

    }

    @RequestMapping("/listAll")
    public ModelAndView listAll(ModelAndView modelAndView, HttpSession session){

        User loginUser = (User) session.getAttribute("loginUser");

        if(loginUser == null){
            modelAndView.setViewName("user/login");
            return modelAndView;
        }

        List<Order> orders = orderService.query();

        modelAndView.addObject("managerOrders",orders);

        modelAndView.setViewName("manager/order_manager");

        return modelAndView;

    }

    @RequestMapping("/deliverGoods")
    public String deliverGoods(@RequestParam("orderId") String orderId){

        Order order = orderService.queryOrder(orderId);

        if(order.getStatus() == 0){
            orderService.deliverGoodsByOrderID(orderId);
        }

        return "redirect:/orderController/listAll";
    }

    @RequestMapping("/waitGoods")
    public ModelAndView waitGoods(ModelAndView modelAndView,@RequestParam("orderId") String orderId,HttpServletRequest req){

        Order order = orderService.queryOrder(orderId);

        if(order.getStatus() == 1){
            orderService.signForOrderId(orderId);
            modelAndView.setViewName("manager/deliverGoods_success");
            return modelAndView;
        }else if(order.getStatus() == 0){
            modelAndView.setViewName("error/notDeliverGood");
            return modelAndView;
        }else{
            modelAndView.setViewName("manager/order_manager");
            return modelAndView;
        }
    }
}
