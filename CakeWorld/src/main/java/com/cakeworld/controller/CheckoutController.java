package com.cakeworld.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cakeworld.main.BillRepository;
import com.cakeworld.main.MenuRepository;
import com.cakeworld.model.Bill;
import com.cakeworld.model.Menu;
import com.cakeworld.model.Orders;
import com.cakeworld.util.Constants;
import com.cakeworld.util.Email.Email;
import com.cakeworld.util.Email.EmailService;
import com.cakeworld.util.Email.EmailTemplate;

@Controller
public class CheckoutController {

	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	BillRepository billRepository;
	
	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String checkOut(Model model,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts) {
		Map<String, List<Menu>> menuMapFromDB = getMenuFromDB(cookiecartcounts.split("\\*"));
		model.addAttribute("checkoutCart", menuMapFromDB);
		return "checkout";
	}
	
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	public String placeOrder(Model model,@ModelAttribute("bill")  Bill bill,HttpServletResponse response,HttpServletRequest request,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts) {
		Map<String, List<Menu>> menuMapFromDB = getMenuFromDB(cookiecartcounts.split("\\*"));
		List<Orders> orderList = new ArrayList<Orders>();
		for (Map.Entry<String, List<Menu>> entry : menuMapFromDB.entrySet())
		{
				Orders order = new Orders();
				order.setMenu(entry.getValue().get(0)); 
				order.setQuantity(entry.getValue().size());
				order.setPrice(entry.getValue().get(0).getPrice()*entry.getValue().size());
				order.setCurrency((entry.getValue().get(0).getCurrency()));
				order.setMenu_name(entry.getValue().get(0).getName());
				order.setBill(bill);
				
				orderList.add(order);
		}
		bill.setOrderList(orderList);
		Bill billPersist = billRepository.save(bill);
		if(billPersist!=null && billPersist.getId()!=0) {
			sendConfirmationEmail(bill);
			clearCart("cookiecartcounts",response,request);
		}
		model.addAttribute("bill", billPersist);
		return "index";
	}
	
	public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

	private void clearCart(String cookiecartcounts, HttpServletResponse response,HttpServletRequest request) { 
		Cookie cookie = getCookie(request, cookiecartcounts);
		if (cookie != null) {
		    cookie.setValue(""); 
		    response.addCookie(cookie);
		}
		
	}

	private void sendConfirmationEmail(Bill bill) {
		String from = "test@thebakeworld.com";
		String to = bill.getEmail().toString();
		String subject = Constants.SUBJECTORDERCONFIRMATION;
		 
		EmailTemplate template = new EmailTemplate("confirmationEmail.html");
		 
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", bill.name);
		replacements.put("today", String.valueOf(new Date()));
		 
		String message = template.getTemplate(replacements);
		 
		Email email = new Email(from, to, subject, message);
		email.setHtml(true);
		emailService.send(email);
		
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
