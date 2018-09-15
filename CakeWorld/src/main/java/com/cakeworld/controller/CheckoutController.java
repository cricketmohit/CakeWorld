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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cakeworld.main.BillRepository;
import com.cakeworld.main.MenuRepository;
import com.cakeworld.model.Bill;
import com.cakeworld.model.Menu;
import com.cakeworld.model.Orders;
import com.cakeworld.util.Constants;
import com.cakeworld.util.DateUtil;
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
		if(cookiecartcounts.equals("")|| cookiecartcounts.equals("0")){
			return "redirect:/index";
		}
		Map<String, List<Menu>> menuMapFromDB = getMenuFromDB(cookiecartcounts.split("\\*"));
		model.addAttribute("checkoutCart", menuMapFromDB);
		return "checkout";
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkOutGet(Model model,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts) {
		Map<String, List<Menu>> menuMapFromDB = getMenuFromDB(cookiecartcounts.split("\\*"));
		if(cookiecartcounts.equals("")|| cookiecartcounts.equals("0")){
			return "redirect:/index";
		}
		model.addAttribute("checkoutCart", menuMapFromDB);
		return "checkout";
	}
	
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	public String placeOrder(Model model,@ModelAttribute("bill")  Bill bill,HttpServletResponse response,HttpServletRequest request,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts,RedirectAttributes redirectAttributes) {
		Map<String, List<Menu>> menuMapFromDB = getMenuFromDB(cookiecartcounts.split("\\*"));
		List<Orders> orderList = new ArrayList<Orders>();
		int deliveryCharge=0;
		int subTotal=0;
		int totalBillPrice=0;
		if(menuMapFromDB.entrySet()==null || menuMapFromDB.entrySet().size()<1 ) {
			return "redirect:/index";
		}
		for (Map.Entry<String, List<Menu>> entry : menuMapFromDB.entrySet())
		{
				Orders order = new Orders();
				order.setMenu(entry.getValue().get(0)); 
				order.setQuantity(entry.getValue().size());
				order.setPrice(entry.getValue().get(0).getPrice()*entry.getValue().size());
				order.setCurrency((entry.getValue().get(0).getCurrency()));
				order.setMenu_name(entry.getValue().get(0).getName());
				order.setCreationTime(new Date());
				subTotal=subTotal+entry.getValue().get(0).getPrice()*entry.getValue().size();
				order.setBill(bill);
				
				orderList.add(order);
		}
		if(subTotal<350) {
			deliveryCharge=49;
		}
		totalBillPrice=subTotal+deliveryCharge;
		bill.setSubTotal(subTotal);
		bill.setDeliveryCharge(deliveryCharge);
		bill.setTotalBillPrice(totalBillPrice);
		bill.setOrderList(orderList);
		bill.setCreationTime(new Date());
		Bill billPersist = billRepository.save(bill);
		if(billPersist!=null && billPersist.getId()!=0) {
			sendConfirmationEmail(bill);
			clearCart("cookiecartcounts",response,request);
		}
		List<Bill> billPersistList = new ArrayList<Bill>();
		billPersistList.add(billPersist);
		
		redirectAttributes.addFlashAttribute("finalBill", bill);
		return "redirect:/index";
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
		replacements.put("name", bill.getName());
		replacements.put("address", bill.getAddress());
		replacements.put("zip", String.valueOf(bill.getZip()));
		replacements.put("city", bill.getCity());
		replacements.put("phone",  String.valueOf(bill.getPhone()));
		replacements.put("orderDate", DateUtil.getDateFormat(bill.getOrderDate()));// Change to Delivery Date
		replacements.put("timeSlot", bill.getTimeSlot());// Change to slot
		replacements.put("orderNumber", String.valueOf(bill.getId()));
		replacements.put("subTotal",String.valueOf(bill.getSubTotal())+" "+bill.getOrderList().get(0).getCurrency());
		replacements.put("deliveryCharge", String.valueOf(bill.getDeliveryCharge())+" "+bill.getOrderList().get(0).getCurrency());
		replacements.put("total", String.valueOf(bill.getTotalBillPrice())+" "+bill.getOrderList().get(0).getCurrency());
		
		String message = template.getTemplate(replacements);
		String orders = getOrderDetails(bill);
	
		message = message.replace("#*order*#", orders);
		Email email = new Email(from, to, subject, message);
		email.setHtml(true);
		emailService.send(email);
		
	}

	private String getOrderDetails(Bill bill) {
		StringBuilder htmlBuilder = new StringBuilder();
		EmailTemplate template = new EmailTemplate("OrderLoop.html");
		for(Orders order :bill.getOrderList()) {
			Map<String, String> replacements = new HashMap<String, String>();
			replacements.put("menuName", order.getMenu_name());
			replacements.put("imageUrl", order.getMenu().getImageUrl());
			replacements.put("quantity", String.valueOf(order.getQuantity()));
			replacements.put("priceWithCurrency", order.getPrice()+" "+order.getCurrency());
			String message = template.getTemplate(replacements);
			htmlBuilder.append(message);
		}
		
		return htmlBuilder.toString();
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
