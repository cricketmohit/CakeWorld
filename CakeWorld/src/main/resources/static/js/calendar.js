function setDateLimit(){
	
	var today = new Date();
	
	
	var dd = today.getDate()+1;
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	 if(dd<10){
	        dd='0'+dd
	    } 
	    if(mm<10){
	        mm='0'+mm
	    } 

	var min = yyyy+'-'+mm+'-'+dd;
	mm=today.getMonth()+3;
	var max = yyyy+'-'+mm+'-'+dd;
	dd = today.getDate();
	document.getElementById("deliveryDate").setAttribute("min",min);
	document.getElementById("deliveryDate").setAttribute("max",max);
}

