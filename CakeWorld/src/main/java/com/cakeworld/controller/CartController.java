package com.cakeworld.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		}
		
		return "index";
    }
	
	

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String checkOut(Model model,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts) {
		Map<String, List<Menu>> menuFromDB = getMenuFromDB(cookiecartcounts.split("\\*"));
		model.addAttribute("checkoutCart", menuFromDB);
		return "cart";
	}

	public Map<String, List<Menu>> getMenuFromDB(String[] ids) {
		List<Menu> innerList;
		Map<String, List<Menu>> menuMap = new HashMap<String, List<Menu>>();
		for (String id : ids) {
			Menu menu = menuRepository.findOne(Integer.valueOf(id));
			if (menu != null) {
				if (menuMap.get(menu.getName()) != null) {
					menuMap.get(menu.getName()).add(menu);
				} else {
					innerList = new ArrayList<Menu>();
					innerList.add(menu);
					menuMap.put(menu.getName(), innerList);
				}
			}
		}
		return menuMap;
	}
}
