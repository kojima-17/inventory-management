<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>入荷(クイック)</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>入荷(クイック)</h1>
	<p>${ message }</p>
	<form action="parcheses/new" method="post">
		仕入先: <select name="warehouseId">
			<c:forEach var="supplier" items="${ suppliers }">
				<option value="${ supplier.id }"
					<c:if test="${ crtSupplierId == supplier.id }">selected</c:if>>
					${ supplier.name }</option>
			</c:forEach>
		</select> 倉庫: <select name="warehouseId">
			<c:forEach var="warehouse" items="${ warehouses }">
				<option value="${ warehouse.id }"
					<c:if test="${ crtWarehouseId == warehouse.id }">selected</c:if>>
					${ warehouse.name }</option>
			</c:forEach>
		</select> JAN: <input type="text" name="jan"> 数量: <input type="text"
			name="qty"> <input type="submit" value="受け入れ">
	</form>
</body>
</html>
