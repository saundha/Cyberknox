package ca.sheridancollege.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailServiceImpl {

	@Autowired JavaMailSender emailSender;
	@Autowired TemplateEngine engine;

	public void sendMail(String name, String amount, String to, String subject)
			throws MessagingException {

		// Prepare the evaluation context
		final Context ctx = new Context();
		ctx.setVariable("name", name);
		ctx.setVariable("amount", amount);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
		final MimeMessageHelper message = 
				new MimeMessageHelper(mimeMessage, true, "UTF-8"); 
		message.setSubject(subject);
		message.setFrom("assignmentginoalbert@gmail.com");
		message.setTo(to);
		
		final String htmlContent = this.engine.process("email.html", ctx);
		message.setText(htmlContent, true); // true = isHtml

		this.emailSender.send(mimeMessage);
	}
	
}
