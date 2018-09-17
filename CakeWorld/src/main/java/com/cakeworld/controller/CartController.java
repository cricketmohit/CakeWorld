package com.cakeworld.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cakeworld.main.CartRepository;
import com.cakeworld.main.MenuRepository;
import com.cakeworld.main.UserRepository;
import com.cakeworld.model.Cart;
import com.cakeworld.model.Menu;

@Controller
public class CartController {
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/substractFromCart", method = RequestMethod.POST) 
    public void substractFromCart(@ModelAttribute("cart")  Menu menuToAdd, Model model,HttpSession session, HttpServletResponse response,HttpServletRequest request,
    		@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		String id = String.valueOf(menuToAdd.getId());
		if(userEmailCookie!=null && !userEmailCookie.isEmpty()) {
			Cart cartPersisted = cartRepository.findByLoggedInUser(userEmailCookie); 
			if(cartPersisted!=null) {
				if (!cartPersisted.getCookieCartCounts().isEmpty()) {
					String cartCookiePersisted = cartPersisted.getCookieCartCounts();

					String newCookie = cartCookiePersisted.replace("*" + id + "*", "*");
					if (cartCookiePersisted.equals(newCookie)) {
						newCookie = cartCookiePersisted.replace("*" + id, "");
					}
					if (cartCookiePersisted.equals(newCookie)) {
						newCookie = cartCookiePersisted.replace(id + "*", "");
					}
					if (cartCookiePersisted.equals(newCookie)) {

						newCookie = cartCookiePersisted.replace(id, "");

					}
					cartPersisted.setCookieCartCounts(newCookie);
					cartRepository.save(cartPersisted);

					for (Cookie cookie : request.getCookies()) {
						if (cookie.getName().equals("cookiecartcounts")) {
							cookie.setValue(newCookie);
							response.addCookie(cookie);
							break;
						}
					}
				}
			}
				
			
		}
		Map<String, List<Menu>> menuFromDB = getMenuFromDB(id.split("\\*"));
		model.addAttribute("checkoutCart", menuFromDB);
		
	
    }
	
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST) 
    public void addToCart(@ModelAttribute("cart")  Menu menuToAdd, Model model,HttpSession session, HttpServletResponse response,HttpServletRequest request,
    		@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		String id = String.valueOf(menuToAdd.getId());
		if(userEmailCookie!=null && !userEmailCookie.isEmpty()) {
			Cart cartPersisted = cartRepository.findByLoggedInUser(userEmailCookie); 
			if(cartPersisted!=null) {
				if(!cartPersisted.getCookieCartCounts().isEmpty()) {
					String cartCookiePersisted = cartPersisted.getCookieCartCounts();
					id=id+"*"+cartCookiePersisted;
				}
				
				cartPersisted.setCookieCartCounts(id); 
				cartRepository.save(cartPersisted);
				for (Cookie cookie : request.getCookies()) {
				    if (cookie.getName().equals("cookiecartcounts")) {
				    	 cookie.setValue(id);
						response.addCookie(cookie);
						break;
				    }
				}
			}else {
				Cart newCart = new Cart(userEmailCookie,id);
				cartRepository.save(newCart);
			}
				
			
		}
		Map<String, List<Menu>> menuFromDB = getMenuFromDB(id.split("\\*"));
		model.addAttribute("checkoutCart", menuFromDB);
		
	
    }
	
	

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String checkOut(Model model,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts) {
		if(cookiecartcounts.equals("")|| cookiecartcounts.equals("0")){
			return "redirect:/index";
		}
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
