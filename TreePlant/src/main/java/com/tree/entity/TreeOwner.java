package com.tree.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "treeowners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeOwner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "total_trees")
	private int totalTrees;

	@Column(name = "profile_img")
	private String profileImg;

	@Column(name = "total_rewards")
	private int totalRewards;

	@Column(name = "country")
	private String country;

	@Column(name = "state")
	private String state;

	@Column(name = "district")
	private String district;

	@Column(name = "taluka")
	private String taluka;

	@Column(name = "village")
	private String village;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "dob")
	private String DOB;

	@Column(name = "landmark")
	private String landmark;

	@Column(name = "mobile_number", unique = true, nullable = false)
	private String mobileNumber;

	@Column(name = "email", unique = true, nullable = false)
	private String email;
}
