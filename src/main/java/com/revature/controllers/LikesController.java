package com.revature.controllers;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.LikeNotFoundException;
import com.revature.models.Likes;
import com.revature.models.Notification;
import com.revature.models.NotificationType;
import com.revature.models.Post;
import com.revature.services.LikesService;
import com.revature.services.NotificationService;
import com.revature.services.PostService;

@RestController
@RequestMapping("/likes")
public class LikesController {
	
	private final LikesService ls;
	private final NotificationService ns;


	public LikesController(LikesService ls, NotificationService ns) {
		super();
		this.ls = ls;
		this.ns = ns;
	}

	@PostMapping
	public ResponseEntity<Likes> userLikesPost(@RequestBody Likes likes){
		Likes checkLikes = ls.findLikesByUserIdAndPostId(likes.getUserId(), likes.getPostId());
		
		
		ns.likenotification(likes);
		

		
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
