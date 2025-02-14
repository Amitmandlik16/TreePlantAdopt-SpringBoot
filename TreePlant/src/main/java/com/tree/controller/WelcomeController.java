package com.tree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/")
public class WelcomeController {
	@GetMapping("")
	public String welcome() {
		return "welcome"; // This should correspond to "welcome.html" in the templates folder
	}
}
