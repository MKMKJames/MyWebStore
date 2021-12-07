<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商品列表</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
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

	<div class="row" style="width: 1210px; margin: 0 auto;">
		<!-- 从数据库获取对应分类的商品信息 -->
		<c:forEach items="${productList }" var="product">
			<div class="col-md-2" style="height:230px">
				<a href="${pageContext.request.contextPath }/product?method=getProduct_info&pid=${product.pid}"> <img src="${product.pimage }"
					width="170" height="170" style="display: inline-block;">
				</a>
				<p align="center">
					<a href="product_info.html" style='color: green'>${product.pname }</a>
				</p>
				<p align="center">
					<font color="#FF0000">商城价：&yen;${product.shop_price }</font>
				</p>
			</div>
		</c:forEach>

	</div>

	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;" align="center">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			<!-- 上一页 -->
			<c:if test="${pageBean.pageIndex==1 }">
				<li class="disabled"><a href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
			<c:if test="${pageBean.pageIndex!=1 }">
				<li><a href="${pageContext.request.contextPath }/product?method=getProductListByName&pageIndex=${pageBean.pageIndex-1 }&bookname=${bookname}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
		
			<!-- 下一页 -->
			<c:if test="${pageBean.pageIndex>=pageBean.totalPage }">
				<li class="disabled"><a href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>
			</c:if>
			<c:if test="${pageBean.pageIndex<pageBean.totalPage }">
				<li><a href="${pageContext.request.contextPath }/product?method=getProductListByName&pageIndex=${pageBean.pageIndex+1 }&bookname=${bookname}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
			</c:if>
		</ul>
	</div>
</body>

</html>