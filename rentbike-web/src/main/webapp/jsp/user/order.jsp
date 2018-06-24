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

	<c:set var="menuLabel" value="${createOrderLabel}:" scope="page"/>
	<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>

	<div style="text-align: center; margin: 20px; float: left">
		<a href="FrontController?command=go_to_bike_product_catalog_page&bikeProductCatalogWithChoise=true"><label class="downloadLabel"><c:out value="${chooseBikeLabel}"></c:out></label></a>
	</div>

	<c:if test="${bikeProduct!=null}">
			<form action="FrontController" method="post">
				<div class="container" style="margin-top: 15px">
					<div align="center" style="border-bottom: 1px solid blue; font-size: 25px; padding-bottom: 5px">
						<c:out value="${yourChoiceLabel}"></c:out>				
					</div>
					<div class="row" style="padding-top: 5px; padding-left: 5px">
						<div class="col-md-6"  style="padding-top: 5px; margin-left: 10px; background: white">
							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${bikeNubmerLabel}"></c:out>:
								</div>
								<div class="col-md-6">
									<c:out value="${bikeProduct.id}"></c:out>
								</div>							
							</div>
							
							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${brandLabel}"></c:out> <c:out value="${modelLabel}"></c:out>:
								</div>
								<div class="col-md-6">
									<c:out value="${bikeProduct.bike.brand.brand}"></c:out> <c:out value="${bikeProduct.bike.model}"></c:out>
								</div>							
							</div>

							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${characteristicsLabel}"></c:out>:
								</div>
								<div class="col-md-6">
									<c:out value="${bikeProduct.bike.bikeType.bikeType}"></c:out>, 
									<c:out value="${wheelSizeLabel}"></c:out>: <c:out value="${bikeProduct.bike.wheelSize}"></c:out>, 
									<c:out value="${speedCountLabel}"></c:out>:  <c:out value="${bikeProduct.bike.speedCount}"></c:out>
								</div>							
							</div>

							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${startParkingLabel}"></c:out>:
								</div>
								<div class="col-md-6">
										<c:out value="${bikeProduct.parking.address}"></c:out>								
								</div>							
							</div>

							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${blockAmountNotice}"></c:out>:
								</div>
								<div class="col-md-6">
										<c:out value="${bikeProduct.value}"></c:out>					
								</div>
							</div>

							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${rentCostPerMinuteLabel}"></c:out>:
								</div>
								<div class="col-md-6">
										<c:out value="${bikeProduct.rentPrice}"></c:out>								
								</div>							
							</div>
							<div class="row" style="margin-top: 10px">
								<div class="col-md-12">
									<c:out value="${freeLabel}"></c:out>	
								</div>						
							</div>							
							<div class="row" style="margin-top: 10px; padding-bottom: 10px">
								<div class="col-md-12">
									<c:out value="${confirmOrderLabel}"></c:out>
								</div>						
							</div>
						</div>
	
						<div class="col-md-3" style="background: white; margin-top: 50px; padding-left: 100px">
							<c:if test="${bikeProduct.bike.picturePath!=null}">
								<img src="${pageContext.request.contextPath}/images/bikes/${bikeProduct.bike.picturePath}" alt="${bikeProduct.bike.model}" style= "height: 228px; border: none">
							</c:if>
						</div>				
					</div>
				</div>
				
				<div align="center" style="margin-top:20px">
					<input type="hidden" name="bikeProductId" value="${bikeProduct.id}"/>
					<input type="hidden" name="command" value="create_order"/>					
					<input type="submit" class="btn btn-primary" style="background-color: green" value="${goLabel}">
				</div>
			</form>
		
	</c:if>

</body>
</html>