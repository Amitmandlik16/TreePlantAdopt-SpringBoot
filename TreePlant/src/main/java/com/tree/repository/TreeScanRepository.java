package com.tree.repository;

import com.tree.entity.TreeScan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeScanRepository extends JpaRepository<TreeScan, Long> {

	List<TreeScan> findByTreeIdOrderByScanDateDescScanTimeDesc(long treeId);
	
	// Delete all TreeScan records for a specific tree
    void deleteByTreeId(Long treeId);
}
