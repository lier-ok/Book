<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>

	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			$("#delete").click(function () {
				return confirm("确定删除" + $(this).parent().parent().find(":first").text()+"吗?")
			});
			$("#clear").click(function () {
				return confirm("确定清空购物车吗?")
			});
			$(".update").change(function () {
				//获取相关参数
				var name = $(this).parent().parent().find(":first").text();
				var id = $(this).attr("bookId");
				var count = this.value;
				if(confirm("你确定修改" + name + "的数量为" + count + "吗?")){
					location.href = "http://localhost:8080/Book/cartController/update?id=" + id + "&count=" + count;
				}else{
					this.value = this.defaultValue;
				}
			});
		});
	</script>

</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">购物车</span>

		<%--静态包含，登录 成功之后的菜单 --%>
		<%@ include file="/pages/common/login_success_menu.jsp"%>


	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>		
			<c:if test="${empty sessionScope.cart.items}">
				<tr>
					<td colspan="5"><a href=clientBookController/page>购物车为空,去逛逛吧~~~~~</a></td>
				</tr>

			</c:if>
			<c:if test="${not empty sessionScope.cart.items}">
				<c:forEach items="${sessionScope.cart.items}" var="cartItem">
					<tr>
						<td>${cartItem.value.name}</td>
						<td><input type="text" style="width: 80px" class="update" bookId="${cartItem.value.id}" value="${cartItem.value.count}"></td>
						<td>${cartItem.value.price}</td>
						<td>${cartItem.value.totalPrice}</td>
						<td><a id="delete" href="cartController/delete?id=${cartItem.value.id}">删除</a></td>
					</tr>
				</c:forEach>


		</table>
		
		<div class="cart_info">
			<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
			<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.priceTotal}</span>元</span>
			<span class="cart_span"><a id="clear" href="cartController/clear">清空购物车</a></span>
			<span class="cart_span"><a href="orderController/createOrder">去结账</a></span>
		</div>
		</c:if>
	</div>


	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>


</body>
</html>