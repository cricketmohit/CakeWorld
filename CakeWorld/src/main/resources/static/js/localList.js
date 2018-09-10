function increaseItem(id)
{
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
