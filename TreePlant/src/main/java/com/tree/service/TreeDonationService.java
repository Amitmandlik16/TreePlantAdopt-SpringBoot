package com.tree.service;

import com.tree.entity.TreeDonation;
import com.tree.entity.TreeOwner;
import com.tree.repository.TreeDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TreeDonationService {
    @Autowired
    private TreeDonationRepository treeDonationRepository;

    @Autowired
    private FileService fileService;

    @Value("${project.image.donatetree}")
    private String donateTreePath;

    public TreeDonation donateTree(Long donorId, String species, int quantity, MultipartFile image) throws IOException {
        // Upload image and get filename
        String fileName = fileService.uploadImage(donateTreePath, image);

        // Create donor reference
        TreeOwner donor = new TreeOwner();
        donor.setId(donorId);

        // Create and save donation
        TreeDonation treeDonation = new TreeDonation();
        treeDonation.setDonor(donor);
        treeDonation.setSpecies(species);
        treeDonation.setQuantity(quantity);
        treeDonation.setImagePath(fileName);
        treeDonation.setAvailable(true);

        return treeDonationRepository.save(treeDonation);
    }

    public List<TreeDonation> getAvailableDonations() {
        return treeDonationRepository.findByAvailableTrue();
    }

    public Optional<TreeDonation> getDonationById(Long id) {
        return treeDonationRepository.findById(id);
    }

    public void deleteDonation(Long id) {
        treeDonationRepository.deleteById(id);
    }
}
