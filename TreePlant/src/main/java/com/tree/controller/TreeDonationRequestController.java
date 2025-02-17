package com.tree.controller;

import com.tree.entity.TreeDonationRequest;
import com.tree.service.TreeDonationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
@CrossOrigin(origins = "*")
public class TreeDonationRequestController {

    @Autowired
    private TreeDonationRequestService requestService;

    // ✅ Request Tree API
    @PostMapping(value = "/request", consumes = "application/json")
    public ResponseEntity<TreeDonationRequest> requestTree(@RequestBody TreeDonationRequest request) {
        return ResponseEntity.ok(requestService.requestTree(request));
    }

    // ✅ Get All Requests API
    @GetMapping("/all")
    public ResponseEntity<List<TreeDonationRequest>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    // ✅ Approve Request API
    @PutMapping("/approve/{requestId}")
    public ResponseEntity<String> approveRequest(@PathVariable Long requestId) {
        requestService.approveRequest(requestId);
        return ResponseEntity.ok("Request approved successfully!");
    }
}
