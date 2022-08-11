package com.revature.controllers;

import com.revature.exceptions.LikeNotFoundException;
import com.revature.models.Likes;
import com.revature.services.LikesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikesController {
	
	private final LikesService ls;

	public LikesController(LikesService ls) {
		super();
		this.ls = ls;
	}

	@PostMapping
	public ResponseEntity<Likes> userLikesPost(@RequestBody Likes likes){
		Likes checkLikes = ls.findLikesByUserIdAndPostId(likes.getUserId(), likes.getPostId());
		
		return (checkLikes != null) ? 
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(likes):
					ResponseEntity.ok(this.ls.userLikesPost(likes));
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<Boolean> removeLike(@PathVariable("id") int id){
		try {
			ls.removeLike(id);
		} catch (LikeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/user/{user_id}/post/{post_id}")
	public ResponseEntity<Likes> findLikesByUserIdAndPostId(@PathVariable("user_id") int user_id,@PathVariable("post_id") int post_id) {
		return ResponseEntity.ok(ls.findLikesByUserIdAndPostId(user_id, post_id));
	}
	
}
