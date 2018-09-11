package com.cakeworld.util.Email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.cakeworld.util.ApplicationLogger;



/**
 * @author pavan.solapure
 *
 */
@Component
public class EmailService {

	
	@Autowired
	private JavaMailSender mailSender;
	private static final ApplicationLogger logger = ApplicationLogger.getInstance();
	
	
	public void send(Email eParams) {

		if (eParams.isHtml()) {
			try {
				sendHtmlMailViaGoogle(eParams);
			} catch (MessagingException e) {
				logger.error("Could not send email to : {} Error = {}", eParams.getToAsList(), e.getMessage());
			}
		} else {
			sendPlainTextMail(eParams);
		}

	}

	private void sendHtmlMail(Email eParams) throws MessagingException {

		boolean isHtml = true;

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
		helper.setReplyTo(eParams.getFrom());
		helper.setFrom(eParams.getFrom());
		helper.setSubject(eParams.getSubject());
		helper.setText(eParams.getMessage(), isHtml);

		if (eParams.getCc().size() > 0) {
			helper.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
		}

		mailSender.send(message);
	}

	private void sendPlainTextMail(Email eParams) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		eParams.getTo().toArray(new String[eParams.getTo().size()]);
		mailMessage.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
		mailMessage.setReplyTo(eParams.getFrom());
		mailMessage.setFrom(eParams.getFrom());
		mailMessage.setSubject(eParams.getSubject());
		mailMessage.setText(eParams.getMessage());

		if (eParams.getCc().size() > 0) {
			mailMessage.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
		}

		mailSender.send(mailMessage);

	}
	
	private void sendHtmlMailViaGoogle(Email eParams) throws MessagingException {


		final String username = "thebakeworlds@gmail.com";
		final String password = "Nagarshyam19*";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

			MimeMessage message = new MimeMessage(session);
			
		   
			message.setFrom(new InternetAddress("thebakeworlds@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(eParams.getTo().get(0))); 
			message.setSubject(eParams.getSubject());
			message.setText(eParams.getMessage());
			if (eParams.getCc().size() > 0) {
				message.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(eParams.getCc().get(0)));
			}
			 message.setContent( eParams.getMessage(), "text/html; charset=utf-8" );
			
			message.saveChanges();
			Transport.send(message);

			System.out.println("Done");

		
	
	}

}