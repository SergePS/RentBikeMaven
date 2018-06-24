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

	<c:set var="menuLabel" value="${addBikeTypeLabel}" scope="page"/>
	<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>

	<form action="FrontController" method="post">
		<div class="container">
			<div class="row" style="margin-top: 60px">
				<div class="col-md-4"><label><c:out value="${addBikeTypeLabel}"></c:out>*</label></div>
				<div class="col-md-4">
						<input 	type="text"
								name="bikeType"
								value = "${bikeType}"
								class="form-control"
								placeholder="${bikeTypePlaceholderLabel}"
								pattern="[a-zA-Zа-яА-ЯёЁ\s]{1,30}"
								oninvalid="setCustomValidity('${bikeTypeWarnLabel}')"
								oninput="setCustomValidity('')">
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4" align="center">		
					<input type="hidden" name="command" value="add_bike_type" />					
					<input type="submit" class="btn btn-primary" value="${addLabel}">
				</div>
			</div>
		</div>	
	</form>
	
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>

</body>
</html>