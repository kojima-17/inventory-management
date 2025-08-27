<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>入出庫履歴</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>入出庫履歴</h1>
	<p>${ message }</p>
	<form action="stock-movements" method="post">
		倉庫: <select name="warehouseId">
			<option value="0">すべて</option>
			<c:forEach var="warehouse" items="${ warehouses }">
				<option value="${ warehouse.id }"
					<c:if test="${ crtWarehouseId == warehouse.id }">selected</c:if>>
					${ warehouse.name }</option>
			</c:forEach>
		</select>
		種別: <select name="type">
		<option>すべて</option>
		<option  value="ADJUST" <c:if test="${ crtType eq 'ADJUST' }">selected</c:if>>ADJUST</option>
		<option  value="PURCHASE" <c:if test="${ crtType eq 'PARCHASE' }">selected</c:if>>PARCHASE</option>
		<option  value="SALE" <c:if test="${ crtType eq 'SALE' }">selected</c:if>>SALE</option>
		</select>
		期間:<input type="datetime-local" name="startDateTime">
		～<input type="datetime-local" name="endDateTime">
		 キーワード(JAN/名称): <input type="text" name="serchWord"
			value="${ crtSerchWord }">
			<input type="submit" value="検索">
	</form>
	<table border="1">
		<tr>
			<th>日時</th>
			<th>JAN</th>
			<th>品目</th>
			<th>倉庫</th>
			<th>種別</th>
			<th>数量</th>
		</tr>
		<c:forEach var="stockMovement" items="${ stockMovements }">
			<tr>
				<td>${ stockMovement.movedAt }</td>
				<td>${ stockMovement.jan }</td>
				<td>${ stockMovement.productName }</td>
				<td>${ stockMovement.warehouseName }</td>
				<td>${ stockMovement.type }</td>
				<td>${ stockMovement.qty }</td>
			</tr>
		</c:forEach>

	</table>

</body>
</html>