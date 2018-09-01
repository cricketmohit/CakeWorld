package com.cakeworld.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		model.addAttribute("menu", menuRepository.findAll());
		return "index";
	}
	@RequestMapping("/index")
	String entryIndex(Model model) {
		Iterable<Menu> findAll = menuRepository.findAll(); 
		List<Menu> cakeMenu = new ArrayList<Menu>();
		List<Menu> ccMenu = new ArrayList<Menu>();
		List<Menu> savMenu = new ArrayList<Menu>();
		List<Menu> biscuitMenu = new ArrayList<Menu>();
		for (Menu menu : findAll) {
			switch(menu.getCategory().getId()) {
			case 1:
				cakeMenu.add(menu);
			case 2:
				ccMenu.add(menu);
			case 3:
				savMenu.add(menu);	
			case 4:
				biscuitMenu.add(menu);
			default :
				break;
			}
			
		}
		model.addAttribute("cakeMenu", cakeMenu);
//		model.addAttribute("ccMenu", ccMenu);
//		model.addAttribute("savMenu", savMenu);
//		model.addAttribute("biscuitMenu", biscuitMenu);
		
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