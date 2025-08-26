<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>仕入先新規登録</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>仕入先新規登録</h1>
	<form action="create" method="post">
		名称<input type="text" name="name">
		リードタイム<input type="number" name="leadTimeDays">
		電話<input type="text" name="phone">
		Email<input type="email" name="email">
		<input type="submit" value="登録">
	</form>
</body>
</html>
