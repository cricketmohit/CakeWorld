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


function imageZoom(imgID, resultID,productPageBodyId,productPageBodyId1,productPageBodyId2,productPageBodyId3) {
	  var img, lens, result, cx, cy;
	  img = document.getElementById(imgID);
	  result = document.getElementById(resultID);
	  /*create lens:*/
	  lens = document.createElement("DIV");
	  lens.setAttribute("class", "img-zoom-lens");
	  /*insert lens:*/
	  img.parentElement.insertBefore(lens, img);
	  /*calculate the ratio between result DIV and lens:*/
	  cx = result.offsetWidth / lens.offsetWidth;
	  cy = result.offsetHeight / lens.offsetHeight;
	  /*set background properties for the result DIV:*/
	  result.style.backgroundImage = "url('" + img.src + "')";
	  result.style.backgroundSize = (img.width * cx) + "px " + (img.height * cy) + "px";
	  
	  lens.setAttribute("style", "display:none");
	 
	  result.style.backgroundImage = "";
	  var productPage=document.getElementById(productPageBodyId);
	  productPage.addEventListener("mousemove", movePage);
	  productPage.addEventListener("touchmove", movePage);
	  var productPage1=document.getElementById(productPageBodyId1);
	  productPage1.addEventListener("mousemove", movePage1);
	  productPage1.addEventListener("touchmove", movePage1);
	  
	  var productPage2=document.getElementById(productPageBodyId1);
	  productPage2.addEventListener("mousemove", movePage1);
	  productPage2.addEventListener("touchmove", movePage1);
	  
	  var productPage3=document.getElementById(productPageBodyId3);
	  productPage3.addEventListener("mousemove", movePage1);
	  productPage3.addEventListener("touchmove", movePage1);
	  
	  /*execute a function when someone moves the cursor over the image, or the lens:*/
	  lens.addEventListener("mousemove", moveLens);
	  img.addEventListener("mousemove", moveLens);
	  /*and also for touch screens:*/
	  lens.addEventListener("touchmove", moveLens);
	  img.addEventListener("touchmove", moveLens);
	  function moveLens(e) {
		  result.style.backgroundImage = "url('" + img.src + "')";
		  lens.setAttribute("style", "display:block");
		  result.style.display = "block";
		
	    var pos, x, y;
	    /*prevent any other actions that may occur when moving over the image:*/
	    e.preventDefault();
	    /*get the cursor's x and y positions:*/
	    pos = getCursorPos(e);
	    /*calculate the position of the lens:*/
	    x = pos.x - (lens.offsetWidth / 2);
	    y = pos.y - (lens.offsetHeight / 2);
	    /*prevent the lens from being positioned outside the image:*/
	    if (x > img.width - lens.offsetWidth) {x = img.width - lens.offsetWidth;}
	    if (x < 0) {x = 0;}
	    if (y > img.height - lens.offsetHeight) {y = img.height - lens.offsetHeight;}
	    if (y < 0) {y = 0;}
	    /*set the position of the lens:*/
	    lens.style.left = x + "px";
	    lens.style.top = y + "px";
	    /*display what the lens "sees":*/
	    result.style.backgroundPosition = "-" + (x * cx) + "px -" + (y * cy) + "px";
	    result.style.display = "block";
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
	  
	  function movePage(e) {
		  
		  lens.setAttribute("style", "display:none");
			 
		  result.style.backgroundImage = "";
		 
	  }
 function movePage1(e) {
		  
	 lens.setAttribute("style", "display:none");
	 
	  result.style.backgroundImage = "";
		 
	  }
  
  
}

