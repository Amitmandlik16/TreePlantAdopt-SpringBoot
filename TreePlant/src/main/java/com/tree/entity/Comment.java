package com.tree.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	@JsonIgnore
	@JsonIgnoreProperties(value = { "id", "text", "imagePath", "createdAt", "treeOwner", "comments", "likes" })
	private Post post;

	@ManyToOne
	@JsonIgnoreProperties(value = { "id", "password", "firstName", "middleName", "lastName", "totalTrees", "profileImg",
			"totalRewards", "country", "state", "district", "taluka", "village", "pincode", "longitude", "latitude",
			"landmark", "mobileNumber", "email", "dob" })
	@JoinColumn(name = "tree_owner_id", nullable = false)
	private TreeOwner treeOwner;

	@Column(nullable = false)
	private String text;

	@JsonIgnore
	private LocalDateTime createdAt = LocalDateTime.now();
}