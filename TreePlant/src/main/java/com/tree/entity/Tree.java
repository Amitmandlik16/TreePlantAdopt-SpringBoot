package com.tree.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tree")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tree {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "registered_date")
	private LocalDate registeredDate;

	@Column(name = "registered_time")
	private LocalTime registeredtime;

	@Column(name = "tree_img")
	private String treeImg;

	@Column(name = "rewards")
	private int rewards;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "latitude")
	private double latitude;

	@ManyToOne
	@JsonIgnoreProperties(value = { "password", "firstName", "middleName", "lastName", "totalTrees", "profileImg",
			"totalRewards", "country", "state", "district", "taluka", "village", "pincode", "longitude", "latitude",
			"landmark", "mobileNumber", "email", "dob" })
	@JoinColumn(name = "tree_owner_id", nullable = false)
	private TreeOwner treeOwner;
}
