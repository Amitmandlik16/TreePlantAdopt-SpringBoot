package com.tree.controller;

import com.tree.entity.TreeDonation;
import com.tree.service.TreeDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/donations")
@CrossOrigin(origins = "*")
public class TreeDonationController {

    @Autowired
    private TreeDonationService treeDonationService;

    // ✅ Donate Tree API
    @PostMapping(value = "/donate", consumes = "multipart/form-data")
    public ResponseEntity<TreeDonation> donateTree(@RequestParam("donorId") Long donorId,
                                                   @RequestParam("species") String species,
                                                   @RequestParam("quantity") int quantity,
                                                   @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(treeDonationService.donateTree(donorId, species, quantity, image));
    }

    // ✅ Get All Donations API
    @GetMapping("/all")
    public ResponseEntity<List<TreeDonation>> getAllDonations() {
        return ResponseEntity.ok(treeDonationService.getAvailableDonations());
    }

    // ✅ Delete Donation API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDonation(@PathVariable Long id) {
        treeDonationService.deleteDonation(id);
        return ResponseEntity.ok("Donation deleted successfully!");
    }
}
