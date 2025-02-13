package com.tree.service;

import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private TreeOwnerepo treeOwnerepo;

    // ✅ Fetch all tree owners
    public List<TreeOwner> getAllTreeOwners() {
        return treeOwnerepo.findAll();
    }

    // ✅ Fetch tree owner by ID
    public TreeOwner getTreeOwnerById(long id) {
        return treeOwnerepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tree Owner not found with ID: " + id));
    }
}
