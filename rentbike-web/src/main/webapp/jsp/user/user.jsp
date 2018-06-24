<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>

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
	
	<!-- ------------------------------ menu ----------------------------------- -->
	<nav>	
		<ul>
			<li class="logo">RentBike</li>
			
			<c:if test="${bikeOrder==null}">
				<li><a href="FrontController?command=go_to_order_page"><c:out value="${createOrderLabel}"></c:out></a></li>
			</c:if>
			
			<li><a href="FrontController?command=find_user_orders"><c:out value="${ordersLabel}"></c:out></a></li>
			
			<li style="float: right; margin-right: 50px"><a href="#"><c:out value="${user.name}"></c:out></a>
				<ul>
					<li><a href="#" onclick="showProfile()"><c:out value="${profileLabel}"></c:out></a></li>
					<li><a href="#" onclick="showPassword()"><c:out value="${passwChangeLabel}"></c:out></a></li>
					<li><a href="FrontController?command=logout"><c:out value="${logoutLabel}"></c:out></a></li>
				</ul>
			</li>
			<li style="float: right; color: white; padding: 18px 0px 15px 10px;"><c:out value="${userLabel}"></c:out></li>
		</ul>
	</nav>
	<!-- --------------------------end of menu -------------------------------- -->
	
	
	<!-- ---------------------------edit user --------------------------------- -->
	<div id="calend" style="margin-top: 20 px">
		<%@ include file="../../WEB-INF/jspf/editUser.jspf"%>
	</div>
	<!-- ------------------------ end of edit user ---------------------------- -->
	
	
	<div id="body">
		<!-- --------------------------- user order ------------------------------- -->
		<c:if test="${bikeOrder!=null}">
			<form action="FrontController" method="post" id="bikeOrder" onsubmit="checkParkingChoice(); return false">
				<div class="container" style="margin-top: 15px">
					<div align="center" style="border-bottom: 1px solid blue; font-size: 25px; padding-bottom: 5px">
						<c:out value="${currentOrderLabel}"></c:out>:				
					</div>
					<div class="row" style="padding-top: 5px; padding-left: 5px">
						<div class="col-md-6"  style="padding-top: 5px; margin-left: 10px; background: white">
							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${bikeNubmerLabel}"></c:out>:
								</div>
								<div class="col-md-6">
									<c:out value="${bikeOrder.bikeProductId}"></c:out>
								</div>							
							</div>
							
							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${brandLabel}"></c:out> <c:out value="${modelLabel}"></c:out>:
								</div>
								<div class="col-md-6">
									<c:out value="${bikeOrder.bike.brand.brand}"></c:out> <c:out value="${bikeOrder.bike.model}"></c:out>
								</div>							
							</div>	
			
							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${characteristicsLabel}"></c:out>:
								</div>
								<div class="col-md-6">
									<c:out value="${bikeOrder.bike.bikeType.bikeType}"></c:out>, 
									<c:out value="${wheelSizeLabel}"></c:out>: <c:out value="${bikeOrder.bike.wheelSize}"></c:out>, 
									<c:out value="${speedCountLabel}"></c:out>:  <c:out value="${bikeOrder.bike.speedCount}"></c:out>
								</div>							
							</div>
			
							<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
								<div class="col-md-6">
									<c:out value="${startParkingLabel}"></c:out>:
								</div>
								<div class="col-md-6">
										<c:out value="${bikeOrder.startParking.address}"></c:out>							
								</div>							
							</div>
			
						</div>
			
						<div class="col-md-3" style="background: white; padding-left: 100px">
							<c:if test="${bikeOrder.bike.picturePath!=null}">
								<img src="${pageContext.request.contextPath}/images/bikes/${bikeOrder.bike.picturePath}" alt="${bikeOrder.bike.model}" style= "width: 375px; height: 228px; border: none">
							</c:if>
						</div>				
					</div>
					
					
					<div align="center" style="border-bottom: 1px solid gray; font-size: 20px; margin-top:20px">
						<c:out value="${timeOfOrderLabel}"></c:out>:					
					</div>	
								
					<div class="row" style="margin-top:10px">
					
					
						<div class="col-md-2"></div>	
							<div class="col-md-2" style="font-size: 20px">
								<label style="float:left"><c:out value="${daysLabel}"></c:out>: </label>
								<label style="float:left;" id="days">0</label>
							</div>
							
							<div class="col-md-2" style="font-size: 20px">
								<label style="float:left;"><c:out value="${hoursLabel}"></c:out>: </label>
								<label style="float:left;" id="hours">0</label>
							</div>
							
							<div class="col-md-2" style="font-size: 20px">
								<label style="float:left;"><c:out value="${minutesLabel}"></c:out>: </label>
								<label style="float:left;" id="minutes">0</label>
							</div>
							
							<div class="col-md-2" style="font-size: 20px">
								<label style="float:left;"><c:out value="${secondsLabel}"></c:out>: </label>
								<label style="float:left;" id="seconds">0</label>
							</div>			
					</div>	
					
					<div align="center" style="border-bottom: 1px solid gray; font-size: 20px; margin-top:20px">
						<c:out value="${closeOrderLabel}"></c:out>:					
					</div>	
				
					<div class="row" style="margin-top:10px">
						<div class="col-md-1"></div>
						<div class="col-md-3"><label style="margin-left:10px"><c:out value="${returnParkingLabel}"></c:out>:</label></div>
						<div class="col-md-4">
							<select	name="finishParkingId"  class="form-control" id="finishParkingId">
								<option value="0"><c:out value="${chooseParkingLabel}"></c:out></option>
								<c:forEach items="${parkingList}" var="item">
									<option value="${item.id}"><c:out value="${item.address}"></c:out></option>
								</c:forEach> 
							</select>				
						</div>
					</div>	
					
				</div>
		
				<div align="center" style="margin-top:20px">	
					<input type="hidden" name="command" value="close_order"/>
					<input type="hidden" name="orderId" value="${bikeOrder.id}"/>	
					<input type="hidden" name="startTime" value="${bikeOrder.startTime}"/>
					<input type="hidden" name="rentPrice" value="${bikeOrder.rentPrice}"/>					
					<input type="submit" class="btn btn-primary" style="background-color: green" value="${returnBikeLabel}">
				</div>
			</form>
		</c:if>
		<!-- ------------------------- end of user order ------------------------------- -->
		
		
		<!-- -------------------------- bike order list -------------------------------- -->
		<c:if test="${bikeOrderList!=null}">
			<div style="text-align:center; margin-bottom:10px; font-size: 28px">
				<c:out value="${ordersLabel}">:</c:out>
			</div>
			
			<div style="margin-left: 20px">
				<div class="row">
					<div class="col-md-12">
						<table class="table">
							<thead>
								<tr>
									<th><c:out value="${orderNumberLabel}"></c:out></th>
									<th style="width: 200px"><c:out value="${bikeLabel}"></c:out></th>
									<th style="width: 300px"><c:out value="${startParkingLabel}"></c:out></th>
									<th style="width: 300px"><c:out value="${finishParkingLabel}"></c:out></th>								
									<th><c:out value="${startTimeLabel}"></c:out></th>
									<th><c:out value="${finishTimeLabel}"></c:out></th>
									<th style="width: 150px"><c:out value="${rentCostPerMinuteLabel}"></c:out></th>
									<th><c:out value="${minutesLabel}"></c:out></th>
									<th><c:out value="${amountLabel}"></c:out></th>
								</tr>
							</thead>
							<c:forEach items="${bikeOrderList}" var="item">
								<tbody>
									<tr>
										<td><c:out value="${item.id}"></c:out></td>	
										<td><c:out value="${item.bike.brand.brand}"></c:out>, <c:out value="${item.bike.model}"></c:out></td>
										<td><c:out value="${item.startParking.address}"></c:out></td>
										<td><c:out value="${item.finishParking.address}"></c:out></td>									
										<td><c:out value="${item.startTime}"></c:out></td>
										<td><c:out value="${item.finishTime}"></c:out></td>
										<td><c:out value="${item.rentPrice}"></c:out></td>
										<td><ctg:calculateTime finishTime="${item.finishTime}" startTime="${item.startTime}"/></td>
										<td><c:out value="${item.payment}"></c:out></td>												
									</tr>									
								</tbody>
							</c:forEach>		
						</table>
					</div>		
				</div>
			</div>
		</c:if>
	</div>
	<!-- ------------------------ end of bike order list ------------------------------ -->
	
	
	<input type="hidden" id="secondsInputParam" value="${seconds}">
	<input type="hidden" id="minutesInputParam" value="${minutes}">
	<input type="hidden" id="hoursInputParam" value="${hours}">
	<input type="hidden" id="daysInputParam" value="${days}">
	<input type="hidden" id="orderUser" value="${message}">
		
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>
	
    <script type="text/javascript">	      		
    	showOrderTime();
    	<%@include file="/js/user.js"%> 
	</script>
</body>
</html>