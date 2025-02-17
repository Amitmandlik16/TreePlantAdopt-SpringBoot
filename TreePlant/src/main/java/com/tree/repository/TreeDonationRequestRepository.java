package com.tree.repository;

import com.tree.entity.TreeDonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeDonationRequestRepository extends JpaRepository<TreeDonationRequest, Long> {
}
