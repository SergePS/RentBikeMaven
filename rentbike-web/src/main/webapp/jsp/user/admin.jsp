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
		<%@include file="/css/header.css"%>
	</style>
	
	<%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>
	
	<title><c:out value="${title}"></c:out></title>
	
</head>
<body>

	<nav>	
		<ul>
			<li class="logo">RentBike</li>
			<li><a href="FrontController?command=take_all_user"><c:out value="${usersLabel}"></c:out></a></li>
			<li><a href="#">${bikesLabel}</a>
				<ul>
					<li><a href="FrontController?command=go_to_bike_purchase"><c:out value="${bikePurchaseLabel}"></c:out></a></li>
				   	<li><a href="FrontController?command=bikeCatalog&bikeCatalogWithChoise=false"><c:out value="${bikeCatalogLabel}"></c:out></a></li>
				   	<li><a href="FrontController?command=go_to_bike_product_catalog_page&bikeProductCatalogWithChoise=false"><c:out value="${productsCatalog}"></c:out></a></li> 
				</ul>	
			</li>
			
			<li><a href="FrontController?command=go_to_parking_page"><c:out value="${parkingsLabel}"></c:out></a></li>
			
			<li>
				<a href="FrontController?command=go_to_order_report_page"><c:out value="${ordersLabel}"></c:out></a>
			</li>
	
			<li style="float: right; margin-right: 50px"><a href="#"><c:out value="${user.name}"></c:out></a>
				<ul>
					<li><a href="#" onclick="showProfile()"><c:out value="${profileLabel}"></c:out></a></li>
					<li><a href="#" onclick="showPassword()"><c:out value="${passwChangeLabel}"></c:out></a></li>
					<li><a href="FrontController?command=logout"><c:out value="${logoutLabel}"></c:out></a></li>
				</ul></li>
			<li style="float: right; color: white; padding: 18px 0px 15px 10px;"><c:out value="${adminLabel}"></c:out></li>
			
		</ul>
	</nav>

	

		<div id="calend" style="margin-top: 20px">
			<%@ include file="../../WEB-INF/jspf/editUser.jspf" %>
		</div>
		
		<div id="body" style="margin: 20px">
			<c:if test="${orderList!=null}">
				<div style="text-align:center; margin-bottom:10px; font-size: 28px">
					<c:out value="${usersLabel}">:</c:out>
				</div>
				
				<div style="margin-left: 20px">
					<div class="row">
						<div class="col-md-12">
							<table class="table">
								<thead>
									<tr>
										<th><c:out value="${loginLabel}"></c:out></th>
										<th><c:out value="${nameLabel}"></c:out></th>
										<th><c:out value="${surnameLabel}"></c:out></th>
										<th><c:out value="${birthdayLabel}"></c:out></th>
										<th><c:out value="${regDateLabel}"></c:out></th>
										<th><c:out value="${roleLabel}"></c:out></th>
										<th><c:out value="${stateLabel}"></c:out></th>
										<th><c:out value="${openOrderLabel}"></c:out></th>
									</tr>
								</thead>
								<c:forEach items="${orderList}" var="item">
									<tbody>
										<tr>
											<td><c:out value="${item.user.login}"></c:out></td>	
											<td><c:out value="${item.user.name}"></c:out></td>
											<td><c:out value="${item.user.surname}"></c:out></td>
											<td><c:out value="${item.user.birthday}"></c:out></td>
											<td><c:out value="${item.user.registrationDate}"></c:out></td>
											<td><c:out value="${item.user.role}"></c:out></td>
											<td><c:out value="${item.user.state}"></c:out></td>
											<td>
												<c:if test="${item.id!=0}">
													<c:out value="${item.id}"></c:out>
												</c:if>
											</td>													
										</tr>									
									</tbody>
								</c:forEach>		
							</table>
						</div>		
					</div>
				</div>
			</c:if>
		</div>
	
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>
	
</body>
</html>