function checkSelectForm(){
	var brandId = document.getElementById('brandId').value;
	var bikeTypeId = document.getElementById('bikeTypeId').value;
	
	if(brandId>0 && bikeTypeId>0){
		document.getElementById("addBikeForm").submit();
	}else{
		errorMessageShow('<c:out value="${selectBikeErrorLabel}"></c:out>');
	}			
	
}

function getBrandValue(href){	
	var model = document.getElementById("model");
	var wheelSize = document.getElementById("wheelSize");
	var speedCount = document.getElementById("speedCount");
	var res = (href + "&model=" + model.value + "&wheelSize=" + wheelSize.value + "&speedCount=" + speedCount.value + "&isAddForm=true");
	return res;
}


function handleFileSelect(evt) {
    var file = evt.target.files;
    var f = file[0];
    // Only process image files.
    if (!f.type.match('image.*')) {
        alert("Image only please....");
    }else{
	    var reader = new FileReader();
	    reader.onload = (function(theFile) {
	        return function(e) {
	            document.getElementById('output').innerHTML = ['<img class="thumb" title="', escape(theFile.name), '"height="152" src="', e.target.result, '" />'].join('');
	        };
	    })(f);
	    // Read in the image file as a data URL.
	    reader.readAsDataURL(f);
    }

}
document.getElementById('file').addEventListener('change', handleFileSelect, false);