package com.tree.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.tree.entity.Tree;
import com.tree.entity.TreeScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardDTO {
	private String username;
	private String firstName;
	private String lastName;
	private int totalTrees;
	private int totalRewards;

}
