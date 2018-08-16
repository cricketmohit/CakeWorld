package com.cakeworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cakeworld.model.Cake;

@Controller
public class WelcomeController {

	@RequestMapping("/cake")
	public String Person (Model model) {
		Cake cake = new Cake();
		cake.setName("Vanilla"); 
		cake.setPrice(200); 
		model.addAttribute("cake",cake);
		return "cakeView";
	}
	
	
	@RequestMapping("/")
	String entry() {
		return "home";
	}
}