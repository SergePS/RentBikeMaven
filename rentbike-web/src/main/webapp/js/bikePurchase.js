function checkPurchaseForm() {
	
	if(isParkingNotSelected()){
		return false;
	}	
	if(isValueNotCorrect()){
		return false;
	}
	if(isRentPriceNotCorrect()){
		return false;
	}	
	if(isBikeNotSelected()){
		return false;
	}	
		document.getElementById("addBikeProductForm").submit();
}

function isParkingNotSelected(){
	if(document.getElementById("parkingId").value==0){
		errorMessageShow('<c:out value="${parkingNotSelectedWarn}"></c:out>');
		return true
	}else{
		return false
	}
}

function isValueNotCorrect(){
	if(document.getElementById("value").value==0){
		errorMessageShow('<c:out value="${valueZeroWarn}"></c:out>');
		return true
	}else{
		return false
	}
}

function isRentPriceNotCorrect(){
	if(document.getElementById("rentPrice").value==0){
		errorMessageShow('<c:out value="${rentPriceZeroWarn}"></c:out>');
		return true
	}else{
		return false
	}
}

function isBikeNotSelected(){
	if(document.getElementById("bikeId").value==0){
		errorMessageShow('<c:out value="${bikeNotSelected}"></c:out>');
		return true
	}else{
		return false
	}
}