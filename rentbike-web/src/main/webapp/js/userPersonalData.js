function isBirthdayNotEmpty(){
	var birthday = document.getElementById('birthday').value;

	if(birthday==""){
		errorMessageShow('<c:out value="${birthdayEmptyLabel}"></c:out>');
		return false;
	}else{
		return true;
	}	
}