package com.revature.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Likes;
import com.revature.services.LikesService;

@RestController
@RequestMapping("/likes")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LikesController {
	
	private final LikesService ls;

	public LikesController(LikesService ls) {
		super();
		this.ls = ls;
	}

	@PostMapping
	public ResponseEntity<Likes> userLikesPost(@RequestBody Likes likes){
		
		return ResponseEntity.ok(this.ls.userLikesPost(likes));
		
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<Boolean> removeLike(@PathVariable("id") int id){
		ls.removeLike(id);
		
		return ResponseEntity.ok(true);
	
	}
	
}
