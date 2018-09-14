package com.cakeworld.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cakeworld.main.SubscribeRepository;
import com.cakeworld.model.SubscribedEmail;
import com.cakeworld.util.Constants;
import com.cakeworld.util.Email.Email;
import com.cakeworld.util.Email.EmailService;
import com.cakeworld.util.Email.EmailTemplate;

@Controller
public class SubscribeController {
	
	@Autowired
	private SubscribeRepository subscribeRepository;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST) 
    public String subscribe(@ModelAttribute("subscribe")  SubscribedEmail subscribe, Model model) {
		List<SubscribedEmail> subscribeList = subscribeRepository.findByEmailId(subscribe.getEmailId());
		if(subscribeList!=null && subscribeList.size()>0 ) {
			model.addAttribute("alreadyAdded",subscribe);
			return "redirect:/index";
		}else {
			 SubscribedEmail subscribed = subscribeRepository.save(subscribe);
				model.addAttribute("subs",subscribed);
				sendSubscribeEmail(subscribed);
		        return "redirect:/index";
		}
		
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
