package com.tree.service;

import com.tree.entity.Tree;
import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;
import com.tree.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TreeService {

	@Autowired
	private TreeRepository treeRepository;

	@Autowired
	private TreeOwnerepo treeOwnerepo;

	@Autowired
	private FileService fileService;

	@Value("${project.image.tree}")
	private String treepath;

	public List<Tree> getTreesByOwnerId(long ownerId) {
		return treeRepository.findByTreeOwnerId(ownerId);
	}

	// ✅ Register new tree owner
	public Tree createTree(MultipartFile image, Tree tree) throws IOException {
		tree.setRewards(5); // Initial 5 rewards on tree registration

		TreeOwner treeOwner = tree.getTreeOwner();

		if (treeOwner != null) { // Ensure treeOwner is not null
			treeOwner.setTotalTrees(treeOwner.getTotalTrees() + 1);
			treeOwner.setTotalRewards(treeOwner.getTotalRewards() + 5);

			// ✅ Save the updated TreeOwner
			treeOwnerepo.save(treeOwner);
		}
		String imgname = fileService.uploadImage(treepath, image);
		tree.setTreeImg(imgname);

		// ✅ Set the current date as registered date
		tree.setRegisteredDate(LocalDate.now());

		// ✅ Set the current time as registered time
		tree.setRegisteredtime(LocalTime.now());

		// ✅ Save and return the Tree entity
		return treeRepository.save(tree);
	}

	public List<Tree> getAllTrees() {
		return treeRepository.findAll();
	}

	public Tree getTreeById(long id) {
		return treeRepository.findById(id).orElseThrow(() -> new RuntimeException("Tree not found"));
	}

	public Tree updateTree(long id, Tree treeDetails) {
		Tree tree = getTreeById(id);
		tree.setName(treeDetails.getName());
		tree.setType(treeDetails.getType());
		tree.setTreeImg(treeDetails.getTreeImg());
		return treeRepository.save(tree);
	}

	public void deleteTree(long id) {
		Tree tree = treeRepository.getById(id);
		TreeOwner treeOwner = tree.getTreeOwner();

		if (treeOwner != null) { // Ensure treeOwner is not null
			treeOwner.setTotalTrees(treeOwner.getTotalTrees() - 1);

			// ✅ Save the updated TreeOwner
			treeOwnerepo.save(treeOwner);
		}

		treeRepository.deleteById(id);
	}
}
