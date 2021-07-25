package com.lier.controller;



import com.lier.pojo.Book;
import com.lier.pojo.Page;
import com.lier.service.BookService;
import com.lier.service.impl.BookServiceImpl;
import com.lier.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/managerBookController")
public class BookController {

    @Autowired
    private BookService bookService;


    @RequestMapping("/page")
    public ModelAndView page(ModelAndView modelAndView , HttpServletRequest req,
                             @RequestParam(value = "pageNo",defaultValue = "1") String pageNo){

        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        Page<Book> pageBook = bookService.page(Integer.parseInt(pageNo), pageSize);
        pageBook.setUrl("managerBookController/page");

        modelAndView.addObject("page",pageBook);
        modelAndView.addObject("page",pageBook);
        modelAndView.setViewName("manager/book_manager");

        return modelAndView;
    }


    @RequestMapping("/add")
    public ModelAndView addBook(Book book, ModelAndView modelAndView, HttpServletRequest req,
                                @RequestParam("pageTotalCount") String pageTotalCount,
                                @RequestPart("file") MultipartFile photo) throws IOException {


        //添加数据后跳转到改数据所在页面的算法
        int pages = Integer.parseInt(pageTotalCount);
        int pageNum = pages / Page.PAGE_SIZE + 1;
        if((pages + 1) % Page.PAGE_SIZE == 1 ){
            pageNum = pages / Page.PAGE_SIZE + 1;
        }




        //处理图片
        //上传路径保存设置
        if(!photo.isEmpty()){
            photo.transferTo(new File("E:\\springMVC\\Book\\src\\main\\webapp\\static\\img\\bookphotos" + photo.getOriginalFilename()));
        }
        book.setImgPath("static/img/bookphotos/" + photo.getOriginalFilename());

        bookService.addBook(book);

        modelAndView.setViewName("redirect: " + req.getContextPath() + "/managerBookController/page?pageNo=" + pageNum);
        return modelAndView;
    }

    //查询修改指定id书籍信息,做页面回显
    @RequestMapping("/getBookById")
    public ModelAndView getBookById(@RequestParam("id") Integer id,@RequestParam("pageNo") Integer pageNo,
                                    ModelAndView modelAndView){

        Book book = bookService.queryBookById(id);

        modelAndView.addObject("book",book);

        modelAndView.addObject("pageNo",pageNo);

        modelAndView.setViewName("manager/book_edit_update");


        return modelAndView;
    }

    //修改
    @RequestMapping("/update")
    public ModelAndView updateBook(Book book, @RequestParam("pageNo") Integer pageNo, HttpServletRequest req,
                                   ModelAndView modelAndView,@RequestPart("file") MultipartFile photo){

        //处理图片
        //上传路径保存设置
        if(!photo.isEmpty()){
            try {
                photo.transferTo(new File("E:\\springMVC\\Book\\src\\main\\webapp\\static\\img\\bookphotos" + photo.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        book.setImgPath("static/img/bookphotos/" + photo.getOriginalFilename());

        bookService.updateBook(book);

        modelAndView.setViewName("redirect:/managerBookController/page?pageNo=" + pageNo);

        return modelAndView;
    }

    @RequestMapping("/deleteBook")
    public ModelAndView deleteBook(@RequestParam("id") Integer id,@RequestParam("pageTotalCount") Integer pageTotalCount,
                                    ModelAndView modelAndView,HttpServletRequest req){
        bookService.deleteBookById(id);

        int pageNo = pageTotalCount /Page.PAGE_SIZE + 1;
        if((pageTotalCount - 1) % Page.PAGE_SIZE == 0){
            pageNo--;
        }

        modelAndView.setViewName("redirect:/managerBookController/page?pageNo=" + pageNo);

        return modelAndView;
    }



}
