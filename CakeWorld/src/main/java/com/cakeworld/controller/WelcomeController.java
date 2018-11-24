
package com.cakeworld.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cakeworld.main.MenuRepository;
import com.cakeworld.main.UserRepository;
import com.cakeworld.model.Menu;
import com.cakeworld.model.User;

@Controller
@SessionAttributes("userEmail")
public class WelcomeController {

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private UserRepository userRepository;
	int productIdGlobal = 0;
	@RequestMapping("/")
	String entry(Model model, HttpSession session, HttpServletResponse response,
			@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		Iterable<Menu> findAll = menuRepository.findAll();
		List<Menu> cakeMenu = new ArrayList<Menu>();
		List<Menu> ccMenu = new ArrayList<Menu>();
		List<Menu> savMenu = new ArrayList<Menu>();
		List<Menu> biscuitMenu = new ArrayList<Menu>();
		List<Menu> galleryMenu = new ArrayList<Menu>();

		for (Menu menu : findAll) {
			switch (menu.getCategory().getId()) {
			case 1:
				cakeMenu.add(menu);
				break;
			case 2:
				ccMenu.add(menu);
				break;
			case 3:
				savMenu.add(menu);
				break;
			case 4:
				biscuitMenu.add(menu);
				break;
			default:
				break;
			}
			if (galleryMenu.size() < 12) {
				galleryMenu.add(menu);
			}

		}
		model.addAttribute("cakeMenu", cakeMenu);
		model.addAttribute("ccMenu", ccMenu);
		model.addAttribute("savMenu", savMenu);
		model.addAttribute("biscuitMenu", biscuitMenu);
		model.addAttribute("galleryMenu", galleryMenu);
		return "index";
	}
	
	@RequestMapping("/index")
	String entryIndex(Model model, HttpSession session, HttpServletResponse response,
			@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		Iterable<Menu> findAll = menuRepository.findAll();
		List<Menu> cakeMenu = new ArrayList<Menu>();
		List<Menu> ccMenu = new ArrayList<Menu>();
		List<Menu> savMenu = new ArrayList<Menu>();
		List<Menu> biscuitMenu = new ArrayList<Menu>();
		List<Menu> galleryMenu = new ArrayList<Menu>();
		for (Menu menu : findAll) {
			switch (menu.getCategory().getId()) {
			case 1:
				cakeMenu.add(menu);
				break;
			case 2:
				ccMenu.add(menu);
				break;
			case 3:
				savMenu.add(menu);
				break;
			case 4:
				biscuitMenu.add(menu);
				break;
			default:
				break;
			}
			if (galleryMenu.size() < 12) {
				galleryMenu.add(menu);
			}
		}
		model.addAttribute("cakeMenu", cakeMenu);
		model.addAttribute("ccMenu", ccMenu);
		model.addAttribute("savMenu", savMenu);
		model.addAttribute("biscuitMenu", biscuitMenu);
		model.addAttribute("galleryMenu", galleryMenu);

		return "index";
	}
	
	@RequestMapping("/product")
	String product(Model model, HttpSession session, HttpServletResponse response,
			@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts,
			HttpServletRequest request) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		Iterable<Menu> findAll = menuRepository.findAll();
		List<Menu> cakeMenu = new ArrayList<Menu>();
		List<Menu> ccMenu = new ArrayList<Menu>();
		List<Menu> savMenu = new ArrayList<Menu>();
		List<Menu> biscuitMenu = new ArrayList<Menu>();
		List<Menu> galleryMenu = new ArrayList<Menu>();
		for (Menu menu : findAll) {
			switch (menu.getCategory().getId()) {
			case 1:
				cakeMenu.add(menu);
				break;
			case 2:
				ccMenu.add(menu);
				break;
			case 3:
				savMenu.add(menu);
				break;
			case 4:
				biscuitMenu.add(menu);
				break;
			default:
				break;
			}
			if (galleryMenu.size() < 12) {
				galleryMenu.add(menu);
			}
		}
		model.addAttribute("cakeMenu", cakeMenu);
		model.addAttribute("ccMenu", ccMenu);
		model.addAttribute("savMenu", savMenu);
		model.addAttribute("biscuitMenu", biscuitMenu);
		model.addAttribute("galleryMenu", galleryMenu);
		Menu product = null;
		/*for (Cookie cookie : request.getCookies()) {
		    if (cookie.getName().equals("product")) {
		    	 product = menuRepository.findOne(Integer.parseInt(cookie.getValue()));
				model.addAttribute("product", product);
				break;
		    }
		}*/
		Object productId = session.getAttribute("productIdSession");
		if(productId!=null) {
			product = menuRepository.findOne(Integer.parseInt(productId.toString()));
			model.addAttribute("product", product);
		}
		
		if(product!=null) {
			return "product";
		}else {
			return "redirect:/index";
		}
		
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public  String checkPinCode(@ModelAttribute("productId") Menu productId, Model model, 
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts,
			@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie,
			HttpServletResponse response,HttpServletRequest request,HttpSession session) {
	
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
			model.addAttribute("discountEligible", "discountEligible");
		}
		//Cookie cookie = new Cookie("product", productId.getId().toString());
		//response.addCookie(cookie);
		Menu product = menuRepository.findOne(productId.getId());
		model.addAttribute("product", product);
		session.setAttribute("productIdSession", productId.getId());
		Object productId2 = session.getAttribute("productIdSession");
		return "product";
	}

	@ExceptionHandler
	@RequestMapping("/404")
	String noPage(Model model, @CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		return "404";
	}

	@RequestMapping("/about")
	String about(Model model, @CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		return "about";
	}

	@RequestMapping("/blog")
	String blog(	Model model, @CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		return "404";
	}

	@RequestMapping("/contact")
	String contact(	Model model, @CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		return "contact";
	}

	@RequestMapping("/gallery")
	String gallery(Model model, @CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		Iterable<Menu> findAll = menuRepository.findAll();
		List<Menu> galleryMenu = new ArrayList<Menu>();

		for (Menu menu : findAll) {
			galleryMenu.add(menu);

		}
		model.addAttribute("galleryMenu", galleryMenu);
		return "gallery";
	}

	

	@RequestMapping("/login")
	String login(Model model, @CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		return "login";
	}

	@RequestMapping("/register")
	String register(Model model, @CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		return "register";
	}

	@RequestMapping("/single")
	String single() {
		return "redirect:/404";
	}

}