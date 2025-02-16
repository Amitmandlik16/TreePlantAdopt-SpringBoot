package com.tree.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlantDetails {
	@JsonProperty("PlantName")
	private String plantName;

	@JsonProperty("WateringFrequency")
	private String wateringFrequency;

	@JsonProperty("SoilRequired")
	private String soilRequired;

	@JsonProperty("GrowthRate")
	private String growthRate;

	@JsonProperty("SunlightDuration")
	private String sunlightDuration;
}
