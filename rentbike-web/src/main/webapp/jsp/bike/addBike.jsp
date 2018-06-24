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
	
		<c:set var="menuLabel" value="${addBikeLabel}" scope="page"/>
		<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>
			
		<div style="margin-top: 15px">
			<%@ include file="../../WEB-INF/jspf/bike.jspf" %>
		</div>
		
		<%@ include file="../../WEB-INF/jspf/message.jspf" %>
	</body>
</html>