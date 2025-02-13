package com.tree.service;

import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
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

    // ✅ Fetch all emails and send message
    public void sendEmailsToAllTreeOwners(String subject, String messageContent) {
        List<TreeOwner> treeOwners = treeOwnerRepository.findAll();

        for (TreeOwner owner : treeOwners) {
            sendEmail(owner.getEmail(), owner.getFirstName(), subject, messageContent);
        }
    }

    // ✅ Send individual email
    private void sendEmail(String toEmail, String firstName, String subject, String messageContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);

            // ✅ HTML Email Content
            String emailContent = "<!DOCTYPE html>" +
                    "<html><head><style>" +
                    "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                    ".container { max-width: 500px; background: white; padding: 20px; border-radius: 10px; " +
                    "box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); text-align: center; }" +
                    ".header { font-size: 22px; font-weight: bold; color: #2E7D32; }" +
                    ".content { font-size: 16px; color: #333; margin: 15px 0; }" +
                    ".footer { font-size: 14px; color: #777; margin-top: 20px; }" +
                    "</style></head><body>" +
                    "<div class='container'>" +
                    "<div class='header'>🌳 TreeAdoption Community Update</div>" +
                    "<div class='content'>Hello " + firstName + ",</div>" +
                    "<div class='content'>" + messageContent + "</div>" +
                    "<div class='footer'>🌱 Thank you for being part of the TreeAdoption community!</div>" +
                    "</div></body></html>";

            helper.setText(emailContent, true); // Enable HTML format
            mailSender.send(message);

            System.out.println("Email sent to: " + toEmail);
        } catch (MessagingException e) {
            System.out.println("Error sending email to " + toEmail + ": " + e.getMessage());
        }
    }
}
