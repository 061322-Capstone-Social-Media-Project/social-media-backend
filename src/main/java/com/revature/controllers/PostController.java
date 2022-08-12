package com.revature.controllers;

import com.revature.models.Post;
import com.revature.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

	private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getMainPosts());

    }

    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody Post post) {
    	return ResponseEntity.ok(this.postService.upsert(post));
    }
}
