<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <span>欢迎<span class="um_span">${sessionScope.loginUser.username}</span>光临GYU网上书城</span>
    <a href="orderController/list">我的订单</a>
    <a href="userController/loginOut">注销</a>&nbsp;&nbsp;
    <a href="clientBookController/page">返回首页</a>
</div>