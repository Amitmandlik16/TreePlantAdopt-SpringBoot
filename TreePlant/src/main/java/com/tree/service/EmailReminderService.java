package com.tree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class EmailReminderService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TreeOwnerepo treeOwnerRepository;

	@Scheduled(cron = "0 44 23 * * ?") // Runs every day at 6:00 AM
	private void EncourageQuotes() {
		String quote = fetchTreeQuote(); // Get AI-generated quote
		String subject = "🌿 Daily Tree Wisdom";

		String body = "<br><br><i>\"" + quote + "\"</i><br><br>"
				+ "<a href='https://treeadoption.org/watering-guide' style='display: inline-block; padding: 10px 20px; color: white; background-color: #28a745; text-decoration: none; border-radius: 5px;'>Learn More 🌱</a><br><br>"
				+ "Thank you for being a responsible tree owner! 🌱";

		sendDailyReminderEmails(subject, body);
	}

	// ✅ Fetch Random Tree-Related Quote (Fallback if API is unavailable)
	private String fetchTreeQuote() {
		String[] quotes = { "Trees are poems that the earth writes upon the sky. 🌳",
				"The best time to plant a tree was 20 years ago. The second best time is now. 🌿",
				"A society grows great when old men plant trees whose shade they know they shall never sit in. 🌲",
				"Look deep into nature, and then you will understand everything better. 🍃",
				"Every tree, no matter how tall, started as a tiny seed. 🌱" };
		Random random = new Random();
		return quotes[random.nextInt(quotes.length)];
	}

	// ✅ Extracts Quote from OpenAI API Response (JSON Parsing)
	private String extractQuoteFromResponse(String responseBody) {
		int start = responseBody.indexOf("\"content\":\"") + 10;
		int end = responseBody.indexOf("\"}", start);
		if (start > 10 && end > start) {
			return responseBody.substring(start, end).replace("\\n", "").trim();
		}
		return "Trees are the lungs of the Earth. 🌍";
	}

	@Scheduled(cron = "0 00 12 * * ?")
	private void WaterReminder() {
		String subject = "🌱 Time to Water Your Tree!";
		String body = ",<br><br>" + "This is your daily reminder to <b>water your tree</b>! 🌳<br>"
				+ "Keeping your tree hydrated is essential for its growth.<br><br>"
				+ "<a href='https://treeadoption.org/watering-guide' style='display: inline-block; padding: 10px 20px; color: white; background-color: #28a745; text-decoration: none; border-radius: 5px;'>Learn More 🌱</a><br><br>"
				+ "Thank you for being a responsible tree owner! 🌱";
		sendDailyReminderEmails(subject, body);
	}

	public void sendDailyReminderEmails(String subject, String body) {
		List<TreeOwner> treeOwners = treeOwnerRepository.findAll();

		for (TreeOwner owner : treeOwners) {
			sendHtmlReminderEmail(owner.getEmail(), owner.getFirstName(), subject, body);
		}
	}

	// ✅ Send HTML-based Email
	private void sendHtmlReminderEmail(String toEmail, String firstName, String subject, String messageContent) {
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
					+ ".footer { font-size: 14px; color: #777; margin-top: 20px; }"
					+ ".btn { display: inline-block; padding: 10px 20px; color: white; background-color: #28a745; text-decoration: none; border-radius: 5px; }"
					+ "</style></head><body>" + "<div class='container'>" + "<div class='header'></div>"
					+ "<div class='content'>Hello " + firstName + ",</div>" + "<div class='content'>" + messageContent
					+ "</div>" + "<div class='footer'>🌱 Keep growing with the TreeAdoption community!</div>"
					+ "</div></body></html>";

			helper.setText(emailContent, true); // Enable HTML format
			mailSender.send(message);

			System.out.println("HTML Reminder Email sent to: " + toEmail);
		} catch (MessagingException e) {
			System.out.println("Error sending HTML email to " + toEmail + ": " + e.getMessage());
		}
	}
}
