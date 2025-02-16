package com.tree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tree.entity.Comment;
import com.tree.entity.Like;
import com.tree.entity.Post;
import com.tree.entity.TreeOwner;
import com.tree.repository.CommentRepository;
import com.tree.repository.LikeRepository;
import com.tree.repository.PostRepository;
import com.tree.repository.TreeOwnerepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private TreeOwnerepo treeOwnerepo;

	@Autowired
	private FileServiceImpl fileService;

	// ✅ Create Post (Fixed `TreeOwner` fetching)
	public Post createPost(MultipartFile image, String text, Long ownerId) throws IOException {
		TreeOwner owner = treeOwnerepo.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));

		Post post = new Post();
		post.setText(text);
		post.setTreeOwner(owner);

		// Save image to file system
		String imageUrl = fileService.uploadImage("uploads/posts", image);
		post.setImagePath(imageUrl);

		return postRepository.save(post);
	}

	// ✅ Get posts by owner
	public List<Post> getPostsByOwner(Long ownerId) {
		return postRepository.findByTreeOwnerId(ownerId);
	}

	// ✅ Like a post (Fixed parameters)
	public Like likePost(Long postId, Long ownerId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
		TreeOwner owner = treeOwnerepo.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));

		Like like = new Like();
		like.setPost(post);
		like.setTreeOwner(owner);

		return likeRepository.save(like);
	}

	// ✅ Delete a post (Admin or Owner)
	public void deletePost(Long postId, Long ownerId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

		if (post.getTreeOwner().getId() != ownerId) { // ✅ No boxing/unboxing issues
			throw new RuntimeException("Unauthorized deletion");
		}

		postRepository.delete(post);
	}

	// ✅ Add a comment (Fixed parameters)
	public Comment addComment(Long postId, Long ownerId, String text) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
		TreeOwner owner = treeOwnerepo.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));

		Comment comment = new Comment();
		comment.setPost(post);
		comment.setTreeOwner(owner);
		comment.setText(text);

		return commentRepository.save(comment);
	}

	// ✅ Delete a comment (Admin or Owner)
	public void deleteComment(Long commentId, Long ownerId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		if (comment.getTreeOwner().getId() != ownerId) { // ✅ No boxing/unboxing issues
			throw new RuntimeException("Unauthorized deletion");
		}

		commentRepository.delete(comment);
	}

	// ✅ Get all posts
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

}
