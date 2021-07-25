package com.lier.controller;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.lier.pojo.User;
import com.lier.service.UserService;
import com.lier.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userController")
public class UserController  {

    @Autowired
    private UserService userService;


    //注销

    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request){
        //销毁session中对象
        request.getSession().invalidate();
        //返回登录界面
        return "user/login";

    }



   //登录

    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest request){

        User loginUser = userService.login(new User(null, username, password, null));

        if(loginUser == null){

            request.setAttribute("msg","账号或密码错误");
            request.setAttribute("username",username);
            return "user/login";
        }else{

            request.getSession().setAttribute("loginUser",loginUser);
            return "/user/login_success";
        }


    }


    //注册

    @RequestMapping("/regist")
    public ModelAndView regist(User user,@RequestParam("code") String code,HttpServletRequest request,ModelAndView modelAndView) {
        //获取session中验证码
        String token = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //删除session中的验证码,以便下一次验证
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);

        if (token != null && token.equals(code)) {
            if (userService.existsUsername(user.getUsername())) {
                modelAndView.addObject("msg", "用户名已存在");
                modelAndView.addObject("username", user.getUsername());
                modelAndView.addObject("password", user.getPassword());

                modelAndView.setViewName("user/regist");

                return modelAndView;
            } else {

                userService.registUser(user);

                modelAndView.setViewName("user/regist_success");

                return modelAndView;
            }
        } else {

            modelAndView.addObject("msg", "验证码错误");
            modelAndView.addObject("username", user.getUsername());
            modelAndView.addObject("password", user.getPassword());
            modelAndView.setViewName("user/regist");
            return modelAndView;

        }
    }

    @RequestMapping("/ajaxRegist")
    @ResponseBody
    public String ajaxRegist(String username){

        boolean existsUsername = userService.existsUsername(username);

        String msg = "";

        if(existsUsername){
            msg = "true";
        }else{
            msg = "false";
        }

        return msg;
    }

    @RequestMapping("/ajaxLogin")
    @ResponseBody
    public String ajaxLogin(String username){

        String msg = "";
        boolean existsUsername = userService.existsUsername(username);

        if(existsUsername){
            msg = "true";
        }else {
            msg = "false";
        }
        return msg;
    }
}
