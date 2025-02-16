package com.tree.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

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
	@JoinColumn(name = "tree_owner_id", nullable = false) // Foreign key column
	private TreeOwner treeOwnerId;

	private String subject;
	private String description;
	private LocalDate localDate;
	private LocalTime localTime;
}
