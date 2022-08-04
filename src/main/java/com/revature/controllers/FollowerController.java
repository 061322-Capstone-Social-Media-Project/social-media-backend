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

@RestController
public class FollowerController {

	@Authorized
	@GetMapping("/following")
	public ResponseEntity<Set<User>> getFollowing(@RequestBody User u) {
		return null;
	}
	
	@Authorized
	@GetMapping("/followers")
	public ResponseEntity<Set<User>> getFollowers(@RequestBody User u) {
		return null;
	}
	
	@Authorized
	@PostMapping("/following")
	public ResponseEntity<String> addFollowing(@RequestBody FollowerKey fk) {
		return null;
	}
	
	@Authorized
	@DeleteMapping("/following")
	public ResponseEntity<String> removeFollowing(@RequestBody FollowerKey fk) {
		return null;
	}
}
