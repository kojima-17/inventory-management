<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>仕入先一覧</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>仕入先一覧</h1>
	<p>
		<a href="supplier/new">新規登録</a>
	</p>
	<p>${ message }</p>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>名称</th>
			<th>リードタイム(日)</th>
			<th>電話</th>
			<th>Email</th>
			<th>操作</th>
		</tr>
		<c:forEach var="supplier" items="${suppliers}">
			<tr>
				<td>${supplier.id}</td>
				<td>${supplier.name}</td>
				<td>${supplier.leadTimeDays}</td>
				<td>${supplier.phone}</td>
				<td>${supplier.email}</td>
				<td><a href="supplier/edit?id=${supplier.id}">編集</a>
					<form action="suppliers/toggle" method="post"
						style="display: inline;">
						<input type="hidden" name="id" value="${supplier.id}">
					</form></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
