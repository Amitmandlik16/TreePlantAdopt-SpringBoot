package com.tree.repository;

import com.tree.entity.TreeDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TreeDonationRepository extends JpaRepository<TreeDonation, Long> {
    List<TreeDonation> findByAvailableTrue();
}
