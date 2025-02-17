package com.tree.repository;

import com.tree.entity.TreePlantationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreePlantationEventRepository extends JpaRepository<TreePlantationEvent, Long> {
}
