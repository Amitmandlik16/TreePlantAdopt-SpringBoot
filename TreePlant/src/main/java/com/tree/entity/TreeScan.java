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

	@Column(name = "treescan_img")
	private String treeScanImg;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "tree_health_status")
	private String treeHealthStatus; // e.g., Healthy, Stressed, Diseased

	@Column(name = "leaf_color")
	private String leafColor; // e.g., Green, Yellow, Brown

	@Column(name = "leaf_density")
	private double leafDensity; // Percentage of leaves present

	@Column(name = "trunk_diameter")
	private double trunkDiameter; // In cm

	@Column(name = "canopy_diameter")
	private double canopyDiameter; // In meters

	@Column(name = "growth_rate")
	private double growthRate; // Growth per month (cm)

	@Column(name = "pest_infestation")
	private boolean pestInfestation; // true if pests detected

	@Column(name = "disease_detection")
	private String diseaseDetection; // Name of detected disease, if any

	@Column(name = "soil_moisture")
	private double soilMoisture; // Percentage

	@Column(name = "chlorophyll_content")
	private double chlorophyllContent; // Measure of chlorophyll level

	@Column(name = "temperature")
	private double temperature; // Temperature around the tree (°C)

	@Column(name = "humidity")
	private double humidity; // Humidity percentage

	@Column(name = "carbon_absorption")
	private double carbonAbsorption; // Amount of CO₂ absorbed (kg/year)

	@Column(name = "oxygen_production")
	private double oxygenProduction; // Oxygen generated (kg/year)

	@ManyToOne
	@JoinColumn(name = "tree_id", nullable = false)
	private Tree tree;
}
