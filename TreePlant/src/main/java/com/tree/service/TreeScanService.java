package com.tree.service;

import com.tree.entity.Tree;
import com.tree.entity.TreeOwner;
import com.tree.entity.TreeScan;
import com.tree.repository.TreeOwnerepo;
import com.tree.repository.TreeRepository;
import com.tree.repository.TreeScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TreeScanService {

	@Autowired
	private TreeScanRepository treeScanRepository;

	@Autowired
	private TreeRepository treeRepository;

	@Autowired
	private TreeOwnerepo treeOwnerRepository;

	@Autowired
	private FileService fileService;

	@Value("${project.image.treescan}")
	private String treescanpath;

	public List<TreeScan> getTreeScansByTreeId(long treeId) {
		return treeScanRepository.findByTreeIdOrderByScanDateDescScanTimeDesc(treeId);
	}

	// ✅ Register new tree scan
	public TreeScan createTreeScan(MultipartFile image, TreeScan treeScan) throws IOException {
		// Fetch the Tree using treeId
		Tree tree = treeRepository.findById(treeScan.getTree().getId())
				.orElseThrow(() -> new RuntimeException("Tree not found with ID: " + treeScan.getTree().getId()));

		// Fetch the associated TreeOwner
		TreeOwner treeOwner = tree.getTreeOwner();

		// Increment rewards by 2
		tree.setRewards(tree.getRewards() + 2);

		// Increment TreeOwner's total rewards by 2
		treeOwner.setTotalRewards(treeOwner.getTotalRewards() + 2);

		// Save the updated Tree and TreeOwner
		treeRepository.save(tree);
		treeOwnerRepository.save(treeOwner);

		String imgname = fileService.uploadImage(treescanpath, image);
		treeScan.setTreeScanImg(imgname);

		treeScan.setScanDate(LocalDate.now());
		treeScan.setScanTime(LocalTime.now());

		// Save the TreeScan entry
		return treeScanRepository.save(treeScan);

	}

	public TreeScan createTreeScan(TreeScan treeScan) {
		// Fetch the Tree using treeId
		Tree tree = treeRepository.findById(treeScan.getTree().getId())
				.orElseThrow(() -> new RuntimeException("Tree not found with ID: " + treeScan.getTree().getId()));

		// Fetch the associated TreeOwner
		TreeOwner treeOwner = tree.getTreeOwner();

		// Increment rewards by 2
		tree.setRewards(tree.getRewards() + 2);

		// Increment TreeOwner's total rewards by 2
		treeOwner.setTotalRewards(treeOwner.getTotalRewards() + 2);

		// Save the updated Tree and TreeOwner
		treeRepository.save(tree);
		treeOwnerRepository.save(treeOwner);

		treeScan.setScanDate(LocalDate.now());
		treeScan.setScanTime(LocalTime.now());

		// Save the TreeScan entry
		return treeScanRepository.save(treeScan);
	}

	public List<TreeScan> getAllTreeScans() {
		return treeScanRepository.findAll();
	}

	public TreeScan getTreeScanById(long id) {
		return treeScanRepository.findById(id).orElseThrow(() -> new RuntimeException("TreeScan not found"));
	}

	public TreeScan updateTreeScan(long id, TreeScan treeScanDetails) {
		TreeScan treeScan = getTreeScanById(id);
		treeScan.setScanDate(LocalDate.now());
		treeScan.setScanTime(LocalTime.now());
		treeScan.setTreeScanImg(treeScanDetails.getTreeScanImg());
		treeScan.setLongitude(treeScanDetails.getLongitude());
		treeScan.setLatitude(treeScanDetails.getLatitude());
		return treeScanRepository.save(treeScan);
	}

	public void deleteTreeScan(long id) {
		treeScanRepository.deleteById(id);
	}
}
