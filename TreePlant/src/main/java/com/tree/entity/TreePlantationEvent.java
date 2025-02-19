package com.tree.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tree_plantation_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreePlantationEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String place;

	@Column(nullable = false)
	private String date;

	@Column(nullable = false)
	private String time;

	@Column(length = 500)
	private String description;

	@ManyToMany
	@JoinTable(name = "event_participants", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "tree_owner_id"))
	private Set<TreeOwner> participants = new HashSet<>();
}
