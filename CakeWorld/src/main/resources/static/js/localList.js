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


function imageZoom(imgID, resultID) {
  var img, lens, result, cx, cy;
  img = document.getElementById(imgID);
  
  result = document.getElementById(resultID);
  /*create lens:*/
  lens = document.createElement("DIV");
  lens.setAttribute("class", "img-zoom-lens");
  lens.setAttribute("style", "display:none");
  /*insert lens:*/
  img.parentElement.insertBefore(lens, img);
  /*calculate the ratio between result DIV and lens:*/
  cx = result.offsetWidth / lens.offsetWidth;
  cy = result.offsetHeight / lens.offsetHeight;
  /*set background properties for the result DIV:*/
  result.style.backgroundImage = "url('" + img.src + "')";
  result.style.backgroundSize = (img.width * cx) + "px " + (img.height * cy) + "px";
  /*execute a function when someone moves the cursor over the image, or the lens:*/
  lens.addEventListener("mousemove", moveLens);
  img.addEventListener("mousemove", moveLens);
  /*and also for touch screens:*/
  lens.addEventListener("touchmove", moveLens);
  img.addEventListener("touchmove", moveLens);
  function moveLens(e) {
	  lens.setAttribute("style", "display:block");
	  result.setAttribute("style", "display:block");
    var pos, x, y,p,q;
    /*prevent any other actions that may occur when moving over the image:*/
    e.preventDefault();
    /*get the cursor's x and y positions:*/
    pos = getCursorPos(e);
    
    /*calculate the position of the lens:*/
    x = pos.x - (lens.offsetWidth / 2);
    y = pos.y - (lens.offsetHeight / 2);
    /*prevent the lens from being positioned outside the image:*/
    p=x;
    q=y;
    if (x > img.width - lens.offsetWidth) {x = img.width - lens.offsetWidth;} 
    if (x < 0) {x = 0;}
    if (y > img.height - lens.offsetHeight) {y = img.height - lens.offsetHeight;}
    if (y < 0) {y = 0;}

    if(p - (lens.offsetWidth / 2) + 10 > img.width - lens.offsetWidth){
    	 lens.setAttribute("style", "display:none");
    	 result.setAttribute("style", "display:none");
    }
   
    if(q - (lens.offsetHeight / 2) + 10 > img.height - lens.offsetWidth){
   	 lens.setAttribute("style", "display:none");
   	 result.setAttribute("style", "display:none");
    }
   
 	if(q + (lens.offsetHeight / 2) - 10 < 0){
     	 lens.setAttribute("style", "display:none");
     	 result.setAttribute("style", "display:none");
   	
   }
    /*set the position of the lens:*/
    lens.style.left = x + "px";
    lens.style.top = y + "px";
    
    /*display what the lens "sees":*/
    result.style.backgroundPosition = "-" + (x * cx) + "px -" + (y * cy) + "px";
    
  }
  function getCursorPos(e) {
	    var a, x = 0, y = 0;
	    e = e || window.event;
	    /*get the x and y positions of the image:*/
	    a = img.getBoundingClientRect();
	    /*calculate the cursor's x and y coordinates, relative to the image:*/
	    
	    x = e.pageX - a.left;
	    y = e.pageY - a.top;
	  
	    /*consider any page scrolling:*/
	    x = x - window.pageXOffset;
	    y = y - window.pageYOffset;
	    return {x : x, y : y};
	  }
 
  function getMouseCoords(event) {
      var x = event.clientX;
      var y = event.clientY;
      var img = document.getElementById(imgID);
      alert(img.width);
     
  }
  
}

