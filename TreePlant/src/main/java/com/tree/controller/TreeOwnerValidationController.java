package com.tree.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tree.service.TreeOwnerService;

@RestController
@RequestMapping("/validate")
public class TreeOwnerValidationController {

    @Autowired
    private TreeOwnerService validationService;

    @GetMapping("/username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        return ResponseEntity.ok(validationService.isUsernameExists(username));
    }

    @GetMapping("/mobile")
    public ResponseEntity<Boolean> checkMobileNumber(@RequestParam String mobileNumber) {
        return ResponseEntity.ok(validationService.isMobileNumberExists(mobileNumber));
    }

    @GetMapping("/email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(validationService.isEmailExists(email));
    }
}
