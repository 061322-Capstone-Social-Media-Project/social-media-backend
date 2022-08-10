package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Post;
import com.revature.services.NotificationService;
import com.revature.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

	private final PostService postService;
	private final NotificationService ns;

    public PostController(PostService postService, NotificationService ns) {
        this.postService = postService;
        this.ns = ns;
    }
    
    @Authorized
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getMainPosts());

    }
    
    @Authorized
    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody Post post) {
    	 ns.commentNotification(post);
    
    	return ResponseEntity.ok(this.postService.upsert(post));
    }
    
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable("id") int id) {
     	return ResponseEntity.ok(this.postService.findById(id));
    }
}
