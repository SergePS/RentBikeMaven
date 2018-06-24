<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<style>
		<%@include file="/css/bootstrap.min.css"%>
		<%@include file="/css/commonStyles.css"%>
		<%@include file="/css/header.css"%>
	</style>

	<%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>
	
	<title><c:out value="${title}"></c:out></title>

</head>

<body>

	<c:set var="menuLabel" value="${parkingsLabel}:" scope="page"/>
	<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>


	<div class="container" style="margin-left: 20px">
		<div class="row">
			<div class="col-md-3">
				<div style="text-align: center; margin: 20px; float: left">
					<a href="FrontController?command=go_to_add_parking_page"><label class="downloadLabel"><c:out value="${addParkingLabel}"></c:out></label></a>
				</div>
			</div>
			<div class="col-md-9">
				<table class="table">
					<thead>
						<tr>
							<th><c:out value="${addressLabel}">:</c:out></th>
							<th><c:out value="${parkingCapacityLabel}">:</c:out></th>
							<th><c:out value="${bikeInTheParkingLabel}">:</c:out></th>
						</tr>
					</thead>
					<c:forEach items="${parkingList}" var="item">
						<tbody>
							<tr>
								<td style="width: 500px"><a href="FrontController?command=go_to_add_parking_page&parkingId=${item.id}"><c:out value="${item.address}"></c:out></a></td>
								<td style="width: 100px"><c:out value="${item.capacity}"></c:out></td>
								<td style="width: 100px"><c:out value="${item.bikeCount}"></c:out></td>					
							</tr>									
						</tbody>	
					</c:forEach>		
				</table>
			</div>		
		</div>
	</div>
	
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>	

</body>
</html>