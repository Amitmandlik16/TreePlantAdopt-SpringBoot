package com.tree.controller;

import com.tree.dto.ForgotPasswordRequest;
import com.tree.entity.TreeOwner;
import com.tree.service.TreeOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/treeowner")
public class TreeOwnerController {

	@Autowired
	private TreeOwnerService treeOwnerService;

	// ✅ Login API
	@PostMapping("/login")
	public ResponseEntity<TreeOwner> login(@RequestBody Map<String, String> request) {
		return ResponseEntity.ok(treeOwnerService.login(request.get("username"), request.get("password")));
	}

	// ✅ Register Owner API
	@PostMapping(value = "/registerowner", consumes = "multipart/form-data")
	public ResponseEntity<TreeOwner> registerOwner(@RequestPart("treeOwner") TreeOwner treeOwner,
			@RequestParam("image") MultipartFile image) throws IOException {

		return ResponseEntity.ok(treeOwnerService.createOwner(image, treeOwner));

	}

	// ✅ Get TreeOwner by ID
	@GetMapping("/{id}")
	public ResponseEntity<TreeOwner> getTreeOwnerById(@PathVariable long id) {
		return ResponseEntity.ok(treeOwnerService.getTreeOwnerById(id));
	}

	// ✅ Update TreeOwner
	@PutMapping("/update/{id}")
	public ResponseEntity<TreeOwner> updateTreeOwner(@PathVariable long id, @RequestBody TreeOwner treeOwnerDetails) {
		return ResponseEntity.ok(treeOwnerService.updateTreeOwner(id, treeOwnerDetails));
	}

	// ✅ Update Password
	@PutMapping("/update-password")
	public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> request) {
		return ResponseEntity.ok(treeOwnerService.updatePassword(request.get("username"), request.get("oldPassword"),
				request.get("newPassword")));
	}

	// ✅ Delete TreeOwner
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTreeOwner(@PathVariable Long id) {
		return ResponseEntity.ok(treeOwnerService.deleteTreeOwner(id));
	}

	// ✅ Forgot Password API
	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
		return ResponseEntity.ok(treeOwnerService.forgotPassword(request));
	}
}
