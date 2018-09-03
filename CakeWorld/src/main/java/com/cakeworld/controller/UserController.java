package com.cakeworld.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cakeworld.main.UserRepository;
import com.cakeworld.model.Users;


@Controller    // This means that this class is a Controller
public class UserController {
	 // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.GET) 
    public Iterable<Users> getUser(Model model) {
		Iterable<Users> users = userRepository.findAll(); 
        return users;
    }
	@RequestMapping(value = "/login", method = RequestMethod.POST) 
    public String login(@ModelAttribute("users")  Users user,HttpSession session) {
		Users userPersisted;
		if(session.getAttribute("userDBSession")!=null){
			 userPersisted = (Users)session.getAttribute("userDBSession");
		}else {
			userPersisted = userRepository.findByEmail(user.getEmail()).get(0); 
			session.setAttribute("userDBSession",userPersisted);
		}
		
		if(userPersisted.getPassword().equals(user.getPassword()))
			return "index";
		
		return "login";
    }
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST) 
	    public String registerUser(@ModelAttribute("users")  Users user) {
			List<Users> emailList = userRepository.findByEmail(user.getEmail());
			for(Users userPersisted :emailList) {
				if(userPersisted.getEmail().equalsIgnoreCase(user.getEmail())){
					userPersisted.setPassword(user.getPassword());
					userRepository.save(userPersisted);
				return"login";	
				}
			}
		 	userRepository.save(user);
	        return "login";
	    }
	@RequestMapping("/add")// Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Users n = new Users();
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
	public @ResponseBody Iterable<Users> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
}