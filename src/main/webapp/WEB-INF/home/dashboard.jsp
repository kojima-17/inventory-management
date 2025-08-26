<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫管理システム-トップ</title>
</head>
<body>
	<jsp:include page="/WEB-INF/parts/header.jsp"></jsp:include>
	<h1>ダッシュボード</h1>
	<div>
		<p>商品数(全体/現役)</p>
		<p>${ productRows }/${ productAllCount }</p>
	</div>
	<div>
		<p>仕入先数</p>
		<p>${ supplierCount }</p>
	</div>
	<div>
		<p>在庫行数</p>
		<p>${ stockCount }</p>
	</div>
	<div>
		<p>在庫数量合計</p>
		<p>${ stockAllCount }</p>
	</div>

	<h2>最近の入出庫(最新10件)</h2>
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