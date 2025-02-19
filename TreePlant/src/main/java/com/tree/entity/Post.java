package com.tree.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@ManyToOne
//	@JoinColumn(name = "tree_owner_id", nullable = false)
//	private TreeOwner treeOwner;
	
	@ManyToOne
	@JoinColumn(name = "tree_owner_id", nullable = false)
	@JsonIgnoreProperties(value = { "password", "firstName", "middleName", "lastName", "totalTrees", "profileImg",
			"totalRewards", "country", "state", "district", "taluka", "village", "pincode", "longitude", "latitude",
			"landmark", "mobileNumber", "email", "dob" })
	private TreeOwner treeOwner;
	
	

	@Column(nullable = false)
	private String text;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Like> likes;
}
