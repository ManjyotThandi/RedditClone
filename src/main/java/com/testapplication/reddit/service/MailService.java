package com.testapplication.reddit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.testapplication.reddit.exceptions.SpringRedditException;
import com.testapplication.reddit.model.NotificationEmail;

@Service
public class MailService {

	Logger logger = LoggerFactory.getLogger(MailService.class);

	private MailContentBuilder mailContentBuilder;
	private JavaMailSender mailSender;

	@Autowired
	public MailService(MailContentBuilder mailContentBuilder, JavaMailSender mailSender) {
		this.mailContentBuilder = mailContentBuilder;
		this.mailSender = mailSender;
	}

	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("testingcodeprojects@gmail.com");
			messageHelper.setTo(notificationEmail.getRecipent());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
		};

		try {
			mailSender.send(messagePreparator);
			logger.info("Mail was sent succesfully");
		} catch (MailException e) {
			logger.info("Mail was unable to send");
			throw new SpringRedditException("Exception occured when sending mail to" + notificationEmail.getRecipent());
		}
	}
}
