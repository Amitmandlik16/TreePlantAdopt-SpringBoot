package com.tree.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tree.repository.TreeOwnerepo;
import com.tree.repository.TreeRepository;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping("/")
public class WelcomeController {
	@Autowired
	private TreeOwnerepo treeOwnerRepository;

	@Autowired
	private TreeRepository treeRepository;
	
	@GetMapping("")
	public String welcome() {
		return "welcome"; // This should correspond to "welcome.html" in the templates folder
	}
	
	// ✅ GET API to get the total count of tree owners
    @GetMapping("/treeowners-count")
    public ResponseEntity<Integer> getTotalTreeOwnerCount() {
        int count = (int) treeOwnerRepository.count();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/trees-count")
    public ResponseEntity<Integer> getTotalTreeCount() {
        int count = (int) treeRepository.count();
        return ResponseEntity.ok(count);
    }
}
