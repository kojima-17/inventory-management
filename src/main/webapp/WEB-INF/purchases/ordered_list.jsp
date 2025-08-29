<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>未検収一覧</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>未検収一覧</h1>
	<p>${ message }</p>
	<table border="1">
		<tr>
			<th>発注ID</th>
			<th>仕入先</th>
			<th>発注日</th>
			<th>検収</th>
		</tr>
		<c:forEach var="purchase" items="${ purchases }">
		<tr>
			<td>${ purchase.id }</td>
			<td>${ purchase.supplierName }</td>
			<td>${ purchase.orderedAt }</td>
			<td><a href="receive?id=${ purchase.id }">検収</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>