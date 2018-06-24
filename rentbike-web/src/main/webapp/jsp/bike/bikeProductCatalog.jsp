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

	<c:set var="menuLabel" value="${chooseBikeLabel}" scope="page"/>
	<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>

	<div style="padding-left: 15px; padding-right: 15px">
		<div class="row">
			<div class="col-md-2" style="background: #F0FFF0; padding-left: 10px; padding-bottom: 50px">
				<form action="FrontController" method="post">
				
					<!-- --------------------------------select parking ------------------------------------ -->
					<div>
						<label style="margin-left:10px; margin-top: 20px">${parkingLabel}:</label>
						<select	name="parkingId"  class="form-control">
							<c:if test="${parkingId==null || parkingId == 0}">
								<option selected value="0"><c:out value="${chooseParkingLabel}"></c:out></option>
							</c:if>
							<c:if test="${parkingId != null && parkingId != '0'}">
								<option value="0"><c:out value="${chooseParkingLabel}"></c:out></option>
							</c:if>
							<c:forEach items="${parkingList}" var="item">
								<option <c:if test="${parkingId==item.id}">selected</c:if> value="${item.id}"><c:out value="${item.address}"></c:out></option>
							</c:forEach> 
						</select>				
					</div>

					<!-- --------------------------------select brand -------------------------------------- -->
					<div>
						<label style="margin-left:10px; margin-top: 20px"><c:out value="${brandLabel}"></c:out>:</label>
						<select	name="brandId"  class="form-control">
							<c:if test="${brandId==null || brandId == 0}">
								<option selected value="0"><c:out value="${chooseBarndLabel}"></c:out></option>
							</c:if>
							<c:if test="${brandId != null && brandId != '0'}">
								<option value="0"><c:out value="${chooseBarndLabel}"></c:out></option>
							</c:if>
							<c:forEach items="${brandList}" var="item">
								<option <c:if test="${brandId==item.id}">selected</c:if> value="${item.id}">${item.brand}</option>
							</c:forEach> 
						</select>				
					</div>
					
					<!-- ----------------------------------input model ------------------------------------ -->					
					<div>
						<label style="margin-left:10px; margin-top: 20px"><c:out value="${modelLabel}"></c:out>:</label>
						<input 	type="text"	
							name="model"
							class="form-control"
							value="${model}" 
							placeholder="${modelPlaceholder}"
							pattern="[\w\-\sа-яА-ЯёЁ]{1,15}"
							oninvalid="setCustomValidity('${modelWarnLabel}')"
							oninput="setCustomValidity('')">					
					</div>
					
					<!-- --------------------------------select bikeType ------------------------------------ -->
					<div>
						<label style="margin-left:10px; margin-top: 20px"><c:out value="${bikeTypeLabel}"></c:out>:</label>						
						<select	name="bikeTypeId"  class="form-control">						
							<c:if test="${bikeTypeId == null || bikeTypeId == 0}">
								<option selected value="0"><c:out value="${chooseBikeTypeLabel}"></c:out></option>
							</c:if>
							<c:if test="${bikeTypeId != null && bikeTypeId != 0}">
								<option value="0"><c:out value="${chooseBikeTypeLabel}"></c:out></option>
							</c:if>
							<c:forEach items="${bikeTypeList}" var="item">
								<option <c:if test="${bikeTypeId==item.id}">selected</c:if> value="${item.id}"><c:out value="${item.bikeType}"></c:out></option>
							</c:forEach> 
						</select>				
					</div>
				
					<div style="text-align: center; margin-top: 25px">
						<input type="hidden" name="command" value="find_bike_product" />	
						<input type="submit" class="btn btn-primary"   value=<c:out value="${findLabel}"></c:out>>		
					</div>
				</form>
			</div>
			
			<!-- --------------------------------search results ------------------------------------ -->
			<c:if test="${bikeProductList!=null}">	
				<div class="col-md-10" style="background: #F5F5DC">
					<div class="row" style="font-size: 20px; font-weight: bold; padding-top: 5px; padding-left: 5px; color: #000080	">
						<div class="col-md-1" style="padding-top:10px;  background: white">
							<label style="font-size: 16px;"><c:out value="${bikeNubmerLabel}"></c:out></label>
						</div>
						<div class="col-md-3" style="padding-top:15px; padding-left: 40px;  background: white">
							<label style="font-size: 16px;"><c:out value="${pictureLabel}"></c:out></label>
						</div>
						<div class="col-md-3" style="padding-top:15px; padding-left: 40px;  background: white">
							<label style="font-size: 16px;"><c:out value="${characteristicsLabel}"></c:out></label>
						</div>
						<div class="col-md-2" style="padding-top:15px;  background: white">
							<label style="font-size: 16px;"><c:out value="${parkingLabel}"></c:out></label>
						</div>
						<div class="col-md-1" style="padding-top:10px;  background: white">
							<label style="font-size: 16px;" ><c:out value="${amountOfCollateralLabel}"></c:out></label>
						</div>
						<div class="col-md-1" style="background: white">
							<label style="font-size: 16px;"><c:out value="${rentCostPerMinuteLabel}"></c:out></label>
						</div>
						<c:if test="${bikeProductCatalogWithChoise}">
							<div class="col-md-1" style="padding-top:10px; background: white">
								<label style="font-size: 16px;"><c:out value="${makeCoiceLabel}"></c:out></label>
							</div>
						</c:if>			
					</div>	
					
					<c:forEach items="${bikeProductList}" var="item">
						<div class="row" style="padding-top: 5px; padding-left: 5px">
							<div class="col-md-1"  style="padding-top: 5px; background: white">
								<div>
									<c:out value="${item.id}"></c:out>
								</div>
							</div>
							<div class="col-md-3" style="background: white">
								<c:if test="${item.bike.picturePath!=null}">
									<img src="${pageContext.request.contextPath}/images/bikes/${item.bike.picturePath}" 
										alt="${item.bike.model}" style= "height: 152px; border: none">
								</c:if>
							</div>
							<div class="col-md-3"  style="background: white">
								<div>
									<label style="font-size: 20px; font-weight: bold;">${item.bike.brand.brand} ${item.bike.model}</label>
								</div>
								<div>
									<c:out value="${item.bike.bikeType.bikeType}"></c:out>, 
									<c:out value="${wheelSizeLabel}"></c:out>: <c:out value="${item.bike.wheelSize}"></c:out>, 
									<c:out value="${speedCountLabel}"></c:out>:  <c:out value="${item.bike.speedCount}"></c:out>
								</div>
							</div>					
							<div class="col-md-2"  style="padding-top: 5px; background: white">
								<div>
									<c:out value="${item.parking.address}"></c:out>	
								</div>
							</div>					
							<div class="col-md-1"  style="padding-top: 5px; background: white">
								<div>
									<c:out value="${item.value}"></c:out>
								</div>
							</div>
							<div class="col-md-1"  style="padding-top: 5px; background: white">
								<div>
									<c:out value="${item.rentPrice}"></c:out>
								</div>
							</div>
							<c:if test="${bikeProductCatalogWithChoise}">										
								<div class="col-md-1"  style="padding-top: 5px; background: white">
									<div>
										<label><a href="FrontController?command=choose_bike_product&bikeProductId=${item.id}">${chooseLabel}</a></label>
									</div>
								</div>
							</c:if>					
						</div>
					
					</c:forEach> 
					
					<%@ include file="../../WEB-INF/jspf/pagination.jspf" %>
			
				</div>
			</c:if>
			
		</div>
	</div>	
	
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>		

</body>
</html>