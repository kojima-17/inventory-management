<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検収</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>検収(発注ID:${ purchaseId })</h1>
	<p>${ message }</p>
	<form action="/purchase-orders/receive">
		<table border="1">
			<tr>
				<th>LineID</th>
				<th>商品ID</th>
				<th>発注残</th>
				<th>今回受入数量</th>
			</tr>
			<c:forEach var="purchaseLine" items="${ purchaseLines }">
				<tr>
					<td>${ purchaseLine.id }</td>
					<td>${ purchaseLine.productId }</td>
					<td>${ purchaseLine.notReceivedQty }</td>
					<td><input type="number" name="${ purchaseLine.id }"></td>
				</tr>
			</c:forEach>
		</table>
		<input type="submit" value="検収を登録">
	</form>
</body>
</html>