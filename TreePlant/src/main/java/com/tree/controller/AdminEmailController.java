package com.tree.controller;

import com.tree.dto.EmailRequest;
import com.tree.service.AdminEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminEmailController {

	@Autowired
	private AdminEmailService adminEmailService;

	// ✅ API to send plain text emails to all TreeOwners using JSON Request Body
	@PostMapping("/send-plainemails")
	public ResponseEntity<String> sendPlainEmailsToAllTreeOwners(@RequestBody EmailRequest emailRequest) {
		if (emailRequest.getSubject() == null || emailRequest.getMessage() == null) {
			return ResponseEntity.badRequest().body("Subject and message are required!");
		}

		adminEmailService.sendPlainEmailsToAllTreeOwners(emailRequest.getSubject(), emailRequest.getMessage());
		return ResponseEntity.ok("Plain text emails sent successfully to all tree owners!");
	}

	// ✅ API to send emails to all TreeOwners using JSON Request Body
	@PostMapping("/send-htmlemails")
	public ResponseEntity<String> sendhtmlEmailsToAllTreeOwners(@RequestBody EmailRequest emailRequest) {
		if (emailRequest.getSubject() == null || emailRequest.getMessage() == null) {
			return ResponseEntity.badRequest().body("Subject and message are required!");
		}

		adminEmailService.sendHtmlEmailsToAllTreeOwners(emailRequest.getSubject(), emailRequest.getMessage());
		return ResponseEntity.ok("HTML Formatted Emails sent successfully to all tree owners!");
	}
}
