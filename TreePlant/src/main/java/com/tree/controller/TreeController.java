package com.tree.controller;

import com.tree.entity.Tree;
import com.tree.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trees")
public class TreeController {

    @Autowired
    private TreeService treeService;
    
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Tree>> getTreesByOwnerId(@PathVariable long ownerId) {
        List<Tree> trees = treeService.getTreesByOwnerId(ownerId);
        return ResponseEntity.ok(trees);
    }


    @PostMapping
    public ResponseEntity<Tree> createTree(@RequestBody Tree tree) {
        return ResponseEntity.ok(treeService.createTree(tree));
    }

    @GetMapping
    public ResponseEntity<List<Tree>> getAllTrees() {
        return ResponseEntity.ok(treeService.getAllTrees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tree> getTreeById(@PathVariable long id) {
        return ResponseEntity.ok(treeService.getTreeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tree> updateTree(@PathVariable long id, @RequestBody Tree tree) {
        return ResponseEntity.ok(treeService.updateTree(id, tree));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTree(@PathVariable long id) {
        treeService.deleteTree(id);
        return ResponseEntity.ok("Tree deleted successfully");
    }
}
