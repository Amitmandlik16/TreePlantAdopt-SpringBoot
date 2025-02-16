package com.tree.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tree.entity.TreeOwner;

@Service
public class EmailService {

	@Autowired
	private TreeOwnerService treeOwnerService;

	@Autowired
	private JavaMailSender mailSender;

	public void sendHtmlInvitationEmail(String invitedName, String invitedEmail, long id) throws MessagingException {

		TreeOwner treeOwner = treeOwnerService.getTreeOwnerById(id);
		String inviterName = treeOwner.getFirstName() + " " + treeOwner.getLastName();
		String inviterEmail = treeOwner.getEmail();

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(invitedEmail);
		helper.setSubject("🌳 " + inviterName + " Invited You to Join Tree Adoption!");
		helper.setFrom("your-email@gmail.com"); // Replace with your email

		// HTML Email Content
		String htmlContent = "<!DOCTYPE html>" + "<html><head><style>"
				+ "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; text-align: center; }"
				+ ".email-container { background: white; padding: 20px; border-radius: 10px; max-width: 500px; margin: auto; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); }"
				+ ".header { color: green; font-size: 20px; font-weight: bold; }"
				+ ".content { margin-top: 10px; font-size: 16px; color: #333; }"
				+ ".cta-button { display: inline-block; margin-top: 15px; padding: 12px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; }"
				+ ".footer { margin-top: 20px; font-size: 14px; color: gray; }" + "</style></head>" + "<body>"
				+ "<div class='email-container'>" + "<p class='header'>🌱 Join the Tree Adoption Program!</p>"
				+ "<p class='content'>Hello <strong>" + invitedName + "</strong>,</p>" + "<p class='content'><strong>"
				+ inviterName + "</strong> (<a href='mailto:" + inviterEmail + "'>" + inviterEmail
				+ "</a>) has invited you to participate in the <b>Tree Adoption Program</b>!</p>"
				+ "<p class='content'>Let's work together to make our planet greener! Click the button below to get started.</p>"
				+ "<a href='https://treeadoption.example.com' class='cta-button'>Join Now 🌳</a>"
				+ "<p class='footer'>Thank you for helping make the world a better place! 🌍</p>" + "</div>"
				+ "</body></html>";

		helper.setText(htmlContent, true);
		mailSender.send(message);

		System.out.println("HTML invitation email sent to: " + invitedEmail);
	}
}
