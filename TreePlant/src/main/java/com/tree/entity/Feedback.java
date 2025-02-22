package com.tree.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonIgnoreProperties(value = {"password", "firstName", "middleName", "lastName", "totalTrees", "profileImg",
			"totalRewards", "country", "state", "district", "taluka", "village", "pincode", "longitude", "latitude",
			"landmark", "mobileNumber", "email", "dob" })
	@JoinColumn(name = "tree_owner_id", nullable = false)
	private TreeOwner treeOwner;

	private int rating;
	private String improvements;
	private String issues;
	private String additionalComments;
	private boolean recommendation; // true for Yes, false for No

	private LocalDate localDate;
	private LocalTime localTime;

	// Auto-assign date and time when creating feedback
	public Feedback(TreeOwner treeOwner, int rating, String improvements, String issues, String additionalComments,
			boolean recommendation) {
		this.treeOwner = treeOwner;
		this.rating = rating;
		this.improvements = improvements;
		this.issues = issues;
		this.additionalComments = additionalComments;
		this.recommendation = recommendation;
		this.localDate = LocalDate.now();
		this.localTime = LocalTime.now();
	}
}
