package com.tree.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tree_donations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@ManyToOne
	@JsonIgnoreProperties(value = {"password", "firstName", "middleName", "lastName", "totalTrees", "profileImg",
			"totalRewards", "country", "state", "district", "taluka", "village", "pincode", "longitude", "latitude",
			"landmark", "mobileNumber", "email", "dob" })
    @JoinColumn(name = "donor_id", nullable = false)
    private TreeOwner donor;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    private LocalDate donationDate;

    private boolean available = true;

    @PrePersist
    protected void onCreate() {
        this.donationDate = LocalDate.now();
    }
}
