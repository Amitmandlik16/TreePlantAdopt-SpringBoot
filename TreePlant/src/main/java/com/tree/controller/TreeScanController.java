package com.tree.controller;

import com.tree.entity.TreeScan;
import com.tree.service.TreeScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
