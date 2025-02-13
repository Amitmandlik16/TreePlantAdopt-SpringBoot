package com.tree.repository;

import com.tree.entity.Tree;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
	List<Tree> findByTreeOwnerId(long ownerId);

	// Custom delete method to remove all trees of a specific owner
	void deleteByTreeOwnerId(Long treeOwnerId);
}
