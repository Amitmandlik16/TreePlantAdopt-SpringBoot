package com.tree.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tree.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	// Fetch all posts by a specific TreeOwner ID
	List<Post> findByTreeOwnerId(Long treeOwnerId);

}
