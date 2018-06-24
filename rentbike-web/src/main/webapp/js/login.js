function initLogin(){
	isLogin = document.getElementById('isLoginElement').value;
	if(isLogin == "" || isLogin == "true"){
		isLogin = true;
	}else{
		isLogin = false;
	}
	
	if(isLogin){
		document.getElementById('authorization').style.display = 'block';
	}else{
		document.getElementById('registration').style.display = 'block';
		document.getElementById('choose_type').text='${authorizationLabel}';
	}
	
	return isLogin;
		
}


function showFunc() {
	if(isLogin) {
		document.getElementById('authorization').style.display = 'none';
		document.getElementById('registration').style.display = 'block';
		document.getElementById('choose_type').text='${authorizationLabel}';
		isLogin = false;
	} else {
		document.getElementById('authorization').style.display = 'block';
		document.getElementById('registration').style.display = 'none';
		document.getElementById('choose_type').text='${registrationLabel}';
		isLogin = true;
		}
}


function checkUserData(){
	if(isPasswordEqual() && isBirthdayNotEmpty()){
		document.getElementById("userDataForm").submit();
	}	
}