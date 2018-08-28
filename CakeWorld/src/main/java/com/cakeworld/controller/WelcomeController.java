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
		return "index";
	}
	@RequestMapping("/index")
	String index() {
		return "index";
	}
	@RequestMapping("/404")
	String noPage() {
		return "404";
	}
	@RequestMapping("/about")
	String about() {
		return "about";
	}
	@RequestMapping("/blog")
	String blog() {
		return "blog";
	}
	@RequestMapping("/contact")
	String contact() {
		return "contact";
	}
	@RequestMapping("/gallery")
	String gallery() {
		return "gallery";
	}
	@RequestMapping("/home")
	String home() {
		return "home";
	}
	@RequestMapping("/login")
	String login() {
		return "login";
	}
	@RequestMapping("/register")
	String register() {
		return "register";
	}
	@RequestMapping("/single")
	String single() {
		return "single";
	}
}