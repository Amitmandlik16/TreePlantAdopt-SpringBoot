package com.tree.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

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

	@Lob
	@Column(name = "tree_img")
	private byte[] treeImg;

	@Column(name = "rewards")
	private int rewards;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "latitude")
	private double latitude;

	@ManyToOne
	@JoinColumn(name = "tree_owner_id", nullable = false) // Foreign key column
	private TreeOwner treeOwner;
}
