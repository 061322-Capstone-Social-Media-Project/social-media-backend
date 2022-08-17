package com.revature.controllers;

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
import com.revature.services.LikesService;
import com.revature.services.NotificationService;

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
	public ResponseEntity<Likes> userLikesPost(@RequestBody Likes likes) {
		Likes checkLikes = ls.findLikesByUserIdAndPostId(likes.getUserId(), likes.getPostId());

		ns.likeNotification(likes);

		return (checkLikes != null) ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(likes)
				: ResponseEntity.ok(this.ls.userLikesPost(likes));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> removeLike(@PathVariable("id") int id) {
		try {
			ls.removeLike(id);
		} catch (LikeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/{user_id}/post/{post_id}")
	public ResponseEntity<Likes> findLikesByUserIdAndPostId(@PathVariable("user_id") int userId,
			@PathVariable("post_id") int postId) {
		return ResponseEntity.ok(ls.findLikesByUserIdAndPostId(userId, postId));
	}

	@GetMapping("/count/post/{post_id}")
	public ResponseEntity<Long> countLikes(@PathVariable("post_id") int postId) {

		return ResponseEntity.ok(ls.countLikesByPostId(postId));

	}

}
