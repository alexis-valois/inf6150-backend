package com.ezbudget.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.ezbudget.entity.EBUser;

@Service
public class MailService {

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendActivationMail(EBUser user) throws MessagingException {

		Context ctx = new Context();
		ctx.setVariable("firstName", user.getFirstName());
		ctx.setVariable("subscriptionDate", new DateTime());
		ctx.setVariable("activationToken", user.getActivationToken());
		ctx.setVariable("username", user.getUsername());
		ctx.setVariable("baseUrl", "localhost:9000/#/activate");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setSubject("EZ Budget Account Activation");
		message.setFrom("activation@ezbudget.com");
		message.setTo(user.getEmail());

		String htmlContent = templateEngine.process("activation", ctx);
		message.setText(htmlContent, true);

		javaMailSender.send(mimeMessage);
	}
}
