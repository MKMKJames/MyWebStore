<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/Style1.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script>
	function delProduct() {
		if (confirm("您确定要删除改商品吗?")) {
			return true;
		}
		return false;
	}
</script>
</HEAD>
<body>
	<br> ${message}
	<form align="right" role="search"
		action="${pageContext.request.contextPath}/admin">
		<input type="text" name="bookname" placeholder="请输入需要修改的商品名">
		<button type="submit" name="method" value="getAdminProductListByName">搜索</button>
	</form>

	<table width="100%" align="center" bgColor="#f5fafe" border="0">
		<TBODY>
			<tr>
				<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
				</TD>
			</tr>
			<tr>
				<td class="ta_01" align="center" bgColor="#f5fafe">
					<table cellspacing="0" cellpadding="1" rules="all"
						bordercolor="gray" border="1" id="DataGrid1"
						style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
						<tr
							style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

							<td align="center" width="18%">商品编号</td>
							<td align="center" width="17%">商品图片</td>
							<td align="center" width="17%">商品名称</td>
							<td align="center" width="17%">商城价</td>
							<td align="center" width="17%">市场价</td>
							<td width="7%" align="center">编辑</td>
							<td width="7%" align="center">删除</td>
						</tr>
						<c:forEach items="${productList }" var="product">
							<tr onmouseover="this.style.backgroundColor = 'white'"
								onmouseout="this.style.backgroundColor = '#F5FAFE';">
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="18%">${product.pid }</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%"><img width="40" height="45"
									src=${product.pimage }></td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${product.pname }</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${product.shop_price }</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${product.market_price }</td>
								<td align="center" style="HEIGHT: 22px">
									<!-- 
								<a
									href="${ pageContext.request.contextPath }/admin/product/edit.jsp?pid=${product.pid}">
										<img
										src="${pageContext.request.contextPath}/images/i_edit.gif"
										border="0" style="CURSOR: hand">
								</a>
								 -->
									<form
										action="${pageContext.request.contextPath}/admin/product/edit.jsp"
										method="post">
										<input type="hidden" name="pid" value="${product.pid}">
										<input type="image"
											src="${pageContext.request.contextPath}/images/i_edit.gif">
									</form>
								</td>
								<td align="center" style="HEIGHT: 22px">
									<form
										action="${pageContext.request.contextPath }/admin?method=delProduct"
										method="post" onsubmit="return delProduct()">
										<input type="hidden" name="pid" value="${product.pid}">
										<input type="image"
											src="${pageContext.request.contextPath}/images/i_del.gif">
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</TBODY>
	</table>
</body>
</HTML>

