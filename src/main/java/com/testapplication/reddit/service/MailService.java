package com.testapplication.reddit.service;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.testapplication.reddit.model.NotificationEmail;

@Service
public class MailService {

	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipent());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setTe(notificationEmail.getBody());
		}
	}
}
