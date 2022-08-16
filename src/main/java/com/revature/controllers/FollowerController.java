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
import com.revature.dtos.SearchRequest;
import com.revature.keys.FollowerKey;
import com.revature.models.User;
import com.revature.services.FollowerService;
import com.revature.services.NotificationService;
import com.revature.services.UserService;

@RestController
public class FollowerController {

	private FollowerService fs;
	private UserService us;
	private NotificationService ns;

	public FollowerController(FollowerService fs, UserService us, NotificationService ns) {
		super();
		this.fs = fs;
		this.us = us;
		this.ns = ns;
	}


	@PostMapping("/followed")
	public ResponseEntity<Map<String, Boolean>> isFollowing(@RequestBody FollowerKey fk) {

		return ResponseEntity.ok(Collections.singletonMap("following", fs.isFollowing(fk)));
	}


	@GetMapping("/following/user/{id}")
	public ResponseEntity<List<SearchRequest>> getFollowing(@PathVariable int id,
			@RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit) {
		List<SearchRequest> fdto = new ArrayList<>();
		Pageable p;
		if (limit.isPresent()) {
			p = PageRequest.of(offset.isPresent() ? offset.get() : 0, limit.get());
		} else {
			p = Pageable.unpaged();
		}
		Optional<User> user = us.findById(id);
		if (user.isPresent()) {
			fs.getFollowingByFollower(user.get(), p).forEach(u -> {
				SearchRequest f = new SearchRequest();
				f.setId(u.getId());
				f.setFirstName(u.getFirstName());
				f.setLastName(u.getLastName());
				f.setEmail(u.getEmail());
				f.setLocation(u.getLocation());
				f.setNamePronunciation(u.getNamePronunciation());
				f.setProfessionalURL(u.getProfessionalURL());
				f.setProfilePic(u.getProfilePic());
				f.setUsername(u.getUsername());
				fdto.add(f);
			});
		} else {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(fdto);
	}


	@GetMapping("/followers/user/{id}")
	public ResponseEntity<List<SearchRequest>> getFollowers(@PathVariable int id,
			@RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit) {
		List<SearchRequest> fdto = new ArrayList<>();
		Pageable p;
		if (limit.isPresent()) {
			p = PageRequest.of(offset.isPresent() ? offset.get() : 0, limit.get());
		} else {
			p = Pageable.unpaged();
		}
		Optional<User> user = us.findById(id);
		if (user.isPresent()) {
			fs.getFollowersByFollowing(user.get(), p).forEach(u -> {
				SearchRequest f = new SearchRequest();
				f.setId(u.getId());
				f.setFirstName(u.getFirstName());
				f.setLastName(u.getLastName());
				f.setEmail(u.getEmail());
				f.setLocation(u.getLocation());
				f.setNamePronunciation(u.getNamePronunciation());
				f.setProfessionalURL(u.getProfessionalURL());
				f.setProfilePic(u.getProfilePic());
				f.setUsername(u.getUsername());
				fdto.add(f);
			});
		} else {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(fdto);
	}


	@PostMapping("/following")
	public HttpStatus addFollowing(@RequestBody FollowerKey fk) {
		fs.addFollowing(fk);
		ns.followNotification(fk);
		return HttpStatus.OK;
	}


	@DeleteMapping("/following")
	public HttpStatus removeFollowing(@RequestBody FollowerKey fk) {
		fs.removeFollowing(fk);
		return HttpStatus.OK;
	}


	@GetMapping("/followers/user/{id}/count")
	public ResponseEntity<Map<String, Long>> countFollowers(@PathVariable int id) {
		Optional<User> user = us.findById(id);
		return user.isPresent()
				? ResponseEntity.ok(Collections.singletonMap("count", fs.countFollowersByUserFollowed(user.get())))
				: ResponseEntity.badRequest().build();
	}


	@GetMapping("/following/user/{id}/count")
	public ResponseEntity<Map<String, Long>> countFollowing(@PathVariable int id) {
		Optional<User> user = us.findById(id);
		return user.isPresent()
				? ResponseEntity.ok(Collections.singletonMap("count", fs.countFollowingByUserFollowing(user.get())))
				: ResponseEntity.badRequest().build();
	}
}
