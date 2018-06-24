if(document.getElementById('message')!=null){
	var message = document.getElementById('message').value;
	messageShow(message);
}

if(document.getElementById('errorMessage')!=null){
	var errorMessage = document.getElementById('errorMessage').value;
	errorMessageShow(errorMessage);
}

function errorMessageShow(errorMessage) {
	  var window = document.createElement('div');
	  window.style.position = 'fixed';
	  window.style.left = '35%';
	  window.style.top  = '20%';
	  window.style.zIndex = '500';
	  window.style.boxSizing = 'border-box';
	  window.style.background = 'rgba(255, 192, 203, 1)';
	  window.style.boxShadow  = '0 0 10px rgba(0, 0, 0, 0.5)';
	  window.style.padding = '40px 60px';
	  window.style.borderRadius = '7px';
	  window.style.width = '500px';
	  window.style.heght = '250px';
	 
	  window.innerHTML = '<div onclick="this.parentNode.parentNode.removeChild(this.parentNode)" style="position: absolute; zIndex: 510; box-Shadow: 0 0 10px rgba(0, 0, 0, 0.5); padding: 0px 20px; background-color: skyblue;  border-radius: 5px; top: 70%; right: 210px; cursor: pointer; font-size: 15px">OK</div><div style="text-align: center; margin-bottom: 25px">' + errorMessage + '</div>';    	  
	  document.body.appendChild(window);
	  
	  return false;
}

function messageShow(message) {
	  var window= document.createElement('div');
	  window.style.position = 'fixed';
	  window.style.left = '35%';
	  window.style.top  = '20%';
	  window.style.zIndex = '500';
	  window.style.boxSizing = 'border-box';
	  window.style.background = 'rgba(237, 255, 238, 1)';
	  window.style.boxShadow  = '0 0 10px rgba(0, 0, 0, 0.5)';
	  window.style.padding = '40px 60px';
	  window.style.borderRadius = '7px';
	  window.style.width = '500px';
	  window.style.heght = '250px';
	 
		  window.innerHTML = '<div onclick="this.parentNode.parentNode.removeChild(this.parentNode)" style="position: absolute; zIndex: 510; box-Shadow: 0 0 10px rgba(0, 0, 0, 0.5); padding: 0px 20px; background-color: skyblue;  border-radius: 5px; top: 70%; right: 210px; cursor: pointer; font-size: 15px">OK</div><div style="text-align: center; margin-bottom: 25px">' + message + '</div>';    	  
	  document.body.appendChild(window);
	  
	  return false;
}

