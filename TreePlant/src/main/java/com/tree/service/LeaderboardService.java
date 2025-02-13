package com.tree.service;

import com.tree.dto.LeaderboardDTO;
import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

	@Autowired
	private TreeOwnerepo treeOwnerRepository;

	// ✅ Fetch top 100 users with highest total rewards and map to DTO
	public List<LeaderboardDTO> getLeaderboard() {
		List<TreeOwner> treeOwners = treeOwnerRepository.findTop100ByOrderByTotalRewardsDesc();

		return treeOwners
				.stream().map(owner -> new LeaderboardDTO(owner.getUsername(), owner.getFirstName(),
						owner.getLastName(), owner.getTotalTrees(), owner.getTotalRewards()))
				.collect(Collectors.toList());
	}
}
