<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>书城首页</title>

	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">网上书城</span>
			<div>
				<%--如果用户还没有登录，显示     【登录 和注册的菜单】 --%>
				<c:if test="${empty sessionScope.loginUser}">
					<a href="pages/user/login.jsp">登录</a> |
					<a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
				</c:if>
				<%--如果已经登录，则显示 登录 成功之后的用户信息。--%>
				<c:if test="${not empty sessionScope.loginUser}">
					<span>欢迎<span class="um_span">${sessionScope.loginUser.username}</span>光临GYU网上书城</span>
					<a href="orderController/list">我的订单</a>
					<a href="userController/loginOut">注销</a>&nbsp;&nbsp;&nbsp;
				</c:if>

				<a href="pages/cart/cart.jsp">购物车</a>

				<c:if test="${sessionScope.loginUser.username != admin}">
					<a href="pages/manager/manager.jsp">后台管理</a>

				</c:if>
			</div>
	</div>

	<div id="main">
		<div id="book">
			<div class="book_cond">
				<form action="clientBookController/pageByPrice" method="get">
					<input type="hidden" name="action" value="pageByPrice">
					价格：<input id="min" type="text" name="min" value="${param.min}"> 元 -
						<input id="max" type="text" name="max" value="${param.max}"> 元
						<input type="submit" value="查询" />
				</form>
			</div>
			<div style="text-align: center">
				<c:if test="${not empty sessionScope.cart.items}">
					<span id="totalCount">您的购物车中有${sessionScope.cart.totalCount}件商品"</span>
					<div>
						您刚刚将<span id="lastName" style="color: red">${sessionScope.lastName}</span>加入到了购物车中
					</div>
				</c:if>
			</div>

			<c:forEach items="${requestScope.page.items}" var="book">
			<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="${book.imgPath}" />
				</div>
				<div class="book_info">
					<div class="book_name">
						<span class="sp1">书名:</span>
						<span class="sp2">${book.name}</span>
					</div>
					<div class="book_author">
						<span class="sp1">作者:</span>
						<span class="sp2">${book.author}</span>
					</div>
					<div class="book_price">
						<span class="sp1">价格:</span>
						<span class="sp2">￥${book.price}</span>
					</div>
					<div class="book_sales">
						<span class="sp1">销量:</span>
						<span class="sp2">${book.sales}</span>
					</div>
					<div class="book_amount">
						<span class="sp1">库存:</span>
						<span class="sp2">${book.stock}</span>
					</div>
					<div class="book_add">
						<button><a href="cartController/add?id=${book.id}">加入购物车</a></button>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>

		<%--静态包含分页条--%>
		<%@include file="/pages/common/page_nav.jsp"%>




	
	</div>

	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>

</body>
</html>