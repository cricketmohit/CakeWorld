package com.cakeworld.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cakeworld.main.MenuRepository;
import com.cakeworld.model.Menu;

@Controller
public class CartController {
	
	@Autowired
	MenuRepository menuRepository;
	
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST) 
    public String addToCart(@ModelAttribute("cart") 
    		Menu menuToAdd,HttpSession session, HttpServletResponse response,
    		@CookieValue(value="cart" , defaultValue = "0") String fooCookie, @CookieValue(value="cookiecartcounts" , defaultValue = "0") String cookiecartcounts) {
		System.out.println(cookiecartcounts);
		
		if(true) {
			Cookie a = new Cookie("cart", menuToAdd.getId().toString()); 
			
			response.addCookie(a);
		}else {
			Menu menuPersisted = menuRepository.findOne(menuToAdd.getId()); 
			
		}
		
		return "index";
    }
}
