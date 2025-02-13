package com.tree.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "treescan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeScan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "scan_date", nullable = false)
	private LocalDate scanDate;

	@Column(name = "scan_time", nullable = false)
	private LocalTime scanTime;

	@Lob
	@Column(name = "treescan_img")
	private byte[] treeScanImg;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "latitude")
	private double latitude;

	@ManyToOne
	@JoinColumn(name = "tree_id", nullable = false)
	private Tree tree;
}
