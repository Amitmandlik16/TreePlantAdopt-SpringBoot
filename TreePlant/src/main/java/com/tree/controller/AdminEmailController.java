package com.tree.controller;

import com.tree.dto.EmailRequest;
import com.tree.service.AdminEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminEmailController {

	@Autowired
	private AdminEmailService adminEmailService;

	// ✅ API to send emails to all TreeOwners using JSON Request Body
	@PostMapping("/send-emails")
	public ResponseEntity<String> sendEmailsToAllTreeOwners(@RequestBody EmailRequest emailRequest) {
		if (emailRequest.getSubject() == null || emailRequest.getMessage() == null) {
			return ResponseEntity.badRequest().body("Subject and message are required!");
		}

		adminEmailService.sendEmailsToAllTreeOwners(emailRequest.getSubject(), emailRequest.getMessage());
		return ResponseEntity.ok("Emails sent successfully to all tree owners!");
	}
}
