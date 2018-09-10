
function setCookie(cname, cvalue, exdays) {
	    var d = new Date();
	    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	    var expires = "expires="+d.toUTCString();
	    document.cookie = cname + "=" + cvalue + ";" + expires ;
	}

function getCookie(cname) {
		var name = cname+ "=";
	    var ca = document.cookie.split(';');
	    for(var i = 0; i < ca.length; i++) {
	    		
	        var c = ca[i];
	        while (c.charAt(0) == ' ') {
	            c = c.substring(1);
	        }
	        if (c.indexOf(name) == 0) {
	        		
	            return c.substring(name.length, c.length);
	        }
	    }
	   
	    return "0";
	}


function displayCart(){
	var cartCookie = getCookie("cookiecartcounts");
	if(cartCookie=="0" || cartCookie==""){
		document.getElementById("cartcount").innerHTML = 0;
		return"";
		}
	var menuInCartArray = cartCookie.split('*');
	document.getElementById("cartcount").innerHTML = menuInCartArray.length;
	
}


function displayDelievery(){
	
	
	var cartCookie = getCookie("cookiecartcounts");
	
	if(cartCookie=="0"){
		document.getElementById("cartcount").innerHTML = cartCookie.toString();
		return"";
		}
	var menuInCartArray = cartCookie.split('*');
	document.getElementById("totalCount").innerHTML = menuInCartArray.length;
	
}

function addToCart(id)
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
	
	var modal = document.getElementById('myModal');

	var btn = document.getElementById("addtocart");

	
	var span = document.getElementsByClassName("close")[0];
	
	 modal.style.display = "block";
	 span.onclick = function() {
		    modal.style.display = "none";
		}
		
		window.onclick = function(event) {
		    if (event.target == modal) {
		        modal.style.display = "none";
		    }
		}
		
	 var countCookie = getCookie("cookiecartcounts");
	 if(countCookie=="0" || countCookie==""){
		 countCookie=id;
	 }else{
		 countCookie=countCookie+"*"+id;
	 }
	var menuInCartArray = countCookie.split('*');
	 setCookie("cookiecartcounts", countCookie,365);
	 document.getElementById("cartcount").innerHTML = menuInCartArray.length;
	
	
	 
	 
}