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
		<%@include file="/css/labelStyle.css"%>
	</style>
	
	<%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>
	
	<title><c:out value="${title}"></c:out></title>

</head>
<body>

	<c:set var="menuLabel" value="${bikePurchaseLabel}" scope="page"/>
	<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>
	
<br>	
	<div class="container">
		<form action="FrontController" method="post" id="addBikeProductForm" onsubmit="checkPurchaseForm();return false">
		
			<!-- ----------------------------------select or replace bike link ----------------------------------------- -->
			<div class="row">
				<div class="col-md-4"><label><c:out value="${bikeLabel}"></c:out>:</label></div>
				<div class="col-md-4">
					<c:if test="${bike!=null}">
						<label style="float: left"><a href="FrontController?command=bikeCatalog&bikeCatalogWithChoise=true"><c:out value="${bike.brand.brand}"></c:out> <c:out value="${bike.model}"></c:out></a></label>
						<input type="hidden" name="bikeId" id="bikeId" value="${bike.id}"/>
					</c:if>
					<c:if test="${bike==null}">
						<input type="hidden" name="bikeId" id="bikeId" value="0"/>
						<label style="float: left"><a href="FrontController?command=bikeCatalog&bikeCatalogWithChoise=true"><c:out value="${chooseLabel}"></c:out></a></label>
					</c:if>
				</div>
			</div>
			
			<!-- -----------------------------------------select parking -------------------------------------------- -->
			<div class="row">
				<div class="col-md-4"><label><c:out value="${parkingLabel}">:</c:out></label></div>
				<div class="col-md-4">
						<select	name="parkingId" id="parkingId" class="form-control">
						<c:if test="${parkingId==null}">
							<option selected value="0"><c:out value="${chooseParkingLabel}">:</c:out></option>
						</c:if>
						<c:forEach items="${parkingList}" var="item">
							<option  <c:if test="${item.id eq parkingId}">selected</c:if>  value="${item.id}"><c:out value="${item.address}">:</c:out></option>
						</c:forEach> 
					</select>			
				</div>
			</div>
			
			<!-- -----------------------------------------input bike count ---------------------------------------- -->
			<div class="row">
				<div class="col-md-4">
					<label><c:out value="${countLabel}">:</c:out></label>
				</div>
				<div class="col-md-4">
					<input 
						type="text"
						name="bikeCount"
						class="form-control"
						placeholder="${pcLabel}"
						required
						value = '<c:out value="${bikeCount}"></c:out>'
						pattern="[1-9]"
						oninvalid="setCustomValidity('${countWarnLabel}')"
						oninput="setCustomValidity('')">
				</div>
			</div>
			
			<!-- -----------------------------------------input bike price ---------------------------------------- -->
			<div class="row">
				<div class="col-md-4"><label><c:out value="${bikePriceLabel}">:</c:out></label></div>
				<div class="col-md-4">
					<input 
						type="text"
						id="value"
						name="value"
						class="form-control"
						placeholder="${enterAmountLabel}"
						required
						value = '<c:out value="${value}"></c:out>'
						pattern="(\d{1}|[1-9]{1}\d{1,5})((\.|,){1}\d{1,2})?"
						oninvalid="setCustomValidity('${amountWarnLabel}')"
						oninput="setCustomValidity('')">
				</div>
			</div>		
			
			<!-- -----------------------------------------input rent bike---------------------------------------- -->
			<div class="row">
				<div class="col-md-4"><label><c:out value="${rentPriceLabel}">:</c:out></label></div>
				<div class="col-md-4">
					<input 
						type="text"
						name="rentPrice"
						id="rentPrice"
						class="form-control"
						placeholder="${enterAmountLabel}"
						value = '<c:out value="${rentPrice}"></c:out>'
						required
						pattern="(\d{1}|[1-9]{1}\d{1,5})((\.|,){1}\d{1,2})?"
						oninvalid="setCustomValidity('${amountWarnLabel}')"
						oninput="setCustomValidity('')">
				</div>
			</div>
			
			<!-- -----------------------------------------------submit-------------------------------------------- -->
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4" align="center">
					<input type="hidden" name="command" value="add_bike_product"/>
					<input type="submit" class="btn btn-primary" value="${addLabel}" onclick="sn()">
				</div>
			</div>
			</form>				
	</div>
	
	<!-- -----------------------------------------------result of purchase ----------------------------------------- -->
	<c:if test="${bikeProductList!=null}">
		<div class="container">
			<div class="row" style="margin-top: 25px">
				<div class="col-md-12">
					<c:out value="${bikeHaveBeenAccruedLabel}"></c:out>
					<table class="table">
						<thead>
							<tr>
								<th><c:out value="${bikeNubmerLabel}"></c:out>:</th>
								<th><c:out value="${brandLabel}"></c:out> <c:out value="${modelLabel}"></c:out>:</th>
								<th><c:out value="${parkingLabel}"></c:out>:</th>
								<th><c:out value="${bikePriceLabel}"></c:out>:</th>
								<th><c:out value="${rentCostPerMinuteLabel}"></c:out>:</th>
							</tr>
						</thead>
						<c:forEach items="${bikeProductList}" var="item">
							<tbody>
								<tr>
									<td style="width: 100px"><c:out value="${item.id}"></c:out></td>
									<td style="width: 500px"><c:out value="${item.bike.brand.brand}"></c:out> <c:out value="${item.bike.model}"></c:out></td>
									<td style="width: 800px"><c:out value="${item.parking.address}"></c:out></td>
									<td style="width: 200px"><c:out value="${item.value}"></c:out></td>
									<td style="width: 200px"><c:out value="${item.rentPrice}"></c:out></td>					
								</tr>									
							</tbody>
						</c:forEach>		
					</table>
				</div>		
			</div>
		</div>
		<c:remove var="bikeProductList" scope="session" />		
	</c:if>
	
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>	

	<script>
		<%@include file="/js/bikePurchase.js"%>
	</script>

</body>
</html>