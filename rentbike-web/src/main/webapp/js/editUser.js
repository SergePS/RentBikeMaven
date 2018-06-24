function showProfile() {
	if(document.getElementById('profile').style.display = (document.getElementById('profile').style.display == 'block')){
		document.getElementById('profile').style.display = 'none';
		document.getElementById('body').style.display = 'block';
	}else{
		document.getElementById('profile').style.display = 'block';
		document.getElementById('password').style.display = 'none';
		document.getElementById('body').style.display = 'none';
	}
}
function showPassword() {
	if(document.getElementById('password').style.display = (document.getElementById('password').style.display == 'block')){
		document.getElementById('password').style.display = 'none';
		document.getElementById('body').style.display = 'block';
	}else{
		document.getElementById('password').style.display = 'block';
		document.getElementById('currentPassword').style.display = 'block';
		document.getElementById('profile').style.display = 'none';
		document.getElementById('body').style.display = 'none';
	}
}

function checkPassword(){
	if(isPasswordEqual()){
		document.getElementById("passwUpdateForm").submit();
	}	
}

function checkBirthday(){
	if(isBirthdayNotEmpty()){
		document.getElementById("userUpdateForm").submit();
	}	
}