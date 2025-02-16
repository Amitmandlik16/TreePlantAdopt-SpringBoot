package com.tree.controller;

import com.tree.dto.Admin;
import com.tree.entity.Complaint;
import com.tree.entity.Feedback;
import com.tree.entity.TreeOwner;
import com.tree.service.AdminService;
import com.tree.service.ComplaintService;
import com.tree.service.FeedbackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private FeedbackService feedbackService;

	@GetMapping("/getfeedbacks")
	public List<Feedback> getAllFeedbacks() {
		return feedbackService.getAllFeedbacks();
	}

	@GetMapping("/getcomplaints")
	public List<Complaint> getAllComplaints() {
		return complaintService.getAllComplaints();
	}

	// ✅ Login API
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Admin admin) {
		return ResponseEntity.ok(adminService.login(admin));
	}

	// ✅ Get all tree owners
	@GetMapping("/getowners")
	public ResponseEntity<List<TreeOwner>> getAllTreeOwners() {
		return ResponseEntity.ok(adminService.getAllTreeOwners());
	}

	// ✅ Get tree owner by ID
	@GetMapping("/{id}")
	public ResponseEntity<TreeOwner> getTreeOwnerById(@PathVariable long id) {
		return ResponseEntity.ok(adminService.getTreeOwnerById(id));
	}
}
