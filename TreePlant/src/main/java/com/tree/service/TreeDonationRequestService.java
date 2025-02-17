package com.tree.service;

import com.tree.entity.TreeDonationRequest;
import com.tree.entity.TreeDonation;
import com.tree.entity.RequestStatus;
import com.tree.repository.TreeDonationRequestRepository;
import com.tree.repository.TreeDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreeDonationRequestService {
    @Autowired
    private TreeDonationRequestRepository requestRepository;

    @Autowired
    private TreeDonationRepository donationRepository;

    public TreeDonationRequest requestTree(TreeDonationRequest request) {
        return requestRepository.save(request);
    }

    public List<TreeDonationRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public void approveRequest(Long requestId) {
        Optional<TreeDonationRequest> requestOpt = requestRepository.findById(requestId);
        if (requestOpt.isPresent()) {
            TreeDonationRequest request = requestOpt.get();
            TreeDonation donation = request.getDonation();

            if (donation.getQuantity() >= request.getRequestedQuantity()) {
                donation.setQuantity(donation.getQuantity() - request.getRequestedQuantity());
                request.setStatus(RequestStatus.COMPLETED);

                if (donation.getQuantity() == 0) {
                    donation.setAvailable(false);
                }

                requestRepository.save(request);
                donationRepository.save(donation);
            }
        }
    }
}
