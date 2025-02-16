package com.tree.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tree.entity.Comment;
import com.tree.entity.Post;
import com.tree.service.PostService;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {

	@Autowired
	private PostService postService;

	// ✅ Create Post API (Fixed to use `ownerId`)
	@PostMapping(value = "/create", consumes = "multipart/form-data")
	public ResponseEntity<Post> createPost(@RequestParam("text") String text, @RequestParam("ownerId") Long ownerId,
			@RequestParam("image") MultipartFile image) throws IOException {
		return ResponseEntity.ok(postService.createPost(image, text, ownerId));
	}

	// ✅ Get All Posts API
	@GetMapping("/all")
	public ResponseEntity<List<Post>> getAllPosts() {
		return ResponseEntity.ok(postService.getAllPosts());
	}

	// ✅ Get Posts by Tree Owner API
	@GetMapping("/owner/{ownerId}")
	public ResponseEntity<List<Post>> getPostsByOwner(@PathVariable Long ownerId) {
		return ResponseEntity.ok(postService.getPostsByOwner(ownerId));
	}

	// ✅ Like Post API (Fixed API call)
	@PostMapping("/like/{postId}/{ownerId}")
	public ResponseEntity<String> likePost(@PathVariable Long postId, @PathVariable Long ownerId) {
		postService.likePost(postId, ownerId);
		return ResponseEntity.ok("Post liked successfully!");
	}

	// ✅ Add Comment API (Fixed API call)
	@PostMapping("/addComment")
	public ResponseEntity<Comment> addComment(@RequestParam("postId") Long postId,
			@RequestParam("ownerId") Long ownerId, @RequestParam("text") String text) {
		return ResponseEntity.ok(postService.addComment(postId, ownerId, text));
	}

	// ✅ Delete Post API (Admin and Post Owner can delete)
	@DeleteMapping("/delete/{postId}/{ownerId}")
	public ResponseEntity<String> deletePost(@PathVariable Long postId, @PathVariable Long ownerId) {
		postService.deletePost(postId, ownerId);
		return ResponseEntity.ok("Post deleted successfully!");
	}

	// ✅ Delete Comment API (Admin and Comment Owner can delete)
	@DeleteMapping("/comment/delete/{commentId}/{ownerId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @PathVariable Long ownerId) {
		postService.deleteComment(commentId, ownerId);
		return ResponseEntity.ok("Comment deleted successfully!");
	}
}
