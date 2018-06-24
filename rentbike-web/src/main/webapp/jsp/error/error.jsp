<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8"
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

	<c:set var="menuLabel" value="${errorLabel}" scope="page"/> 
	<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>
	
	<section>
	    <div style="padding: 20px">
	        <h1>Error</h1> 
	        <p><b>Error code:</b> ${pageContext.errorData.statusCode}</p>
	        <p><b>URL:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
	        <p><b>Exception type:</b> ${requestScope['javax.servlet.error.exception_type']}</p>
	        <p><b>Exception message:</b> ${requestScope['javax.servlet.error.message']}</p>
	        <p><b>Exception:</b> ${pageContext.exception.message}</p>
	        <p><b>Servlet name:</b> ${pageContext.errorData.servletName}</p>
	    </div>
	</section>

</body>
</html>
