<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>商品一覧</h1>
	<p>
		<a href="products/new">新規登録</a>
	</p>
	<p>${ message }</p>
	<table border="1">
		<tr>
			<th>JAN</th>
			<th>名称</th>
			<th>原価</th>
			<th>売値</th>
			<th>発注点</th>
			<th>ロット</th>
			<th>廃番</th>
			<th>操作</th>
		</tr>
		<c:forEach var="product" items="${products}">
			<tr>
				<td>${product.jan}</td>
				<td>${product.name}</td>
				<td>${product.stdCost}</td>
				<td>${product.stdPrice}</td>
				<td>${product.reorderPoint}</td>
				<td>${product.orderLot}</td>
				<td><c:if test="${product.discontinued}">廃番</c:if></td>
				<td><a href="products/edit?id=${product.id}">編集</a>
					<form action="products/toggle" method="post"
						style="display: inline;">
						<input type="hidden" name="id" value="${product.id}">
						<c:choose>
							<c:when test="${product.discontinued}">
								<input type="submit" value="廃番解除">
							</c:when>
							<c:otherwise>
								<input type="submit" value="廃番にする">
							</c:otherwise>
						</c:choose>
					</form></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
