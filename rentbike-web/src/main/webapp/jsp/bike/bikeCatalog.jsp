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
	
	<c:set var="menuLabel" value="${bikeCatalogLabel}" scope="page"/>
	<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>
	
	<div style="padding-left: 15px; padding-right: 15px">
		<div class="row">
			<div class="col-md-2" style="background: #F0FFF0; padding-left: 10px">
				<br>
				<form action="FrontController" method="post">
					
					<!--  ------------------------------- add new bike label ----------------------------------- -->
					<div style="text-align: center">
						<a href="FrontController?command=go_to_add_bike_page"><label class="downloadLabel"><c:out value="${addBikeLabel}"></c:out></label></a>
					</div>
					
					<!--  ------------------------------- choose brand ------------------------------------------ -->
					<div style="margin-top: 10px">
						<label style="margin-left:10px"><c:out value="${brandLabel}">:</c:out></label>
						<select name="brandId"  class="form-control">
							<c:if test="${brandId==null || brandId == 0}">
								<option selected value="0"><c:out value="${chooseBarndLabel}"></c:out></option>
							</c:if>
							<c:if test="${brandId != null && brandId != '0'}">
								<option value="0"><c:out value="${chooseBarndLabel}"></c:out></option>
							</c:if>
							<c:forEach items="${brandList}" var="item">
								<option <c:if test="${brandId==item.id}">selected</c:if> value=<c:out value="${item.id}"></c:out>><c:out value="${item.brand}"></c:out></option>
							</c:forEach>
						</select>				
					</div>
					<br>
					
					<!--  ------------------------------- model input ------------------------------------------ -->
					<div>
						<label style="margin-left:10px"><c:out value="${modelLabel}">:</c:out></label>
						<input 	type="text"	
							name="model"
							class="form-control"
							value="${model}"
							placeholder="${modelPlaceholder}"
							pattern="[\w\-\s\.\dа-яА-ЯёЁ]{1,30}"
							oninvalid="setCustomValidity('${modelWarnLabel}')"
							oninput="setCustomValidity('')">					
					</div>
					<br>
					
					<!--  ------------------------------- choose bike type --------------------------------------- -->
					<div>
						<label style="margin-left:10px"><c:out value="${bikeTypeLabel}">:</c:out></label>
						<select name="bikeTypeId"  class="form-control">
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
					<br>
					
					<!--  ----------------------------- input min and max speedCount  ---------------------------- -->
					<div>
						<div class="row" style="margin-left:10px">
							<label><c:out value="${speedCountLabel}">:</c:out></label>
						</div>
						<div class="row" style="margin-top:3px">
							<div class="col-md-6">						
								<input 	type="text" 
									name="minSpeedCount"
									class="form-control" 
									value="${minSpeedCount}"
									placeholder="${fromLabel}"
									pattern="[1-9]{1}\d{0,1}"
									oninvalid="setCustomValidity('${speedCountWarn}')"
									oninput="setCustomValidity('')">
							</div>
							
							<div class="col-md-6">
								<input 	type="text" 
									name="maxSpeedCount"
									class="form-control" 
									value="${maxSpeedCount}"
									placeholder="${toLabel}"
									pattern="[1-9]{1}\d{0,1}"
									oninvalid="setCustomValidity('${speedCountWarn}')"
									oninput="setCustomValidity('')">
							</div>
						</div>				
					</div>
					<br>
					<br>
				
					<div style="text-align: center">
						<input type="hidden" name="command" value="find_bike" />	
						<input type="submit" class="btn btn-primary"   value="${findLabel}">		
					</div>
				</form>
			</div>
			
			<!--  --------------------------------- founded bikes ------------------------------------------ -->
			<div class="col-md-1"></div>		
			<div class="col-md-9">	
				<c:forEach items="${bikeList}" var="item">
					<div class="row" style="padding-top: 5px; padding-left: 5px">
						<div class="col-md-3" style="border-bottom: 1px solid gray; margin-top: 15px">
							<c:if test="${item.picturePath!=null}">
								<img src="${pageContext.request.contextPath}/images/bikes/${item.picturePath}" 
									alt=<c:out value="${item.model}"></c:out> style= "height: 152px; border: none">
							</c:if>
						</div>
						<div class="col-md-4"  style="border-bottom: 1px solid gray; margin-top: 10px">
							<div>
								<label style="font-size: 20px; font-weight: bold;">${item.brand.brand} ${item.model}</label>
							</div>
							<div>
								<c:out value="${item.bikeType.bikeType}"></c:out>
							</div>
							<div>
								<c:out value="${wheelSizeLabel}"></c:out>: 
								<c:out value="${item.wheelSize}"></c:out>
							</div>
							<div>
								<c:out value="${speedCountLabel}"></c:out>:  
								<c:out value="${item.speedCount}"></c:out>
							</div>
						</div>
						<div class="col-md-1"  style="padding-top: 5px; border-bottom: 1px solid gray; margin-top: 10px">
							<div>
								<a href="FrontController?command=go_to_add_bike_page&bikeId=${item.id}"><c:out value="${changeLabel}"></c:out></a>
							</div>
						</div>	
						<c:if test="${bikeCatalogWithChoise}">
							<div class="col-md-1"  style="padding-top: 5px; border-bottom: 1px solid gray; margin-top: 10px">
								<div>
									<a href="FrontController?command=choose_bike&bikeId=${item.id}"><c:out value="${chooseLabel}"></c:out></a>
								</div>
							</div>
						</c:if>					
					</div>
				</c:forEach>
				
				<%@ include file="../../WEB-INF/jspf/pagination.jspf" %>
				
			</div>
		</div>
	</div>
		
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>			

</body>
</html>