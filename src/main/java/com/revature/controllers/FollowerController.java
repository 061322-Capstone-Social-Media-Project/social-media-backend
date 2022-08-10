package com.revature.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.UserDTO;
import com.revature.keys.FollowerKey;
import com.revature.services.FollowerService;
import com.revature.services.UserService;

@RestController
public class FollowerController {

	private FollowerService fs;
	private UserService us;

	public FollowerController(FollowerService fs, UserService us) {
		super();
		this.fs = fs;
		this.us = us;
	}

	@Authorized
	@PostMapping("/followed")
	public ResponseEntity<Map<String, Boolean>> isFollowing(@RequestBody FollowerKey fk) {

		return ResponseEntity.ok(Collections.singletonMap("following", fs.isFollowing(fk)));
	}

	@Authorized
	@GetMapping("/following/user/{id}")
	public ResponseEntity<List<UserDTO>> getFollowing(@PathVariable int id, @RequestParam Optional<Integer> offset,
			@RequestParam Optional<Integer> limit) {
		List<UserDTO> fdto = new ArrayList<>();
		Pageable p;
		if (limit.isPresent()) {
			p = PageRequest.of(offset.isPresent() ? offset.get() : 0, limit.get());
		} else {
			p = Pageable.unpaged();
		}
		fs.getFollowingByFollower(us.findById(id).get(), p).forEach(u -> {
			UserDTO f = new UserDTO();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			fdto.add(f);
		});
		return ResponseEntity.ok(fdto);
	}

	@Authorized
	@GetMapping("/followers/user/{id}")
	public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable int id, @RequestParam Optional<Integer> offset,
			@RequestParam Optional<Integer> limit) {
		List<UserDTO> fdto = new ArrayList<>();
		Pageable p;
		if (limit.isPresent()) {
			p = PageRequest.of(offset.isPresent() ? offset.get() : 0, limit.get());
		} else {
			p = Pageable.unpaged();
		}
		fs.getFollowersByFollowing(us.findById(id).get(), p).forEach(u -> {
			UserDTO f = new UserDTO();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			fdto.add(f);
		});
		return ResponseEntity.ok(fdto);
	}

	@Authorized
	@PostMapping("/following")
	public HttpStatus addFollowing(@RequestBody FollowerKey fk) {
		fs.addFollowing(fk);
		return HttpStatus.OK;
	}

	@Authorized
	@DeleteMapping("/following")
	public HttpStatus removeFollowing(@RequestBody FollowerKey fk) {
		fs.removeFollowing(fk);
		return HttpStatus.OK;
	}

	@Authorized
	@GetMapping("/followers/user/{id}/count")
	public ResponseEntity<Map<String, Long>> countFollowers(@PathVariable int id) {
		return ResponseEntity
				.ok(Collections.singletonMap("count", fs.countFollowersByUserFollowed(us.findById(id).get())));
	}
	
	@Authorized
	@GetMapping("/following/user/{id}/count")
	public ResponseEntity<Map<String, Long>> countFollowing(@PathVariable int id) {
		return ResponseEntity
				.ok(Collections.singletonMap("count", fs.countFollowingByUserFollowing(us.findById(id).get())));
	}
}
