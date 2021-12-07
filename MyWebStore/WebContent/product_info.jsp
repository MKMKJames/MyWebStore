<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商品详情</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
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
		if (parseInt(value) < min || parseInt(value) > max) {
			alert('输入错误');
			o.value = '';
		}

	}
</script>
<!-- 引入自定义css文件 style.css -->
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
</style>

</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; width: 950px;">
				<div class="col-md-6">
					<img style="opacity: 1; width: 400px; height: 350px;" title=""
						class="medium"
						src="${pageContext.request.contextPath }/${product.pimage}">
				</div>

				<div class="col-md-6">
					<div>
						<strong>${product.pname }</strong>
					</div>
					<div style="margin: 10px 0 10px 0;">
						特惠价: <strong style="color: #ef0101;">￥:${product.shop_price }元/本</strong>
						市场价:
						<del>￥${product.market_price }/本</del>
					</div>

					<div
						style="padding: 10px; border: 1px solid #e7dbb1; width: 330px; margin: 15px 0 10px 0;; background-color: #fffee6;">
						<form
							action="${pageContext.request.contextPath }/payment/transaction?method=addToCart"
							method="post"
							onsubmit="return notEmpty(document.getElementById('quantity'));">
							<div
								style="border-bottom: 1px solid #faeac7; margin-top: 20px; padding-left: 10px;">
								购买数量: <input id="quantity" name="orderNum" size="10"
									type="number" oninput="limitInput(this);">
							</div>
							<!-- 加入隐藏标签，设置商品属性 -->
							<input type="hidden" name="pid" value="${product.pid }">
							<input type="hidden" name="pimage" value="${product.pimage }">
							<input type="hidden" name="pname" value="${product.pname }">
							<input type="hidden" name="shop_price"
								value="${product.shop_price }">
							<div style="margin: 20px 0 10px 0;; text-align: center;">
								<input
									style="background: url('./images/product.gif') no-repeat scroll 0 -600px rgba(0, 0, 0, 0); height: 36px; width: 127px;"
									value="加入购物车" type="submit">

							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div style="width: 950px; margin: 0 auto;">
				<div
					style="background-color: #d3d3d3; width: 930px; padding: 10px 10px; margin: 10px 0 10px 0;">
					<strong>商品介绍</strong>
				</div>

				<div>${product.pdesc}</div>
			</div>

		</div>
	</div>
</body>

</html>