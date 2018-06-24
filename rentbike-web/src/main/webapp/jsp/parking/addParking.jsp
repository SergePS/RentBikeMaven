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
		<%@include file="/css/labelStyle.css"%>
		<%@include file="/css/header.css"%>	
	</style>

	<%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>
	
	<title><c:out value="${title}"></c:out></title>

</head>
<body>

	<c:set var="menuLabel" value="${addParkingLabel}" scope="page"/>
	<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>
	
	<br>
	<form action="FrontController" method="post">
		<div class="container">
			<div class="row">		
				<div class="col-md-4"><label><c:out value="${addressLabel}"></c:out>*</label></div>
				<div class="col-md-4">
					<input
						type="text" 
						name="address"
						value = "${parking.address}"
						class="form-control" 
						placeholder="${addressPlaceholderLabel}"
						pattern="[\wа-яА-ЯёЁ\s\.,0-9]{1,50}"
						oninvalid="setCustomValidity('${addressWrongLabel}')"
						oninput="setCustomValidity('')">		
				</div>
			</div>
			<div class="row">	
				<div class="col-md-4"><label><c:out value="${parkingCapacityLabel}"></c:out>*</label></div>
				<div class="col-md-4">
						<input 	type="text"	
								name="capacity"
								value = "${parking.capacity}"
								class="form-control"
								placeholder="${parkingCapacityLabel}"
								pattern="[1-9]{1}\d{0,2}"
								oninvalid="setCustomValidity('${capacity_warn}')"
								oninput="setCustomValidity('')">
				</div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4" align="center">
					<br>		
					<c:if test="${parking==null}">
						<input type="hidden" name="command" value="add_parking">
						<input type="submit" class="btn btn-primary" value="${addLabel}">
					</c:if>
					<c:if test="${parking!=null}">
						<input type="hidden" name="parkingId" value="${parking.id}">
						<input type="hidden" name="command" value="update_parking">
						<input type="submit" class="btn btn-primary" value="${changeLabel}">
					</c:if>
					
				</div>
			</div>
		</div>
	</form>
	
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>			

</body>
</html>