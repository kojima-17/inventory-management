<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫一覧</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>在庫一覧</h1>
	<p>${ message }</p>
	<form action="stock" method="post">
		倉庫: <select name="warehouseId">
			<option value="0"></option>
			<c:forEach var="warehouse" items="${ warehouses }">
				<option value="${ warehouse.id }"
					<c:if test="${ crtWarehouseId == warehouse.id }">selected</c:if>>
					${ warehouse.name }</option>
			</c:forEach>
		</select> キーワード(JAN/名称): <input type="text" name="serchWord"
			value="${ crtSerchWord }"> <input type="submit" value="検索">
	</form>
	<table border="1">
		<tr>
			<th>JAN</th>
			<th>名称</th>
			<th>倉庫</th>
			<th>在庫数</th>
		</tr>
		<c:forEach var="stock" items="${ stocks }">
			<tr>
				<td>${ stock.jan }</td>
				<td>${ stock.productName }</td>
				<td>${ stock.warehouseName }</td>
				<td>${ stock.qty }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
