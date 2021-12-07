<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<script>
	function logOut(){
		if(confirm("您确认要退出吗？")){
			location.href = "${pageContext.request.contextPath }/user?method=logOut";
		}
	}
</script>
<div class="container-fluid">
	<div class="col-md-4">
		<a href="${pageContext.request.contextPath }"></a>
	</div>
	<div class="col-md-5">
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:if test="${empty user }">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<li><a href="login.jsp">登录</a></li>&nbsp;&nbsp;
				<li><a href="register.jsp">注册</a></li>&nbsp;&nbsp;
			</c:if>
			<c:if test="${!empty user }">
				<li>
					<a href="javascript:" style="color:red">${user.username }&nbsp;&nbsp;</a></option>
					<a href="javascript:;" onclick="logOut()">退出登录</a>
				</li>
				<li><a href="/MyWebStore/payment/cart.jsp">购物车</a></li>
				<li><a href="${pageContext.request.contextPath }/payment/transaction?method=findOrder">我的订单</a></li>
			</c:if>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">首页</a>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=1">文学</a> 
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=2">计算机</a> 
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=3">经管</a>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=4">励志</a> 
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=5">社科</a> 
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=6">学术</a> 
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=7">少儿</a>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=8">艺术</a> 
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=9">原版</a> 
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=10">科技</a> 
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=11">考试</a>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=getProductList&cid=12">生活百科</a> 
			</div>
			
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryList">
					
				</ul>
				<form class="navbar-form navbar-right" role="search" action="${pageContext.request.contextPath}/product">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="请输入书名" name="bookname">
					</div>
					<button type="submit" class="btn btn-default" name="method" value="getProductListByName">搜索</button>
				</form>
			</div>
		</div>
	</nav>
</div>