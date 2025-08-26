<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品新規登録</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>商品新規登録</h1>
	<form action="create" method="post">
		JAN<input type="text" name="jan">
		名称<input type="text" name="name">
		原価<input type="number" name="stdCost">
		売値<input type="number" name="stdPrice">
		発注点<input type="number" name="orderPoint">
		ロット<input type="number" name="orderLot">
		<input type="submit" value="登録">
	</form>
</body>
</html>
