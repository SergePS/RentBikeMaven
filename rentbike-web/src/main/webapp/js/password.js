function isPasswordEqual(){
	var passw1 = document.getElementById('passw1').value;
	var passw2 = document.getElementById('passw2').value;
	
	if(passw1!=passw2){
		errorMessageShow('<c:out value="${passwordNotMatchLabel}"></c:out>');
		return false;
	}else{
		return true;
	}	
}