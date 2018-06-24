function showOrderTime()
  {
  	timePrint = '';
	sec = document.getElementById("secondsInputParam").value;
	secPrint = '';
	min = document.getElementById("minutesInputParam").value;
	minPrint = '';
	hr = document.getElementById("hoursInputParam").value;
	hrPrint = '';
	day = document.getElementById("daysInputParam").value;
	dayPrint = '';
    setInterval(tick, 1000);
}
  
function tick(){
    sec++;
    if(sec > 59){
    	sec = 0;
    	min++;
    	if(min>59){
    		min = 0;
    		hr++;
    		if(hr>23){
    			hr = 0;
    			day++;
    		}
    	}
 	}
      
    secPrint = sec < 10 ? '0' + sec : sec;
    minPrint = min < 10 ? '0' + min : min;
    hrPrint = hr < 10 ? '0' + hr : hr;

    document.getElementById("seconds").childNodes[0].nodeValue = secPrint;
    document.getElementById("minutes").childNodes[0].nodeValue = minPrint;
    document.getElementById("hours").childNodes[0].nodeValue = hrPrint;
    document.getElementById("days").childNodes[0].nodeValue = day;    
}

function checkParkingChoice(){
	if(document.getElementById("finishParkingId").value==0){
		errorMessageShow('<c:out value="${parkingNotSelectedWarn}"></c:out>');
		return false;
	}else{
		document.getElementById("bikeOrder").submit();
	}
}