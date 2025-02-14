package com.tree.service;

import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminEmailService {

	@Autowired
	private TreeOwnerepo treeOwnerRepository;

	@Autowired
	private JavaMailSender mailSender;

	// ✅ Fetch all emails and send plain text message
	public void sendPlainEmailsToAllTreeOwners(String subject, String messageContent) {
		List<TreeOwner> treeOwners = treeOwnerRepository.findAll();

		for (TreeOwner owner : treeOwners) {
			sendPlainEmail(owner.getEmail(), owner.getFirstName(), subject, messageContent);
		}
	}

	// ✅ Send individual plain text email
	private void sendPlainEmail(String toEmail, String firstName, String subject, String messageContent) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(toEmail);
			message.setSubject(subject);

			// ✅ Plain text email content
			String emailContent = "Hello " + firstName + ",\n\n" + messageContent + "\n\n"
					+ "Thank you for being part of the TreeAdoption community! 🌱";

			message.setText(emailContent);
			mailSender.send(message);

			System.out.println("Plain text email sent to: " + toEmail);
		} catch (Exception e) {
			System.out.println("Error sending plain text email to " + toEmail + ": " + e.getMessage());
		}
	}

	// ✅ Fetch all emails and send message
	public void sendHtmlEmailsToAllTreeOwners(String subject, String messageContent) {
		List<TreeOwner> treeOwners = treeOwnerRepository.findAll();

		for (TreeOwner owner : treeOwners) {
			sendHtmlEmail(owner.getEmail(), owner.getFirstName(), subject, messageContent);
		}
	}

	// ✅ Send individual email
	private void sendHtmlEmail(String toEmail, String firstName, String subject, String messageContent) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(toEmail);
			helper.setSubject(subject);

			// ✅ HTML Email Content
			String emailContent = "<!DOCTYPE html>" + "<html><head><style>"
					+ "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }"
					+ ".container { max-width: 500px; background: white; padding: 20px; border-radius: 10px; "
					+ "box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); text-align: center; }"
					+ ".header { font-size: 22px; font-weight: bold; color: #2E7D32; }"
					+ ".content { font-size: 16px; color: #333; margin: 15px 0; }"
					+ ".footer { font-size: 14px; color: #777; margin-top: 20px; }" + "</style></head><body>"
					+ "<div class='container'>" + "<div class='header'>🌳 TreeAdoption Community Update</div>"
					+ "<div class='content'>Hello " + firstName + ",</div>" + "<div class='content'>" + messageContent
					+ "</div>" + "<div class='footer'>🌱 Thank you for being part of the TreeAdoption community!</div>"
					+ "</div></body></html>";

			helper.setText(emailContent, true); // Enable HTML format
			mailSender.send(message);

			System.out.println("HTML Email sent to: " + toEmail);
		} catch (MessagingException e) {
			System.out.println("Error sending HTML email to " + toEmail + ": " + e.getMessage());
		}
	}
}
