package com.demo.token.serviceImpl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.demo.token.model.Users.Role;
import com.demo.token.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
	private final JavaMailSender javaMailSender;

	public MailServiceImpl(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}
	@Override
	public void sendEmail(String to, String name, String email, String phoneNumber, String userName, String password,
			Role role) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject("Registration Successful");

			// Create the email body with user details and a custom message
			String emailBody = "<h3>Welcome " + name + "!</h3>"
					+ "<p>Your registration was successful. Please find your details below:</p>"
					+ "<h3>User Details:</h3>" + "<p><strong>Name:</strong> " + name + "</p>"
					+ "<p><strong>Email:</strong> " + email + "</p>" + "<p><strong>PhoneNumber:</strong> " + phoneNumber
					+ "</p>" + "<p><strong>Username:</strong> " + userName + "</p>" + "<p><strong>Password:</strong> "
					+ password + "</p>" + "<p><strong>Role:</strong> " + role + "</p>"
					+ "<p><strong>Is Active:</strong> true</p>" + "<p>Thank you for registering with us!</p>";

			helper.setText(emailBody, true); // 'true' indicates that the text contains HTML

			javaMailSender.send(message);
			System.out.println("Registration email sent successfully!");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while sending email: " + e.getMessage());
		}
	}

	{
		// TODO Auto-generated method stub

	}

}
