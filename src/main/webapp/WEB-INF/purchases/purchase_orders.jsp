<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>発注作成</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>発注作成(複数明細)</h1>
	<p>${ message }</p>
	<form action="create" method="post">
	 仕入先:
		<select name="supplierId">
		<c:forEach var="supplier" items="${ suppliers }">
			<option value="${ supplier.id }"
				<c:if test="${ crtSupplierId == supplier.id }">selected</c:if>>
				${ supplier.name }</option>
				</c:forEach>
	</select>
		<table border="1">
			<tr>
				<th>#</th>
				<th>JAN</th>
				<th>数量</th>
			</tr>
			<c:forEach var="i" begin="1" end="10">
				<tr>
					<td>${ i }</td>
					<td><input type="text" name="jan${ i }"
						value="${ orders.get(i - 1).jan }"></td>
					<td><input type="number" name="qty${ i }"
						value="${ orders.get(i - 1).qty }"></td>
				</tr>
			</c:forEach>
		</table>
		<input type="submit" value="発注を作成">
	</form>
</body>
</html>