package com.revature.controllers;

import com.revature.models.Post;
import com.revature.services.NotificationService;
import com.revature.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

	private final PostService postService;
	private final NotificationService ns;

    public PostController(PostService postService, NotificationService ns) {
        this.postService = postService;
        this.ns = ns;
    }


    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(postService.getMainPosts());

    }


    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody Post post) {
    	 ns.commentNotification(post);
     	 return ResponseEntity.ok(postService.upsert(post));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable("id") int id) {
     	return ResponseEntity.ok(postService.findById(id));
    }
}
