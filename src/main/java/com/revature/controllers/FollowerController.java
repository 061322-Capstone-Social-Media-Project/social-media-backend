package com.revature.controllers;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.keys.FollowerKey;
import com.revature.models.User;
import com.revature.services.FollowerService;

@RestController
public class FollowerController {
	
	private FollowerService fs;

	public FollowerController(FollowerService fs) {
		super();
		this.fs = fs;
	}

	@Authorized
	@GetMapping("/following")
	public ResponseEntity<Set<User>> getFollowing(@RequestBody User u) {
		return ResponseEntity.ok(fs.getFollowingByFollower(u));
	}
	
	@Authorized
	@GetMapping("/followers")
	public ResponseEntity<Set<User>> getFollowers(@RequestBody User u) {
		return ResponseEntity.ok(fs.getFollowersByFollowing(u));
	}
	
	@Authorized
	@PostMapping("/following")
	public ResponseEntity<String> addFollowing(@RequestBody FollowerKey fk) {
		fs.addFollowing(fk);
		return ResponseEntity.ok("Followed");
	}
	
	@Authorized
	@DeleteMapping("/following")
	public ResponseEntity<String> removeFollowing(@RequestBody FollowerKey fk) {
		fs.removeFollowing(fk);
		return ResponseEntity.ok("Unfollowed");
	}
}
