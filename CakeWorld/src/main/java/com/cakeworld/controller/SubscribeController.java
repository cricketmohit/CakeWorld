package com.cakeworld.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cakeworld.main.ContactRepository;
import com.cakeworld.main.SubscribeRepository;
import com.cakeworld.main.UserRepository;
import com.cakeworld.model.ContactUs;
import com.cakeworld.model.SubscribedEmail;
import com.cakeworld.model.User;
import com.cakeworld.util.Constants;
import com.cakeworld.util.Email.Email;
import com.cakeworld.util.Email.EmailService;
import com.cakeworld.util.Email.EmailTemplate;

@Controller
public class SubscribeController {
	
	@Autowired
	private SubscribeRepository subscribeRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST) 
    public String subscribe(@ModelAttribute("subscribe")  SubscribedEmail subscribe, Model model,RedirectAttributes redirectAttributes,
    		@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
		List<SubscribedEmail> subscribeList = subscribeRepository.findByEmailId(subscribe.getEmailId());
		
		if(subscribeList!=null && subscribeList.size()>0 ) {
			 redirectAttributes.addFlashAttribute("subs","subs");
			model.addAttribute("alreadyAdded",subscribe);
			return "redirect:/index";
		}else {
			 SubscribedEmail subscribed = subscribeRepository.save(subscribe);
			 redirectAttributes.addFlashAttribute("subs","subs");
				sendSubscribeEmail(subscribed);
		        return "redirect:/index";
		}
		
    }
	@RequestMapping(value = "/subscribe", method = RequestMethod.GET) 
    public String subscribeGet(@ModelAttribute("subscribe")  SubscribedEmail subscribe, Model model,
    		@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		  return "redirect:/index";
    }
	
	@RequestMapping(value = "/contactus", method = RequestMethod.POST) 
    public String contactUs(@ModelAttribute("contactus")  ContactUs contactus, Model model,RedirectAttributes redirectAttributes,
    		@CookieValue(value = "userEmailCookie", defaultValue = "") String userEmailCookie) {
		
		if (!userEmailCookie.equalsIgnoreCase("")) {
			User user = userRepository.findByEmail(userEmailCookie).get(0);
			model.addAttribute("loggedInUser", user.getName());
		}
			 ContactUs contactPersited = contactRepository.save(contactus);
			 redirectAttributes.addFlashAttribute("contactSoon","contactSoon");
				sendContactEmail(contactPersited);
		        return "redirect:/index";
		
		
    }

	private void sendContactEmail(ContactUs contactPersited) {
		
		String from = "test@thebakeworld.com";
		String to = "thebakeworlds@gmail.com";
		String subject = contactPersited.getSubject();
		
		String message = "Person Email: "+contactPersited.getEmailId() +" Person Name :"+contactPersited.getName()+" \n"+" Message: "+contactPersited.getMessage();
		
	
		
		Email email = new Email(from, to, subject, message);
		email.setHtml(true);
		emailService.send(email);
		
	}

	private void sendSubscribeEmail(SubscribedEmail subscribe) {
		String from = "test@thebakeworld.com";
		String to = subscribe.getEmailId();
		String subject = Constants.SUBJECTORDERCONFIRMATION;
		 
		EmailTemplate template = new EmailTemplate("subscribeEmail.html");
		 
		Map<String, String> replacements = new HashMap<String, String>();
		
		
		String message = template.getTemplate(replacements);
		
	
		
		Email email = new Email(from, to, subject, message);
		email.setHtml(true);
		emailService.send(email);
		
	}

}
