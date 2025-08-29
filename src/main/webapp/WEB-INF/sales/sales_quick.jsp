<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>販売(クイック)</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>販売(1行クイック)</h1>
	<p>${ message }</p>
	<form action="new" method="post">
		 倉庫: <select name="warehouseId">
			<c:forEach var="warehouse" items="${ warehouses }">
				<option value="${ warehouse.id }"
					<c:if test="${ crtWarehouseId == warehouse.id }">selected</c:if>>
					${ warehouse.name }</option>
			</c:forEach>
		</select>
		 JAN: <input type="text" name="jan" value="${ crtJan }">
		 数量: <input type="text"
			name="qty" value="${ crtQty }">
		備考: <input type="text" name="jan" value="${ crtText }">
		<input type="submit" value="出庫(販売)">
	</form>
</body>
</html>
