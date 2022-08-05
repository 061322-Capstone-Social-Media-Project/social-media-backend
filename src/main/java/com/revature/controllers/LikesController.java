package com.revature.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Likes;
import com.revature.repositories.LikesRepository;
import com.revature.services.LikesService;

@RestController
@RequestMapping("/likes")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LikesController {
	
	private final LikesService ls;
	private final LikesRepository lr;

	public LikesController(LikesService ls, LikesRepository lr) {
		super();
		this.ls = ls;
		this.lr = lr;
	}

	@PostMapping
	public ResponseEntity<Likes> userLikesPost(@RequestBody Likes likes){
		
		return ResponseEntity.ok(this.ls.userLikesPost(likes));
		
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<Boolean> removeLike(@PathVariable("id") int id){
		ls.removeLike(id);
//		Likes likes = ls.findById(id);
//		if(likes != null) {
//			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
//		}
		return ResponseEntity.ok(true);
	
	}
	@GetMapping("/user/{user_id}/post/{post_id}")
	public ResponseEntity<Likes> findLikesByUserIdAndPostId(@PathVariable("user_id") int user_id,@PathVariable("post_id") int post_id) {
 		
		return ResponseEntity.ok(ls.findLikesByUserIdAndPostId(user_id, post_id));
		
	}
	
}
