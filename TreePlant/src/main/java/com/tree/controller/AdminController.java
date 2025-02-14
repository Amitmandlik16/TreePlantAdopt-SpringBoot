package com.tree.controller;

import com.tree.entity.TreeOwner;
import com.tree.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

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
