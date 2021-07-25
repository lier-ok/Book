package com.lier.controller;


import com.lier.pojo.Book;
import com.lier.pojo.Page;
import com.lier.service.BookService;
import com.lier.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/clientBookController")
public class ClientBookController  {

    @Autowired
    private BookService bookService;



    //分页
    @RequestMapping("/page")
    public ModelAndView page(ModelAndView modelAndView , HttpServletRequest req,
                            @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo){

        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        Page<Book> pageBook = bookService.page(pageNo, pageSize);
        pageBook.setUrl("clientBookController/page");
        modelAndView.addObject("page",pageBook);

        modelAndView.setViewName("client/index");

        return modelAndView;
    }



    //将价格查询后的结果分页显示
    @RequestMapping("/pageByPrice")
    public ModelAndView pageByPrice(HttpServletRequest req,ModelAndView modelAndView,
                                    @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo){
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(req.getParameter("min"), 0);
        int max = WebUtils.parseInt(req.getParameter("max"), Integer.MAX_VALUE);

        Page<Book> bookPage = bookService.pageByPrice(pageNo, pageSize, min, max);

        modelAndView.addObject("page",bookPage);
        bookPage.setUrl("bookController/pageByPrice/page");
        modelAndView.setViewName("client/index");

        return modelAndView;
    }


}
