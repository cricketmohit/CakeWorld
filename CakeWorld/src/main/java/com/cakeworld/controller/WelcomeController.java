package com.cakeworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cakeworld.main.MenuRepository;
import com.cakeworld.model.Menu;

@Controller
public class WelcomeController {


	@Autowired
	private MenuRepository menuRepository;
	
	@RequestMapping("/")
	String entry(Model model) {
		Iterable<Menu> menuList = menuRepository.findAll();
		for (Menu menu : menuList) { 
			if(menu.getId()!=null) {
				model.addAttribute("menu",menu);
			}
		}
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