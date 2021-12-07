<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>我的购物车</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/style.css" type="text/css" />
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
</style>
<script type="text/javascript">
	<!-- 确认删除该商品 -->
	function clearItem(pid){
		if(confirm("确认删除该商品？")){
			location.href = "${pageContext.request.contextPath }/payment/transaction?method=removeItem&pid="+pid;
		}
	}
	function addItem(pid, num) {

	}
	$(function(){
		$("#clearCart").click(function(){
			if(confirm("确认清空购物车？")){
				location.href = "${pageContext.request.contextPath }/payment/transaction?method=clearCart";
			}
		});
	});
	function subOrder(){
		if(${!empty user}){
			location.href = "${pageContext.request.contextPath }/payment/transaction?method=subOrder";
		}else{
			if(confirm("请先登录")){
				location.href = "${pageContext.request.contextPath }/login.jsp";
			}
		}
	}
	function notEmpty(o) {
		if (o == null || o.value == '') {
			alert('输入不能为空');
			return false;
		}
		return true;
	}
	function limitInput(o) {
		var value = o.value;
		var min = 1;
		var max = 1000;
		if (parseInt(value) < min
				|| parseInt(value) > max) {
			alert('输入错误');
			o.value = '';
		}
	}
	</script>
</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">

			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong style="font-size: 16px; margin: 5px 0;">我的购物车</strong>
				<table class="table table-bordered">
					<tbody>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${cart.map }" var="entry">
							<tr class="active">
								<td width="60" width="40%"><input type="hidden" name="id"
									value="22"> <img
									src="${pageContext.request.contextPath }/${entry.value.pimage}"
									width="70" height="60"></td>
								<td width="30%"><a target="_blank">${entry.value.pname}</a>
								</td>
								<td width="20%">￥${entry.value.shop_price}</td>
								<td width="15%"><span>当前数量：${entry.value.orderNum }</span>
									<form action="${pageContext.request.contextPath }/payment/transaction" 
													onsubmit="return notEmpty(document.getElementById('amount'));">
										<input type="hidden" name="method" value="modifyAmount">
										<input type="hidden" name="pid" value='${entry.value.pid}'>
										<input type="hidden" name="old_amount"
											value='${entry.value.orderNum}'> <input type="hidden"
											name="shop_price" value='${entry.value.shop_price}'>
										<table>
											<tr>
												<td><input type="number" name="amount" id="amount"
													placeholder="修改商品数" style="width: 100px"
													oninput="limitInput(this);"></td>
												<td><input type="submit" value="确认"></td>
											</tr>
										</table>
									</form></td>
								<td width="15%"><span class="subtotal">￥${entry.value.sum_price}</span>
								</td>
								<td width="15%"><a href="javascript:;" id="clearItem"
									class="delete" onclick="clearItem('${entry.value.pid}')">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div style="margin-right: 130px;">
			<div style="text-align: right;">
				商品总金额: <strong style="color: #ff6600;">￥${cart.sumMoney }</strong>
			</div>
			<div
				style="text-align: right; margin-top: 10px; margin-bottom: 10px;">
				<a href="javascript:;" id="clearCart" class="clear">清空购物车</a> <a
					href="javascript:;" onclick="subOrder()"> <input type="button"
					width="100" value="提交订单" name="submit" border="0"
					style="background: url('../images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
				</a>
			</div>
		</div>

	</div>
</body>

</html>