<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>仕入先編集</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>仕入先編集</h1>
	<form action="update" method="post">
	<input type="hidden" name="id" value="${ supplier.id }">
		名称<input type="text" name="name" value="${ supplier.name }">
		リードタイム<input type="number" name="leadTimeDays"  value="${ supplier.leadTimeDays }">
		電話<input type="text" name="phone" value="${ supplier.phone }">
		Email<input type="email" name="email"  value="${ supplier.email }">
		<input type="submit" value="更新">
	</form>
</body>
</html>
