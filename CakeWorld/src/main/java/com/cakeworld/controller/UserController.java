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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cakeworld.main.MenuRepository;
import com.cakeworld.main.UserRepository;
import com.cakeworld.model.Menu;
import com.cakeworld.model.User;
import com.cakeworld.util.Constants;
import com.cakeworld.util.Email.Email;
import com.cakeworld.util.Email.EmailService;
import com.cakeworld.util.Email.EmailTemplate;

@Controller
@SessionAttributes("userEmail")
public class UserController {
	 // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.GET) 
    public String getUser(Model model) {
		return "redirect:index";
    }
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST) 
    public String forgotPassword(Model model) {
		return "forgotpassword";
    }
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST) 
    public String changePassword(@ModelAttribute("users") 
    		User user,Model model, HttpServletResponse response,
    		RedirectAttributes redirectAttributes) {
		User userPersisted;
		
			if(userRepository.findByEmail(user.getEmail())!=null && userRepository.findByEmail(user.getEmail()).size() >0 ){
				userPersisted = userRepository.findByEmail(user.getEmail()).get(0);
				userPersisted.setPassword(user.getPassword());
				userRepository.save(userPersisted);
				redirectAttributes.addFlashAttribute("changed", "changed");
			} else {
				
				redirectAttributes.addFlashAttribute("userInvalid", "userInvalid");
				  return "redirect:/login";
			}
			return "redirect:/login";
		
		
		
    }
	@RequestMapping("/logout")
	String logout(Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request,
			@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie,
			@CookieValue(value = "cookiecartcounts", defaultValue = "0") String cookiecartcounts,RedirectAttributes redirectAttributes) {
		Cookie removeCookieUserName = null;
		Cookie removeCookieCartCount = null;
		for (Cookie cookie : request.getCookies()) {
		    if (cookie.getName().equals("userEmailCookie")) {
		    	removeCookieUserName = cookie;
		    	removeCookieUserName.setMaxAge(0);
				response.addCookie(removeCookieUserName);
		    }
		    if (cookie.getName().equals("cookiecartcounts")) {
		    	removeCookieCartCount = cookie;
		    	removeCookieCartCount.setMaxAge(0);
				response.addCookie(removeCookieCartCount);
		    }
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
		redirectAttributes.addFlashAttribute("cakeMenu", cakeMenu);
		redirectAttributes.addFlashAttribute("ccMenu", ccMenu);
		redirectAttributes.addFlashAttribute("savMenu", savMenu);
		redirectAttributes.addFlashAttribute("biscuitMenu", biscuitMenu);
		redirectAttributes.addFlashAttribute("galleryMenu",galleryMenu);
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("users") 
    		User user,Model model, HttpServletResponse response,
    		@CookieValue(value="foo" , defaultValue = "hello") String fooCookie, RedirectAttributes redirectAttributes) {
		User userPersisted;
		
			if(userRepository.findByEmail(user.getEmail())!=null && userRepository.findByEmail(user.getEmail()).size() >0 ){
				userPersisted = userRepository.findByEmail(user.getEmail()).get(0);
				
			} else {
				model.addAttribute("userInvalid","userInvalid");
				  return new ModelAndView("login");
			}
				
		
		
		if(userPersisted.getPassword().equals(user.getPassword())) {
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
			model.addAttribute("userEmail", user.getEmail());
			Cookie userEmailCookie = new Cookie("userEmailCookie", user.getEmail());
			response.addCookie(userEmailCookie);
			redirectAttributes.addFlashAttribute("cakeMenu", cakeMenu);
			redirectAttributes.addFlashAttribute("ccMenu", ccMenu);
			redirectAttributes.addFlashAttribute("savMenu", savMenu);
			redirectAttributes.addFlashAttribute("biscuitMenu", biscuitMenu);

			redirectAttributes.addFlashAttribute("galleryMenu",galleryMenu);
			return new ModelAndView("redirect:/index");
		}else {
			model.addAttribute("incorrectPassword","incorrectPassword");
			model.addAttribute("emailLogin",user.getEmail());
			return new ModelAndView("login");
		}
		
		
    }
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST) 
	    public String registerUser(@ModelAttribute("users")  User user) {
			List<User> emailList = userRepository.findByEmail(user.getEmail());
			for(User userPersisted :emailList) {
				if(userPersisted.getEmail().equalsIgnoreCase(user.getEmail())){
					userPersisted.setPassword(user.getPassword());
					userRepository.save(userPersisted);
					sendRegisterEmail(user);
				return "redirect:/login";
				}
			}
		 	userRepository.save(user);
			sendRegisterEmail(user);
	        return "redirect:/login";
	    }
	
	private void sendRegisterEmail(User user) {
		String from = "test@thebakeworld.com";
		String to = user.getEmail();
		String subject = Constants.SUBJECTREGISTERCONFIRMATION;
		 
		EmailTemplate template = new EmailTemplate("registerEmail.html");
		 
		Map<String, String> replacements = new HashMap<String, String>();
		
		
		String message = template.getTemplate(replacements);
		
	
		
		Email email = new Email(from, to, subject, message);
		email.setHtml(true);
		emailService.send(email);
		
	}
	
	@RequestMapping("/add")// Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}
	@RequestMapping("/mohit")
	public @ResponseBody String getMohit() {
		// This returns a JSON or XML with the users
		return "Mohit";
	}
	@RequestMapping("/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
}