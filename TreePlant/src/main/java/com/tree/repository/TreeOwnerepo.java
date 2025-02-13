package com.tree.repository;

import com.tree.entity.TreeOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TreeOwnerepo extends JpaRepository<TreeOwner, Long> {
	Optional<TreeOwner> findByUsername(String username);

	Optional<TreeOwner> findByUsernameAndEmail(String username, String email);

	List<TreeOwner> findTop100ByOrderByTotalRewardsDesc();

}
