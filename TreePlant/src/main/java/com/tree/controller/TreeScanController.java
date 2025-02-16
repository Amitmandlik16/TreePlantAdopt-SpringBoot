package com.tree.controller;

import com.tree.entity.Tree;
import com.tree.entity.TreeScan;
import com.tree.service.TreeScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/treescan")
public class TreeScanController {

	@Autowired
	private TreeScanService treeScanService;

	@GetMapping("/tree/{treeId}")
	public ResponseEntity<List<TreeScan>> getTreeScansByTreeId(@PathVariable long treeId) {
		List<TreeScan> treeScans = treeScanService.getTreeScansByTreeId(treeId);
		return ResponseEntity.ok(treeScans);
	}

	// ✅ Register TreeScan API
	@PostMapping(value = "/registertreescan", consumes = "multipart/form-data")
	public ResponseEntity<TreeScan> registerTreeScan(@RequestPart("treeScan") TreeScan treeScan,
			@RequestParam("image") MultipartFile image) throws IOException {

		return ResponseEntity.ok(treeScanService.createTreeScan(image, treeScan));

	}

	@PostMapping
	public ResponseEntity<TreeScan> createTreeScan(@RequestBody TreeScan treeScan) {
		return ResponseEntity.ok(treeScanService.createTreeScan(treeScan));
	}

	@GetMapping
	public ResponseEntity<List<TreeScan>> getAllTreeScans() {
		return ResponseEntity.ok(treeScanService.getAllTreeScans());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TreeScan> getTreeScanById(@PathVariable long id) {
		return ResponseEntity.ok(treeScanService.getTreeScanById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TreeScan> updateTreeScan(@PathVariable long id, @RequestBody TreeScan treeScan) {
		return ResponseEntity.ok(treeScanService.updateTreeScan(id, treeScan));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTreeScan(@PathVariable long id) {
		treeScanService.deleteTreeScan(id);
		return ResponseEntity.ok("Tree Scan deleted successfully");
	}
}
