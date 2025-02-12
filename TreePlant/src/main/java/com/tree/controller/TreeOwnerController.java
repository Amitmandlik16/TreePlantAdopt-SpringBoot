package com.tree.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.tree.dto.ForgotPasswordRequest;
import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/treeowner")
public class TreeOwnerController {

	@Autowired
	private TreeOwnerepo treeOwnerepo;

	@Autowired
	private JavaMailSender mailSender;

	// ✅ Login API
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
		String username = request.get("username");
		String password = request.get("password");

		Optional<TreeOwner> owner = treeOwnerepo.findByUsername(username);

		if (owner.isPresent() && owner.get().getPassword().equals(password)) {
			return ResponseEntity.ok(owner.get()); // ✅ Return full user object on success
		} else {
			return ResponseEntity.status(401).body("Invalid username or password");
		}
	}

	// ✅ Update Password API
	@PutMapping("/update-password")
	public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> request) {
		String username = request.get("username");
		String oldPassword = request.get("oldPassword");
		String newPassword = request.get("newPassword");

		Optional<TreeOwner> owner = treeOwnerepo.findByUsername(username);

		if (owner.isPresent() && owner.get().getPassword().equals(oldPassword)) {
			owner.get().setPassword(newPassword); // 🔄 Update password
			treeOwnerepo.save(owner.get());
			return ResponseEntity.ok("Password updated successfully");
		} else {
			return ResponseEntity.status(400).body("Invalid credentials or user not found");
		}
	}

	@GetMapping("/getowners")
	public List<TreeOwner> getAlltreeOwners() {
		return treeOwnerepo.findAll();
	}

	@PostMapping("/registerowner")
	public ResponseEntity<TreeOwner> createOwner(@RequestBody TreeOwner treeOwner) {
		TreeOwner savedOwner = treeOwnerepo.save(treeOwner);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOwner);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TreeOwner> getTreeOwnerById(@PathVariable long id) {
		TreeOwner treeOwner = treeOwnerepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tree Owner not found with ID: " + id));
		return ResponseEntity.ok(treeOwner);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<TreeOwner> updateTreeOwner(@PathVariable long id, @RequestBody TreeOwner treeOwnerDetails) {
		TreeOwner treeOwner = treeOwnerepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tree Owner not found with ID: " + id));

		treeOwner.setUsername(treeOwnerDetails.getUsername());
		treeOwner.setPassword(treeOwnerDetails.getPassword());
		treeOwner.setFirstName(treeOwnerDetails.getFirstName());
		treeOwner.setMiddleName(treeOwnerDetails.getMiddleName());
		treeOwner.setLastName(treeOwnerDetails.getLastName());
		treeOwner.setTotalTrees(treeOwnerDetails.getTotalTrees());
		treeOwner.setProfileImg(treeOwnerDetails.getProfileImg());
		treeOwner.setTotalRewards(treeOwnerDetails.getTotalRewards());
		treeOwner.setCountry(treeOwnerDetails.getCountry());
		treeOwner.setState(treeOwnerDetails.getState());
		treeOwner.setTaluka(treeOwnerDetails.getTaluka());
		treeOwner.setDistrict(treeOwnerDetails.getDistrict());
		treeOwner.setVillage(treeOwnerDetails.getVillage());
		treeOwner.setPincode(treeOwnerDetails.getPincode());
		treeOwner.setLongitude(treeOwnerDetails.getLongitude());
		treeOwner.setLatitude(treeOwnerDetails.getLatitude());
		treeOwner.setAge(treeOwnerDetails.getAge());
		treeOwner.setLandmark(treeOwnerDetails.getLandmark());

		// Ensure these fields exist in the TreeOwner entity
		treeOwner.setMobileNumber(treeOwnerDetails.getMobileNumber());
		treeOwner.setEmail(treeOwnerDetails.getEmail());

		TreeOwner updatedTreeOwner = treeOwnerepo.save(treeOwner);
		return ResponseEntity.ok(updatedTreeOwner);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTreeOwner(@PathVariable Long id) {
		TreeOwner treeOwner = treeOwnerepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tree Owner not found with ID: " + id));

		treeOwnerepo.delete(treeOwner);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
		Optional<TreeOwner> treeOwnerOpt = treeOwnerepo.findByUsernameAndEmail(request.getUsername(),
				request.getEmail());

		if (treeOwnerOpt.isPresent()) {
			TreeOwner treeOwner = treeOwnerOpt.get();

			// Generate a temporary password
			String tempPassword = UUID.randomUUID().toString().substring(0, 8);

			// Update the password in the database
			treeOwner.setPassword(tempPassword);
			treeOwnerepo.save(treeOwner);

			// Send email with new password
			sendEmail(treeOwner.getEmail(), tempPassword);

			return ResponseEntity.ok("A temporary password has been sent to your email.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
		}
	}

	private void sendEmail(String toEmail, String tempPassword) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(toEmail);
			message.setSubject("Password Reset - Your New Temporary Password");
			message.setText(
					"Your new temporary password is: " + tempPassword + "\nPlease log in and change it immediately.");
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println("Error sending email: " + e.getMessage());
		}
	}
}
