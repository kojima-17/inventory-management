<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品編集</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>商品編集</h1>
	<form action="update" method="post">
		JAN ${ product.jan }
		<input type="hidden" name="id" value="${ product.id }">
		<input type="hidden" name="jan" value="${ product.jan }">
		名称<input type="text" name="name" value="${ product.name }">
		原価<input type="number" name="stdCost" value="${ product.stdCost }">
		売値<input type="number" name="stdPrice" value="${ product.stdPrice }">
		発注点<input type="number" name="orderPoint" value="${ product.reorderPoint }">
		ロット<input type="number" name="orderLot" value="${ product.orderLot }">
		<input type="submit" value="更新">
	</form>
</body>
</html>
