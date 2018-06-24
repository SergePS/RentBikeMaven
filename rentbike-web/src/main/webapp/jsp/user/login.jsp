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
	</style>
	
	<%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>
	
	<title><c:out value="${title}"></c:out></title>

</head>
<body>

	<input type="hidden" name="isLoginElement" value="${loginMenu}" id="isLoginElement"/>

	<nav class="navbar bg-primary navbar-dark">
				
		<h1 class="text-white">RentBike</h1>
		
		<ul class="nav nav-pills pull-xs-right">
			<li class="nav-item active">
				<a 	href="#" 
					id="choose_type" 
					class="nav-link text-white" 
					onclick="showFunc()">
					${registrationLabel}
				</a>
			</li>
			<li class="nav-item active">
				<a 	class="nav-link text-white" 
					href="FrontController?command=change_localization&lang=ru&loginMenu=" onclick="location.href=this.href+isLogin;return false;">
					${ru_button}
				</a>
			</li>
			<li class="nav-item active">
				<a 	class="nav-link text-white" 
					href="FrontController?command=change_localization&lang=en&loginMenu=" onclick="location.href=this.href+isLogin;return false;">
					${en_button}
				</a>
			</li>
		</ul>
	</nav>
	
	<br/>
	<br/>
	
	<div id="authorization" class="container" style="display:none">

		<div style="text-align:center; font-size: 20px">
			<c:out value="${loginFieldLabel}"></c:out>
		</div>
		<br>

		<form action="FrontController" method="post">
			<div class="row">
				<div class="col-md-4"><label><c:out value="${loginLabel}"></c:out></label></div>
				<div class="col-md-4">
					<input 	type="text" 
							name="login" 
							class="form-control"
							placeholder="${loginPlaceholder}"
							pattern="[a-zA-Z]{1}[a-zA-Z0-9]{2,20}"
							value="${login}"
							required
							oninvalid="setCustomValidity('${loginWarn}')"
							oninput="setCustomValidity('')">
				</div>
			</div>
			<div class="row">
				<div class="col-md-4"><label><c:out value="${passwordLabel}"></c:out></label></div>
				<div class="col-md-4">
					<input 	type="password"
							name="password" 
							class="form-control" 
							placeholder="${passwordPlaceholder}"
							required
							pattern="\w{3,15}"
							oninvalid="setCustomValidity('${password_warn}')"
							oninput="setCustomValidity('')">					
				</div>
			</div>
			<div class="row">
			<div class="col-md-4"></div>
				<div class="col-md-4" align="center">
					<input type="hidden" name="command" value="login" />
					<input type="submit" class="btn btn-primary" value="${enterButton}">
				</div>
			</div>
		</form>
	</div>
	
	
	<div   id="calend">
		<div id="registration" class="container" style="display:none">
			<div class="container">
	
				<div style="text-align:center; font-size: 20px">
					<c:out value="${registerFieldLabel}"></c:out>
				</div>
				<br>
	
				<form action="FrontController" method="post"  id="userDataForm" onsubmit="checkUserData(); return false">
				
					<%@ include file="../../WEB-INF/jspf/userPersonalData.jspf" %>
					
					<%@ include file="../../WEB-INF/jspf/password.jspf" %>
	
					<div class="row">
						<div class="col-md-4"></div>
						<div class="col-md-4" align="center">
							<input type="hidden" name="command" value="register" />
							<input type="hidden" name="isLogin" value="false" />					
							<input type="submit" class="btn btn-primary" value="${registrationButton}">
						</div>
					</div>
				</form>
			</div>
		</div>	
	</div>
	
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>		
		
	<script type="text/javascript">
		var isLogin = initLogin();
		<%@include file="/js/login.js"%>
	</script>

</body>
</html>