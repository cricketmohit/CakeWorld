function increaseItem(id)
{
	var tempId = id;
	$.ajax({
	    type : "POST",
	    url : "addToCart",
	    data : {id:tempId},
	    timeout : 100000,
	    success : function(id) {
	        console.log("SUCCESS: ", id);
	        display(id);
	    },
	    error : function(e) {
	        console.log("ERROR: ", e);
	        return "Please Re-try";
	    }
	   
	});
	
	 var countCookie = getCookie("cookiecartcounts");
	 if(countCookie=="0"){
		 countCookie=id;
	 }else{
		 countCookie=countCookie+"*"+id;
	 }

	 setCookie("cookiecartcounts", countCookie,365);


}


function removeItem(id)
{
	var tempId = id;
	$.ajax({
	    type : "POST",
	    url : "substractFromCart",
	    data : {id:tempId},
	    timeout : 100000,
	    success : function(id) {
	        console.log("SUCCESS: ", id);
	        display(id);
	    },
	    error : function(e) {
	        console.log("ERROR: ", e);
	        return "Please Re-try";
	    }
	   
	});
	
	 var countCookie = getCookie("cookiecartcounts");
	 if(countCookie=="0"){
		 return"";
		 
	 }else{
		 	var newCookie = countCookie.replace('*'+id+'*', '*');
			 if(countCookie===(newCookie)){
				 newCookie = countCookie.replace('*'+id, '');
			 }
			 if(countCookie===(newCookie)){
				 newCookie = countCookie.replace(id+'*', '');
			 }
			 if(countCookie===(newCookie)){
				 var r = confirm("This will remove the item from the cart and take you to home page ");
				    if (r == true) {
				    	 newCookie = countCookie.replace(id, '');
				    }
				
			 }
	 }

	 setCookie("cookiecartcounts", newCookie,365);
	
	
}

function payment()
{
	
	

}
