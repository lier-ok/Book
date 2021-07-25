package com.lier.controller;



import com.lier.pojo.Book;
import com.lier.pojo.Cart;
import com.lier.pojo.CartItem;
import com.lier.service.BookService;
import com.lier.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;


import javax.jws.WebParam;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lier
 * @date 2021/4/18 - 20:16
 * @Decription
 * @since jdk1.8
 */
@Controller
@RequestMapping("/cartController")
public class CartController{

    @Autowired
    private BookService bookService;

    @RequestMapping("/add")
    public ModelAndView addItem(@RequestParam(value = "id",defaultValue = "0") Integer id, HttpSession session,
                                ModelAndView modelAndView,HttpServletRequest req){

        Book book = bookService.queryBookById(id);

        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        Cart cart = (Cart) session.getAttribute("cart");

        if(cart == null){
            cart = new Cart();
            session.setAttribute("cart",cart);
        }

        cart.addItem(cartItem);

        session.setAttribute("lastName",cartItem.getName());

        modelAndView.setViewName("redirect:" + req.getHeader("Referer"));

        return modelAndView;

    }

    @RequestMapping("/delete")
    public ModelAndView delete(ModelAndView modelAndView,@RequestParam(value = "id",defaultValue = "0") Integer id,
                                HttpSession session,HttpServletRequest request){
        Cart cart = (Cart) session.getAttribute("cart");

        cart.deleteItem(id);

        modelAndView.setViewName("redirect:" + request.getHeader("Referer"));

        return modelAndView;
    }

    @RequestMapping("/clear")
    public ModelAndView clear(HttpSession session,HttpServletRequest request,ModelAndView modelAndView){
        Cart cart = (Cart) session.getAttribute("cart");

        cart.clear();

        modelAndView.setViewName("redirect:" + request.getHeader("Referer"));

        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestParam("id") Integer id,@RequestParam("count") Integer count,
                                ModelAndView ModelAndView,HttpServletRequest request){

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if(cart != null){
            cart.updateCount(id,count);
        }
        ModelAndView.setViewName("redirect:" + request.getHeader("Referer"));

        return ModelAndView;

    }

}