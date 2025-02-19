package com.tree.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tree_donation_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeDonationRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonIgnoreProperties(value = { "password", "firstName", "middleName", "lastName", "totalTrees", "profileImg",
			"totalRewards", "country", "state", "district", "taluka", "village", "pincode", "longitude", "latitude",
			"landmark", "mobileNumber", "email", "dob" })
	@JoinColumn(name = "requester_id", nullable = false)
	private TreeOwner requester;

	@ManyToOne
	@JsonIgnoreProperties(value = { "password", "firstName", "middleName", "lastName", "totalTrees", "profileImg",
			"totalRewards", "country", "state", "district", "taluka", "village", "pincode", "longitude", "latitude",
			"landmark", "mobileNumber", "email", "dob" })

	@JoinColumn(name = "donator_id", nullable = false)
	private TreeDonation donation;

	@Column(nullable = false)
	private int requestedQuantity;

	@Enumerated(EnumType.STRING)
	private RequestStatus status = RequestStatus.PENDING;
}
